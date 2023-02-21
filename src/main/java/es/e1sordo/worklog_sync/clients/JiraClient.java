package es.e1sordo.worklog_sync.clients;

import es.e1sordo.worklog_sync.config.JiraFeignClientConfiguration;
import es.e1sordo.worklog_sync.dto.jira.CreateWorklogRequest;
import es.e1sordo.worklog_sync.dto.jira.IssueResponse;
import es.e1sordo.worklog_sync.dto.jira.IssueWorklogsPageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * <a href="https://developer.atlassian.com/cloud/jira/platform/rest/v2/intro/">API Reference</a>
 */
@FeignClient(
        value = "jira-client",
        url = "${jira.url}",
        path = "${jira.path}",
        configuration = JiraFeignClientConfiguration.class
)
public interface JiraClient {

    /**
     * <a href="https://developer.atlassian.com/cloud/jira/platform/rest/v2/api-group-issues/#api-rest-api-2-issue-issueidorkey-get">API Reference</a>
     *
     * @param issueKey key of the issue
     * @return the details of the issue
     */
    @GetMapping(
            value = "/2/issue/{issueKey}?fields=id,key,summary",
            produces = APPLICATION_JSON_VALUE
    )
    IssueResponse getIssue(@PathVariable String issueKey);

    /**
     * Get issue worklogs.
     * <a href="https://developer.atlassian.com/cloud/jira/platform/rest/v2/api-group-issue-worklogs/#api-rest-api-2-issue-issueidorkey-worklog-get">API Reference</a>
     *
     * @param issueKey key of the issue
     * @return worklogs for an issue, starting from the oldest worklog
     */
    @GetMapping(
            value = "/2/issue/{issueKey}/worklog",
            produces = APPLICATION_JSON_VALUE
    )
    IssueWorklogsPageResponse getAllWorklogs(@PathVariable String issueKey);

    /**
     * Adds a worklog to an issue.
     * <a href="https://developer.atlassian.com/cloud/jira/platform/rest/v2/api-group-issue-worklogs/#api-rest-api-2-issue-issueidorkey-worklog-post">API Reference</a>
     *
     * @param issueKey key of the issue
     * @param request  body of the worklog to add
     */
    @PostMapping(
            value = "/2/issue/{issueKey}/worklog?notifyUsers=false",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE
    )
    void addWorklog(@PathVariable String issueKey, @RequestBody CreateWorklogRequest request);
}
