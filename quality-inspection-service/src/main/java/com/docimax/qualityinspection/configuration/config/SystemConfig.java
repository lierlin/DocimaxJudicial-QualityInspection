package com.docimax.qualityinspection.configuration.config;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
/**
 * @Description:
 * @author: lel
 * @date: 2022.04.18
 */
@RefreshScope
@Configuration
@Slf4j
public class SystemConfig {

    @Value("${server.port:9092}")
    private String port;

    @Value("${service.platform.url:test}")
    private String auth;
    @Value("${service.courtName:ffff}")
    private String courtName;
    /**
     * nacos请求地址
     */
    @Value("${spring.cloud.nacos.config.server-addr:sc-dev.docimaxvip.com:8848}")
    private String nacosServerAddr;






































    /**
     * 获取接口配置JSON
     *
     * @param path
     * @return Object
     */
    public Object getApiConfigByPath(String path) {
        JSONObject apiConfig = getApiConfigJSONObject();
        Object result = getNacosJSONObjectByPath(apiConfig, path);
        return result;
    }
    /**
     * 获取nacos文件内容
     *
     * @param jsonObject 数据源
     * @param path       匹配路径（data.children:[]）
     * @return Object
     */
    public Object getNacosJSONObjectByPath(JSONObject jsonObject, String path) {
        Object result = null;
        if (jsonObject == null || path == null) {
            return result;
        }
        if (path.contains(":")) {
            String def = path.split(":")[1];
            if (def.startsWith("[") && def.endsWith("]")) {
                result = JSONArray.parseArray(def);
            } else if (def.startsWith("{") && def.endsWith("}")) {
                result = JSONObject.parseObject(def);
            } else {
                result = def;
            }
        }
        String[] splits = path.split(":")[0].split("\\.");
        for (int i = 0; i < splits.length; i++) {
            String split = splits[i];
            Object temp = jsonObject.get(split);
            if (temp == null) {
                return result;
            }
            if (temp instanceof JSONObject) {
                jsonObject = (JSONObject) temp;
            }
            if (i == splits.length - 1) {
                result = temp;
            }
            if (!(temp instanceof JSONObject)) {
                break;
            }
        }
        return result;
    }
    /**
     * 获取接口配置JSON
     *
     * @return JSONObject
     */
    public JSONObject getApiConfigJSONObject() {
        JSONObject result = getNacosJSONObject("sc-adapter-api.json");
        return result;
    }
    /**
     * 获取nacos文件内容
     *
     * @param dataId 文件标识
     * @return JSONObject
     */
    public JSONObject getNacosJSONObject(String dataId) {
        JSONObject json = getNacosJSONObject(dataId, "DEFAULT_GROUP");
        return json;
    }
    /**
     * 获取nacos文件内容
     *
     * @param dataId 文件标识
     * @param group  文件分组
     * @return JSONObject
     */
    public JSONObject getNacosJSONObject(String dataId, String group) {
        String nacosContent = getNacosConfig(dataId, group);
        JSONObject json = JSONObject.parseObject(nacosContent);
        return json;
    }
    /**
     *
     * 获取nacos文件内容
     *
     * @param dataId 文件标识
     * @return String
     */
    public String getNacosConfig(String dataId) {
        String nacosContent = getNacosConfig(dataId, "DEFAULT_GROUP");
        return nacosContent;
    }
    /**
     * 获取nacos文件内容
     *
     * @param dataId 文件标识
     * @param group  文件分组
     * @return String
     */
    public String getNacosConfig(String dataId, String group) {
        try {
            ConfigService configService = NacosFactory.createConfigService(nacosServerAddr);
            String content = configService.getConfig(dataId, group, 5000);
            return content;
        } catch (Exception ex) {
            log.error(">>nacos>>获取文件内容异常，url：" + nacosServerAddr + "，dataId：" + dataId + "，group：" + group, ex);
            ex.printStackTrace();
        }
        return null;
    }
    public String getAuthUrl() {
        return auth;
    }


}
