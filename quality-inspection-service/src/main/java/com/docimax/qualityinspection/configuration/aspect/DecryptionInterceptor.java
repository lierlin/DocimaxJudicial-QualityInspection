package com.docimax.qualityinspection.configuration.aspect;

import com.docimax.qualityinspection.configuration.annotation.Encrypted;
import com.docimax.qualityinspection.util.JasyptEncryptorUtils;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.*;

import java.lang.reflect.Field;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

/**
 * 查询结果解密
 * @author wzh81
 */
@Intercepts({
        @Signature(type = ResultSetHandler.class, method = "handleResultSets", args = {Statement.class})
})
public class DecryptionInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 执行原始方法
        Object result = invocation.proceed();

        if (result instanceof ArrayList) {
            for (Object object : (ArrayList) result) {
                // 检查对象是否包含加密字段
                Class<?> clazz = object.getClass();
                Field[] fields = clazz.getDeclaredFields();
                for (Field field : fields) {
                    if (field.isAnnotationPresent(Encrypted.class)) {
                        field.setAccessible(true);
                        String encryptedValue = (String) field.get(object);
                        // 解密字段值
                        String decryptedValue = JasyptEncryptorUtils.decode(encryptedValue);
                        field.set(object, decryptedValue);
                    }
                }
            }
        }

        return result;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        // 可以在这里配置一些属性
    }
}