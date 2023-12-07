package com.docimax.qualityinspection.configuration.config;

import com.baomidou.dynamic.datasource.plugin.MasterSlaveAutoRoutingPlugin;
import org.springframework.context.annotation.Configuration;

/**
 * @author : xufubiao
 * @className : DynamicDatasourceConfig
 * @description : 动态数据源配置
 * @date : 2022-07-27 21:08
 */
@Configuration
public class DynamicDatasourceConfig {
    /**
     * 读写分离插件
     */
    //@Bean
    public MasterSlaveAutoRoutingPlugin masterSlaveAutoRoutingPlugin(){
        return new MasterSlaveAutoRoutingPlugin();
    }
}
