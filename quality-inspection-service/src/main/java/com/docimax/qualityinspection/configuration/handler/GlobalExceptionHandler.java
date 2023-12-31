package com.docimax.qualityinspection.configuration.handler;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.docimax.erms.common.enums.ErrorCodeEnum;
import com.docimax.erms.common.model.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Description: 全局的的异常拦截器（拦截所有的控制器）
 * @Date: 2019/9/6 13:25
 */
@RestControllerAdvice
@Order(-1)
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 所有异常信息
     *
     * @param exception
     * @return
     * @throws Exception
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public R<String> exceptionHandler(Exception exception) {
        log.error("exceptionHandle {}\n{}", exception.getMessage(), ExceptionUtil.stacktraceToString(exception));
        return R.restResult(ErrorCodeEnum.OTHER_ERROR, exception.getMessage());
    }
}
