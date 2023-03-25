package es.e1sordo.worklog_sync.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record WorklogToAdd(String dbEntityId,
                           String projectCode,
                           int issueId,
                           String comment,
                           LocalDate day,
                           String localStartTime,
                           int minutesSpent) {

    public LocalDateTime localStartDateTime() {
        return LocalDateTime.of(day, LocalTime.parse(localStartTime + ":00"));
    }

    @Override
    public String toString() {
        return "(Jira: %s-%s, comment: %s, day: %s, startTime: %s, minutes: %d, entityId: %s)".formatted(
                projectCode, issueId, comment, day, localStartTime, minutesSpent, dbEntityId
        );
    }
}
