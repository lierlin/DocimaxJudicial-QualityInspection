package com.docimax.qualityinspection.util;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.json.JSONUtil;
import com.docimax.erms.common.model.R;

import java.lang.reflect.Method;

/**
 * @author : lierlin
 * @className : ClientController
 * @description : 客户端(应用)管理
 * @date : 2023/12/7 15:12
 */
public class ReflectionInvokeUtils {

    public static R<String> invokeMethod(String classAndMethod, Object... args) {
        try {
            // 分割字符串以得到类名和方法名
            int lastDotIndex = classAndMethod.lastIndexOf('.');
            String className = classAndMethod.substring(0, lastDotIndex);
            String methodName = classAndMethod.substring(lastDotIndex + 1);

            // 加载类
            Class<?> clazz = Class.forName(className);

            // 创建类的实例
            Object instance = ReflectUtil.newInstance(clazz);

            // 确定参数类型
            Class<?>[] parameterTypes = new Class[args.length];
            for (int i = 0; i < args.length; i++) {
                parameterTypes[i] = args[i].getClass();
            }

            // 获取方法
            Method method = clazz.getMethod(methodName, parameterTypes);

            // 执行方法
            Object invoke = method.invoke(instance, args);
            String jsonStr = JSONUtil.toJsonStr(invoke);
            R<String> result = JSONUtil.toBean(jsonStr, new TypeReference<R<String>>() {
            }, false);
            return result;
        } catch (Exception e) {
            return R.error(e.getMessage());
        }
    }

}
