package es.e1sordo.worklog_sync.config;

import feign.RequestInterceptor;
import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class JiraFeignClientConfiguration extends RetryableAndLoggedGatewaysConfiguration {

    @Bean
    public RequestInterceptor basicAuthRequestInterceptor(@Value("${jira.login}") String login,
                                                          @Value("${jira.password}") String password) {
        return new BasicAuthRequestInterceptor(login, password);
    }
}
