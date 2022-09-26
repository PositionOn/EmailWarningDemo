package com.example.emailwarningdemo.Excetion;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean someFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(jsonFilter());
        registration.addUrlPatterns("/*");
        registration.setName("jsonFilter");
        return registration;
    }

    /**
     * 实例化StreamFilter
     *
     * @return Filter
     */
    @Bean(name = "jsonFilter")
    public Filter jsonFilter() {
        return new JsonFilter();
    }
}
