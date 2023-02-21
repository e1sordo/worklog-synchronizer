package es.e1sordo.worklog_sync;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import es.e1sordo.worklog_sync.services.WorklogSyncService;
import es.e1sordo.worklog_sync.stubs.NotionClientStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@WireMockTest(httpPort = 9561)
@ActiveProfiles("test")
class WorklogSyncRunnerTest {

    @Autowired
    WorklogSyncService service;

    @Value("${notion.timesheetDb}")
    String db;

    @BeforeEach
    void setUp() {
        NotionClientStub.returnEmpty(db);
    }

    @Test
    void test() {
        service.syncNotionWithJira();
    }
}
