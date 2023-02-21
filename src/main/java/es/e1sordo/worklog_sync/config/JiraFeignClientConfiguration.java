package es.e1sordo.worklog_sync.config;

import feign.RequestInterceptor;
import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;

import java.util.List;

public class JiraFeignClientConfiguration extends RetryableAndLoggedGatewaysConfiguration {

    @Bean
    public RequestInterceptor basicAuthRequestInterceptor(@Value("${jira.login}") String login,
                                                          @Value("${jira.password}") String password) {
        return new BasicAuthRequestInterceptor(login, password);
    }

    @NonNull
    @Override
    public List<String> getPrintableHeaders() {
        return List.of(HttpHeaders.AUTHORIZATION, HttpHeaders.ACCEPT);
    }

    @NonNull
    @Override
    public List<String> getSecretHeaders() {
        return List.of(HttpHeaders.AUTHORIZATION);
    }
}
