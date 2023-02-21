package es.e1sordo.worklog_sync.services;

import es.e1sordo.worklog_sync.models.WorklogToAdd;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorklogSyncService {

    private final JiraService jiraService;
    private final NotionService notionService;

    public void syncNotionWithJira() {
        log.info("Application launched and ready to work");

        List<WorklogToAdd> unpostedWorklogs = notionService.getUnpostedWorklogs();

        for (var worklogToAdd : unpostedWorklogs) {
            String jiraId = jiraService.postWorklog(worklogToAdd);

            if (jiraId != null) {
                notionService.updateWorklogsRecord(worklogToAdd.dbEntityId(), jiraId);
            }
        }

//        notionService.getAllUnpostedDays();

        log.info("Job is done");
    }
}
