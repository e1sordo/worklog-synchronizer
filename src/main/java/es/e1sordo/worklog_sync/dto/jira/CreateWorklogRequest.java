package es.e1sordo.worklog_sync.dto.jira;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

import static es.e1sordo.worklog_sync.dto.jira.IssueWorklogResponse.ZONED_PATTERN;

@Data
@AllArgsConstructor
public class CreateWorklogRequest {

    @JsonProperty
    private String comment;

    @JsonProperty
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = ZONED_PATTERN)
    private LocalDateTime started;

    @JsonProperty
    private int timeSpentSeconds;
}
