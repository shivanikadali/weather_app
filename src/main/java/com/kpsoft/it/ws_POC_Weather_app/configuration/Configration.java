package com.kpsoft.it.ws_POC_Weather_app.configuration;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;


@Configuration
@EnableAsync
public class Configration {
    @Bean(name = "multiThreading")
    public Executor getThreadPoolExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(3);
        executor.setMaxPoolSize(10);
        executor.setThreadNamePrefix("ThreadForCity");
        executor.setQueueCapacity(100);
        executor.initialize();
        return executor;
    }
}
