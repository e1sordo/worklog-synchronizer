package es.e1sordo.worklog_sync.services;

import es.e1sordo.worklog_sync.clients.NotionClient;
import es.e1sordo.worklog_sync.dto.notion.ObjectResponse;
import es.e1sordo.worklog_sync.dto.notion.ObjectsPageResponse;
import es.e1sordo.worklog_sync.dto.notion.QueryRequest;
import es.e1sordo.worklog_sync.dto.notion.UpdatePropertiesRequest;
import es.e1sordo.worklog_sync.models.WorklogToAdd;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotionService {

    private final NotionClient notionClient;

    @Value("${notion.timesheetDb}")
    private String notionDb;

    public List<WorklogToAdd> getUnpostedWorklogs() {
        var filterQuery = new QueryRequest();
        ObjectsPageResponse all = notionClient.getAll(notionDb, filterQuery);

        final List<WorklogToAdd> worklogs = all.getResults().stream()
                .map(result -> new WorklogToAdd(
                        result.getId(),
                        result.getJiraTaskId(),
                        result.getComment(),
                        LocalDate.parse(result.getDate()),
                        result.getStartTime(),
                        (int) (result.getHoursSpent() * 60)
                ))
                .toList();

        if (worklogs.isEmpty()) {
            log.info("There is nothing to log");
        } else {
            log.info("There are {} items to log", worklogs.size());
        }

        return worklogs;
    }

    public void updateWorklogsRecord(String dbEntityId, String jiraId) {
        ObjectResponse response = notionClient.updateProperties(dbEntityId, new UpdatePropertiesRequest(jiraId));
        // simple check that we have not an error in response
        if (response.getJiraTaskId() > 0) {
            log.info("Successfully updated in Notion");
        } else {
            log.warn("Something goes wrong here... Response: {}", response);
        }
    }
}
