package es.e1sordo.worklog_sync;

import es.e1sordo.worklog_sync.models.WorklogToAdd;
import es.e1sordo.worklog_sync.services.JiraService;
import es.e1sordo.worklog_sync.services.NotionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class WorklogSyncRunner implements InitializingBean {

    private final NotionService notionService;
    private final JiraService jiraService;

    @Override
    public void afterPropertiesSet() {
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
