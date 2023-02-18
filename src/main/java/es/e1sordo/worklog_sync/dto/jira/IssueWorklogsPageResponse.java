package es.e1sordo.worklog_sync.dto.jira;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssueWorklogsPageResponse {

    private Long startAt;
    private Long maxResults;
    private Long total;
    private List<IssueWorklogResponse> worklogs;

    public List<IssueWorklogResponse> getWorklogsByUser(String username) {
        return worklogs.stream()
                .filter(worklog -> username.equals(worklog.getAuthor().getName()))
                .toList();
    }
}
