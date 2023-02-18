package es.e1sordo.worklog_sync.dto.jira;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private String self;
    private String name;
    private String key;
    private String emailAddress;
    private Map<String, String> avatarUrls;
    private String displayName;
    private Boolean active;
    private String timezone;
}
