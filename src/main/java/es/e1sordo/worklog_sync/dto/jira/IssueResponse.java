package es.e1sordo.worklog_sync.dto.jira;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssueResponse {

    private String id;
    private String key;
    private String description;
}
