package es.e1sordo.worklog_sync.dto.jira;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static es.e1sordo.worklog_sync.dto.jira.IssueWorklogResponse.ZONED_PATTERN;

@Data
public class CreateWorklogRequest {

    @JsonProperty
    private String comment;

    @JsonProperty
    private String started;

    @JsonProperty
    private int timeSpentSeconds;

    public CreateWorklogRequest(final String comment,
                                final LocalDateTime started,
                                final int timeSpentSeconds) {
        this.comment = comment;
        this.started = started
                .atZone(ZoneId.of("Europe/Moscow"))
                .format(DateTimeFormatter.ofPattern(ZONED_PATTERN));
        this.timeSpentSeconds = timeSpentSeconds;
    }
}
