package com.docimax.qualityinspection.configuration.config;

import io.micrometer.core.instrument.util.NamedThreadFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Description:线程池
 * @author: 李二林
 * @date: 2023.7.18
 */
@Configuration
@EnableAsync
public class ThreadPoolConfig {

    /**
     * 获取当前机器的核数
     */
    public static final int cpuNum = Runtime.getRuntime().availableProcessors();

    /**
     * 核心线程数（默认线程数）
     */
    private static final int CORE_POOL_SIZE = 4;
    /**
     * 最大线程数
     */
    private static final int MAX_POOL_SIZE = 8;
    /**
     * 允许线程空闲时间（单位：默认为秒）
     */
    private static final int KEEP_ALIVE_TIME = 10;
    /**
     * 缓冲队列大小
     */
    private static final int QUEUE_CAPACITY = 100;

    /**
     * 实现功能描述：缓存线程池,用于加载自动补全缓存
     *
     * @param:
     * @author: 李二林
     * @date: 2022-03-11 15:28
     */
    @Bean("publicBase")
    public ThreadPoolExecutor executor() {
        ArrayBlockingQueue<Runnable> runnables = new ArrayBlockingQueue<Runnable>(QUEUE_CAPACITY);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                CORE_POOL_SIZE,
                MAX_POOL_SIZE,
                KEEP_ALIVE_TIME,
                TimeUnit.MINUTES,
                runnables,
                new NamedThreadFactory("cacheThreadExecutor"),
                new ThreadPoolExecutor.DiscardPolicy()
        );
        return threadPoolExecutor;
    }

}
