package com.docimax.qualityinspection.configuration.config;

import com.docimax.erms.common.context.HeaderParamHolder;
import com.docimax.erms.common.db.base.interceptor.DataFilterThreadLocal;
import com.docimax.erms.common.dto.RequestHeaderParamDto;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author : lel
 * @className : HeaderParamInterceptor
 * @description : 请求头信息拦截器
 * @date : 2022年2月23日20:19:54
 */
@Component
public class HeaderParamInterceptor implements HandlerInterceptor {
    private static ThreadLocal<RequestHeaderParamDto> threadLocal;



    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String servletPath = request.getServletPath();
        if (servletPath.startsWith("/dataCenter") || servletPath.startsWith("/ocr") || servletPath.startsWith("/api/ThirdPartyInfo") || servletPath.startsWith("/springdoc") || servletPath.startsWith("/swagger") || servletPath.startsWith("/webjars") || servletPath.startsWith("/v3/api-docs")) {
            return true;
        };
        HeaderParamHolderPC.getInstance().setRequestHeaderParam(request);
        //RequestHeaderParamDto requestHeaderParam=new RequestHeaderParamDto();
        //requestHeaderParam.setUid(request.getHeader("uid"));
        //HeaderParamHolder.getInstance().setRequestHeaderParam(requestHeaderParam);
        //HeaderParamHolder.getInstance().setRequestHeaderParam(request);
        //Set<Long> categoryIds = HeaderParamHolder.getInstance().getRequestHeaderParamDTO().getRecordsTypeScope();
        //DataFilterThreadLocal.set(DataFilterMetaData.builder().filterType(DataFilterTypeEnum.CATEGORY_SETS).categoryIds(categoryIds).build());

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        HeaderParamHolder.getInstance().clear();
        DataFilterThreadLocal.clear();
    }
}
