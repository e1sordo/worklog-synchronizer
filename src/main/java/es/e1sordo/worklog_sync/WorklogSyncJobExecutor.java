package es.e1sordo.worklog_sync;

import es.e1sordo.worklog_sync.services.WorklogSyncService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("!test")
@Component
@RequiredArgsConstructor
public class WorklogSyncJobExecutor implements CommandLineRunner {

    private final WorklogSyncService worklogSyncService;

    @Override
    public void run(final String... args) {
        worklogSyncService.syncNotionWithJira();
    }
}
