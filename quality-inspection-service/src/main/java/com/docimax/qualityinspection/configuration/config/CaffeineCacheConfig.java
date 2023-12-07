package com.docimax.qualityinspection.configuration.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @author : xufubiao
 * @className : CacheConfig
 * @description : Caffine 缓存配置
 * @date : 2022-04-27 14:21
 */
@Configuration
public class CaffeineCacheConfig {
    @Bean
    public Cache<String, Object> excludeCache() {
        return Caffeine.newBuilder()
                // 设置最后一次写入或访问后经过固定时间过期
                .expireAfterWrite(60 * 60 * 24, TimeUnit.SECONDS)
                // 初始的缓存空间大小
                .initialCapacity(50)
                // 缓存的最大条数
                .maximumSize(200)
                .build();
    }
}