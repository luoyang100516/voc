package com.lizhen.web.WebConfig;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class WebConfigurer {
    @Bean
    public FilterRegistrationBean registFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new FilterConfig());
        registration.addUrlPatterns("/*");
        registration.setName("FilterConfig");
        registration.setOrder(1);
        return registration;
    }
}
