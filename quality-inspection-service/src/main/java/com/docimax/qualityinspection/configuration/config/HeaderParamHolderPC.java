package com.docimax.qualityinspection.configuration.config;

import com.docimax.erms.common.dto.RequestHeaderParamDto;
import com.docimax.erms.common.dto.RoleAdditional;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author : lierlin
 * @className : ClientController
 * @description : 客户端(应用)管理
 * @date : 2023/7/25 19:14
 */
public class HeaderParamHolderPC {

        private Logger log = LoggerFactory.getLogger(HeaderParamHolderPC.class);
        private ObjectMapper om = new ObjectMapper();

        private ThreadLocal<RequestHeaderParamDto> threadLocal;

        private HeaderParamHolderPC() {
            this.threadLocal = new ThreadLocal<>();
            //在反序列化时忽略在 json 中存在但 Java 对象不存在的属性
            om.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            //在序列化时日期格式默认为 yyyy-MM-dd'T'HH:mm:ss.SSSZ
            om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            //在序列化时忽略值为 null 的属性
            om.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        }

        public static HeaderParamHolderPC getInstance() {
            return HeaderParamHolderPC.SingletonHolder.sInstance;
        }

        public RequestHeaderParamDto getRequestHeaderInfo() {
            return threadLocal.get();
        }

        public void setRequestHeaderParam(HttpServletRequest request) {
            HashMap<String, String> requestHeaders = getRequestHeaders(request);
            String uidStr = requestHeaders.get("uid");

            RequestHeaderParamDto requestHeaderParam = threadLocal.get();
            if (requestHeaderParam == null) {
                requestHeaderParam = new RequestHeaderParamDto();
            }
            if (StringUtils.isNotBlank(uidStr)) {
                requestHeaderParam.setUid(uidStr);
            }
            log.info("HeaderParamHolder|requestHeaderParam={}", requestHeaderParam);
            threadLocal.set(requestHeaderParam);
        }

        public void setRequestHeaderParam(RequestHeaderParamDto requestHeaderParam) {
            threadLocal.set(requestHeaderParam);
        }

        private static class SingletonHolder {
            private static final HeaderParamHolderPC sInstance = new HeaderParamHolderPC();
        }

        public RequestHeaderParamDto getRequestHeaderParamDTO() {
            return threadLocal.get();
        }

        public String getDataScope() {
            return Optional.ofNullable(threadLocal.get().getDataScope()).orElse("");
        }

        public void setDataScope(String dataScope) {
            threadLocal.get().setDataScope(dataScope);
        }

        public String getUserId() {
            if (threadLocal == null || threadLocal.get() == null) {
                return null;
            } else {
                return Optional.ofNullable(threadLocal.get().getUid()).orElse(null);
            }
        }

   

        private HashMap<String, String> getRequestHeaders(HttpServletRequest request) {
            HashMap<String, String> requestHeaders = new HashMap<String, String>();
            Enumeration<String> headerNames = request.getHeaderNames();

            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                String headerValue = request.getHeader(headerName);
                requestHeaders.put(headerName, headerValue);
            }
            return requestHeaders;
        }

        @SuppressWarnings("unused")
        private HashMap<String, String> getRequestParams(HttpServletRequest request) {
            HashMap<String, String> requestParams = new HashMap<String, String>();
            Enumeration<String> paramNames = request.getParameterNames();

            while (paramNames.hasMoreElements()) {
                String paramName = paramNames.nextElement();
                String paramValue = request.getParameter(paramName);
                requestParams.put(paramName, paramValue);
            }
            return requestParams;
        }

        public void clear() {
            threadLocal.remove();
        }

        private Set<Long> getRecordsTypes(String roleAdditional) {
            Set<Long> types = new HashSet<>();
            if (StringUtils.isBlank(roleAdditional)) {
                types.add(-1L);
                return types;
            }
            String[] strArray = roleAdditional.split("},\\s*\\{");
            if (strArray.length <= 0) {
                types.add(-1L);
                return types;
            }
            if (strArray.length > 1) {
                strArray[0] = strArray[0] + "}";
                strArray[strArray.length - 1] = "{" + strArray[strArray.length - 1];

                for(int i = 1; i < strArray.length - 1; i++) {
                    strArray[i] = "{" + strArray[i] + "}";
                }
            }
            for (String str : strArray) {
                try {
                    RoleAdditional ra = om.readValue(str, RoleAdditional.class);
                    if (ra != null && ra.getRecordAssociation() != null) {
                        Set<Long> longs = ra.getRecordAssociation().calcRecordTypeIds();
                        if (longs != null) {
                            types.addAll(longs);
                        }
                    }
                } catch (JsonProcessingException e) {
                    log.error("", e);
                }
            }
            if (types.size() == 0) {
                types.add(-1L);
            }
            log.info("recordTypeIds: {}", types);
            return types;
        }

        public static void main(String[] args) throws JsonProcessingException {
            String str = "{\"recordAssociation\": {\"permanent\": true, \"recordTypeIds\": [1,2], \"termOfValidity\": [\"2022-08-08\", \"2022-08-10\"]}}, {\"recordAssociation\": {\"permanent\": false, \"recordTypeIds\": [3, 4], \"termOfValidity\": [\"2022-07-08\", \"2022-08-10\"]}}, {\"recordAssociation\": {\"permanent\": false, \"recordTypeIds\": [5,6], \"termOfValidity\": [\"2022-08-08\", \"2022-08-10\"]}}";

            String str2 = "{\"recordAssociation\": {\"permanent\": true, \"recordTypeIds\": [\"3\", \"6\"], \"termOfValidity\": []}}";

            HeaderParamHolderPC headerParamHolder = HeaderParamHolderPC.getInstance();
            System.out.println(headerParamHolder.getRecordsTypes(str2));
        }
    
}
