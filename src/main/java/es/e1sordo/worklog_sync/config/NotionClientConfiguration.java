package es.e1sordo.worklog_sync.config;

import feign.RequestInterceptor;
import feign.okhttp.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;

import java.util.List;

public class NotionClientConfiguration extends RetryableAndLoggedGatewaysConfiguration {

    private static final String AUTHORIZATION_HEADER = HttpHeaders.AUTHORIZATION;
    private static final String NOTION_VERSION_HEADER = "Notion-Version";
    private static final String TOKEN_TYPE = "Bearer";

    @Bean
    public OkHttpClient client() {
        return new OkHttpClient();
    }

    @Bean
    public RequestInterceptor requestInterceptor(@Value("${notion.secret}") String secret) {
        return requestTemplate -> {
            requestTemplate.header(AUTHORIZATION_HEADER, TOKEN_TYPE + " " + secret);
            requestTemplate.header(NOTION_VERSION_HEADER, "2022-06-28");
        };
    }

    @NonNull
    @Override
    public List<String> getPrintableHeaders() {
        return List.of(AUTHORIZATION_HEADER, NOTION_VERSION_HEADER);
    }

    @NonNull
    @Override
    public List<String> getSecretHeaders() {
        return List.of(AUTHORIZATION_HEADER);
    }
}
