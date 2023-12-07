package com.docimax.qualityinspection.configuration.aspect;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import lombok.extern.java.Log;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author : xufubiao
 * @className : DataSourceAop
 * @description : 动态数据源配置
 * @date : 2022-07-27 21:25
 */
@Aspect
@Component
@Order(0)
@Lazy(false)
@Log
public class DataSourceAop {
    private static final String MASTER = "master";
    private static final String SLAVE = "slave";


    @Pointcut("execution(* com.docimax.qualityinspection.service..*.*(..)) || execution(* com.baomidou.mybatisplus.extension.service..*.*(..))")
    public void checkArgs() {
    }

    @Before("checkArgs()")
    public void process(JoinPoint joinPoint) throws NoSuchMethodException, SecurityException {
        String methodName = joinPoint.getSignature().getName();
        Class clazz = joinPoint.getTarget().getClass();
        if(clazz.isAnnotationPresent(DS.class)){
            //获取类上注解
            DS ds = (DS) clazz.getAnnotation(DS.class);
            if (MASTER.equals(ds.value())) {
                log.info("当前执行的库："+MASTER);
                DynamicDataSourceContextHolder.push(MASTER);
            } else {
                log.info("当前执行的库："+SLAVE);
                DynamicDataSourceContextHolder.push(SLAVE);
            }
            return;
        }

        String targetName = clazz.getSimpleName();
        Class[] parameterTypes =
                ((MethodSignature)joinPoint.getSignature()).getMethod().getParameterTypes();
        Method methdo = clazz.getMethod(methodName,parameterTypes);
        if (methdo.isAnnotationPresent(DS.class)) {
            DS ds = (DS) methdo.getAnnotation(DS.class);
            if (MASTER.equals(ds.value())) {
                log.info("当前执行的库："+MASTER);
                DynamicDataSourceContextHolder.push(MASTER);
            } else {
                log.info("当前执行的库："+SLAVE);
                DynamicDataSourceContextHolder.push(SLAVE);
            }
            return;
        }
        if (methodName.startsWith("get")
                || methodName.startsWith("count")
                || methodName.startsWith("find")
                || methodName.startsWith("list")
                || methodName.startsWith("select")
                || methodName.startsWith("check")
                || methodName.startsWith("page")) {

            log.info("当前执行的库："+SLAVE);
            DynamicDataSourceContextHolder.push(SLAVE);
        } else {
            log.info("当前执行的库："+MASTER);
            DynamicDataSourceContextHolder.push(MASTER);
        }
    }
    @After("checkArgs()")
    public void afterAdvice(){
        DynamicDataSourceContextHolder.clear();
    }
}
