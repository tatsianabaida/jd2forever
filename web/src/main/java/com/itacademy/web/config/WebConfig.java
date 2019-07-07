package com.itacademy.web.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static com.itacademy.web.util.UrlPath.API;
import static com.itacademy.web.util.UrlPath.LOGIN_PATH;
import static com.itacademy.web.util.ViewName.LOGIN_VIEW;

@Configuration
@ComponentScan({"com.itacademy.web"})
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController(API + LOGIN_PATH).setViewName(LOGIN_VIEW);
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }
}
