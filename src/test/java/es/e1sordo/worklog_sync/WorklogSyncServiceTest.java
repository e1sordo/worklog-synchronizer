package es.e1sordo.worklog_sync;

import es.e1sordo.worklog_sync.models.WorklogToAdd;
import es.e1sordo.worklog_sync.services.JiraService;
import es.e1sordo.worklog_sync.services.NotionService;
import es.e1sordo.worklog_sync.services.WorklogSyncService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WorklogSyncServiceTest {

    @InjectMocks
    WorklogSyncService service;

    @Mock
    JiraService jiraService;

    @Mock
    NotionService notionService;

    @Test
    void test() {
        // given
        final WorklogToAdd worklog1 = new WorklogToAdd(
                "1",
                1750,
                "Participated in code review",
                LocalDate.of(2020, 5, 30),
                "16:30",
                20
        );
        final WorklogToAdd worklog2 = new WorklogToAdd(
                "2",
                1773,
                null,
                LocalDate.of(2020, 5, 30),
                "19:00",
                60
        );
        when(notionService.getUnpostedWorklogs()).thenReturn(List.of(worklog1, worklog2));
        when(jiraService.postWorklog(worklog1)).thenReturn("123456");
        when(jiraService.postWorklog(worklog2)).thenReturn(null);

        // when
        service.syncNotionWithJira();

        // then
        verify(notionService).updateWorklogsRecord(worklog1.dbEntityId(), "123456");
        verifyNoMoreInteractions(notionService);
    }
}
