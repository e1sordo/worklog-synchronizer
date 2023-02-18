package es.e1sordo.worklog_sync.dto.notion;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class UpdatePropertiesRequest {

    @JsonProperty
    private Object properties;

    public UpdatePropertiesRequest(String jiraWorklogId) {
        this.properties = Map.of(
                "In Jira?", Map.of("checkbox", true),
                "Jira worklog ID (debug)", Map.of(
                        "rich_text", List.of(Map.of(
                                "text", Map.of("content", jiraWorklogId)
                        ))
                )
        );
    }
}
