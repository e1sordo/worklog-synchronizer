package es.e1sordo.worklog_sync.stubs;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;

public class NotionClientStub {

    public static final String ENDPOINT = "/notion/v1/databases";

    public void returnNotFoundError() {
        stubFor(get(urlMatching(ENDPOINT + ".*"))
                .willReturn(aResponse()
                        .withBodyFile("payload/notion/db-query-empty-response.json")
                        .withHeader("Content-Type", "application/json;charset=UTF-8")
                        .withStatus(404)));
    }

    public static void returnEmpty(String db) {
        stubFor(post(urlMatching(ENDPOINT + "/" + db + "/query"))
                .willReturn(aResponse()
                        .withBodyFile("payload/notion/db-query-empty-response.json")
                        .withHeader("Content-Type", "application/json;charset=UTF-8")
                        .withStatus(200)));
    }
}
