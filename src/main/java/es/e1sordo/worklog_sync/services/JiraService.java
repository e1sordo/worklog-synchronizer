package es.e1sordo.worklog_sync.services;

import es.e1sordo.worklog_sync.clients.JiraClient;
import es.e1sordo.worklog_sync.dto.jira.CreateWorklogRequest;
import es.e1sordo.worklog_sync.dto.jira.IssueResponse;
import es.e1sordo.worklog_sync.models.WorklogToAdd;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class JiraService {

    private final JiraClient jiraClient;

    @Value("${jira.url}")
    private String jiraUrl;

    @Value("${jira.login}")
    private String myLogin;

    @Value("${jira.project}")
    private String project;

    public String postWorklog(WorklogToAdd worklogToAdd) {
        log.info("--> Starting to proceed worklog: {}", worklogToAdd);

        if (worklogToAdd.issueId() <= 0) {
            log.warn("Mandatory field 'Jira ID' is wrong or was not specified. System couldn't proceed. Value: {}",
                    worklogToAdd.issueId());
            return null;
        }

        var issueKey = ((null == project) ? "" : project + "-") + worklogToAdd.issueId();

        if (worklogToAdd.minutesSpent() <= 0) {
            log.warn("[{}] Mandatory field 'Spent Time' was not specified. System couldn't proceed", issueKey);
            return null;
        }

        if (worklogToAdd.localStartTime() == null) {
            log.warn("[{}] Mandatory field 'Start Time' was not specified. System couldn't proceed", issueKey);
            return null;
        }

        final IssueResponse issue = jiraClient.getIssue(issueKey);
        log.info("[{}] Summary: {}. Unique ID: {}, link: {}",
                issueKey, issue.getFields().getSummary(), issue.getId(), jiraUrl + "/browse/" + issueKey);

        var issueWorklogsPage = jiraClient.getAllWorklogs(issueKey);
        log.info("[{}] Total worklogs for this issue: {}", issueKey, issueWorklogsPage.getTotal());
        var onlyMyWorklogs = issueWorklogsPage.getWorklogsByUser(myLogin);

        log.info("[{}] Validating possible duplicates...", issueKey);

        boolean result = onlyMyWorklogs.stream()
                .noneMatch(myExistedWorklog -> {
                    if (myExistedWorklog.equals(
                            worklogToAdd.comment(), worklogToAdd.day(),
                            worklogToAdd.localStartTime(), worklogToAdd.minutesSpent()
                    )) {
                        log.warn("[{}] DUPLICATE! New: {}. Existed: {}", issueKey, worklogToAdd, myExistedWorklog);
                        return true;
                    }
                    return false;
                });

        if (!result) {
            log.info("[{}] Skipping to add duplicate worklog", issueKey);
            return null;
        } else {
            log.info("[{}] No duplicates were found. Proceed...", issueKey);
        }

        var createWorklogRequest = new CreateWorklogRequest(
                worklogToAdd.comment(),
                worklogToAdd.localStartDateTime(),
                worklogToAdd.minutesSpent() * 60
        );
        log.info("[{}] Worklog to create: {}", issueKey, createWorklogRequest);

        var addedWorklog = jiraClient.addWorklog(issueKey, createWorklogRequest);
        log.info("[{}] Worklog was successfully created! {}", issueKey, addedWorklog);

        return addedWorklog.getId();
    }
}
