package es.e1sordo.worklog_sync.dto.notion;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class QueryRequest {

    @JsonProperty
    private Object filter;

    @JsonProperty
    private List<Map<String, String>> sorts;

    public QueryRequest() {
        this.filter = new QueryRequest.Filter();
        this.sorts = List.of(
                Map.of(
                        "property", "Date",
                        "direction", "ascending"
                )
        );
    }

    @Data
    static class Filter {

        @JsonProperty
        private List<Object> and;

        public Filter() {
            this.and = List.of(
                    Map.of(
                            "property", "In Jira?",
                            "checkbox", Map.of("equals", false)
                    ),
                    Map.of(
                            "property", "⏰ (hrs.)",
                            "number", Map.of("is_not_empty", true)
                    )
//                    ,
//                    Map.of(
//                            "property", "Jira worklog ID (debug)",
//                            "rich_text", Map.of("is_empty", true)
//                    )
            );
        }
    }
}
