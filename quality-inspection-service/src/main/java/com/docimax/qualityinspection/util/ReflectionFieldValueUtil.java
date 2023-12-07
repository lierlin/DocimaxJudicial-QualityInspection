package com.docimax.qualityinspection.util;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;

/**
 * @author : lierlin
 * @className : ClientController
 * @description : 客户端(应用)管理
 * @date : 2023/7/20 13:27
 */
public class ReflectionFieldValueUtil {

    public static <T> T convertToEntity(LinkedHashMap<String, Object> map, Class<T> entityType) throws Exception {
        T entity = entityType.getDeclaredConstructor().newInstance();

        for (String fieldName : map.keySet()) {
            Field field = entityType.getDeclaredField(fieldName);
            field.setAccessible(true);

            Object value = map.get(fieldName);
            field.set(entity, value);
        }

        return entity;
    }


}
