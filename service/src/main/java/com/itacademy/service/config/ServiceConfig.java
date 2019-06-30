package com.itacademy.service.config;

import com.itacademy.database.config.DatabaseConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(DatabaseConfig.class)
@ComponentScan("com.itacademy.service")
public class ServiceConfig {
}