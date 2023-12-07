package com.docimax.qualityinspection.configuration.config;

import com.baomidou.dynamic.datasource.support.DdConstants;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author : xufubiao
 * @className : MasterSlaveAutoRoutingPlugin
 * @description : 读写分离插件
 * @date : 2022-07-27 21:59
 */
@Intercepts({
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
@Slf4j
public class MasterSlaveAutoRoutingPlugin implements Interceptor {

    @Autowired
    protected DataSource dynamicDataSource;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];
        String pushedDataSource = null;
        try {
            if (StringUtils.isEmpty(DynamicDataSourceContextHolder.peek())) {
                String dataSource = SqlCommandType.SELECT == ms.getSqlCommandType() ? DdConstants.SLAVE : DdConstants.MASTER;
                pushedDataSource = DynamicDataSourceContextHolder.push(dataSource);
            }
            return invocation.proceed();
        } finally {
            if (pushedDataSource != null) {
                DynamicDataSourceContextHolder.poll();
            }
        }
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }
}

