package es.e1sordo.worklog_sync.clients;

import es.e1sordo.worklog_sync.config.NotionClientConfiguration;
import es.e1sordo.worklog_sync.dto.notion.ObjectResponse;
import es.e1sordo.worklog_sync.dto.notion.ObjectsPageResponse;
import es.e1sordo.worklog_sync.dto.notion.QueryRequest;
import es.e1sordo.worklog_sync.dto.notion.UpdatePropertiesRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@FeignClient(
        value = "notion-client",
        url = "${notion.url}",
        path = "${notion.path}",
        configuration = NotionClientConfiguration.class
)
public interface NotionClient {

    @PostMapping(
            value = "/v1/databases/{db}/query",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE
    )
    ObjectsPageResponse getAll(@PathVariable String db, @RequestBody QueryRequest queryRequest);

    @PatchMapping(
            value = "/v1/pages/{pageId}",
            produces = APPLICATION_JSON_VALUE,
            consumes = APPLICATION_JSON_VALUE
    )
    ObjectResponse updateProperties(@PathVariable String pageId, @RequestBody UpdatePropertiesRequest updateRequest);
}
