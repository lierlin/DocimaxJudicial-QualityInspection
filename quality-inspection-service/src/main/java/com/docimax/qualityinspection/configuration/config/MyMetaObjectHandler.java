package com.docimax.qualityinspection.configuration.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.docimax.qualityinspection.configuration.annotation.Encrypted;
import com.docimax.qualityinspection.util.JasyptEncryptorUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.time.LocalDateTime;

@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "orderNumber", Integer.class, 0);
        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "createUser", String.class, HeaderParamHolderPC.getInstance().getUserId());
        this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, null);
        this.strictInsertFill(metaObject, "updateUser", String.class, "");
        this.strictInsertFill(metaObject, "isDeleted", Integer.class, 0);
        // 其他需要自动填充的字段
        handleFields(metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, "updateUser", String.class, HeaderParamHolderPC.getInstance().getUserId());
        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
        // 其他需要自动填充的字段
        handleFields(metaObject);
    }

    private void handleFields(MetaObject metaObject) {
        Class<?> clazz = metaObject.getOriginalObject().getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Encrypted.class)) {
                field.setAccessible(true);
                Object value = metaObject.getValue(field.getName());
                if (value instanceof String) {
                    // 加密字段值
                    String encryptedValue = JasyptEncryptorUtils.encode((String) value);
                    metaObject.setValue(field.getName(), encryptedValue);
                }
            }
        }
    }

}