package com.imooc.ad.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.*;


@Configuration
@EnableAsync
public class ExecutorConfiguration {

    @Bean(name = "binlogClientConnectExecutor")
    public Executor getBinlogClientConnectExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(1);
        executor.setMaxPoolSize(1);
        executor.setQueueCapacity(1);
        executor.setKeepAliveSeconds(60);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
        executor.setThreadNamePrefix("BinlogClientConnect-");
        executor.setWaitForTasksToCompleteOnShutdown(true);
        return executor;


    }

}
