package com.docimax.qualityinspection.configuration.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Slf4j
@Configuration
@EnableAsync
public class TaskExecutePool {


    /**
     * pdf拆分异步线程
     *
     * @return
     */
    @Bean(name = "splitPdfThreadPool")
    public ThreadPoolTaskExecutor getAsyncBmThreadPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //核心线程池大小
        int corePoolSize = 8;
        executor.setCorePoolSize(corePoolSize * 4);
        //最大线程数
        executor.setMaxPoolSize(corePoolSize * 4);
        //队列容量
        executor.setQueueCapacity(Integer.MAX_VALUE);
        //线程名字前缀
        executor.setThreadNamePrefix("async-");
        //executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }




}