package com.docimax.qualityinspection.configuration.handler;

import com.docimax.erms.common.enums.ErrorCodeEnum;
import com.docimax.erms.common.model.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.Set;

/**
 * @Description: 校验参数异常处理
 * @Date: 2021/6/2 8:38
 */
@RestControllerAdvice
@Order(-10)
@Slf4j
public class CheckExceptionHandler {

    /**
     * 校验异常
     *
     * @param bindException
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(BindException.class)
    public R<String> bindExceptionExceptionHandler(BindException bindException) {
        log.warn("bindException [{}]", bindException.toString());
        if (bindException.hasErrors()) {
            for (ObjectError error : bindException.getAllErrors()) {
                return R.restResult(ErrorCodeEnum.CHECK_ERROR, error.getDefaultMessage());
            }
        }
        return R.restResult(ErrorCodeEnum.CHECK_ERROR, bindException.getMessage());
    }

    /**
     * 参数未填写 @RequestParam
     *
     * @param missingServletRequestParameterException
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public R<String> missingServletRequestParameterExceptionHandler(MissingServletRequestParameterException missingServletRequestParameterException) {
        log.warn("missingServletRequestParameterException [{}]", missingServletRequestParameterException.toString());
        return R.restResult(ErrorCodeEnum.USER_ERROR_A0410, missingServletRequestParameterException.getMessage());
    }

    /**
     * 基本数据类型，验证错误
     *
     * @param exception
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(ValidationException.class)
    public R<String> validationExceptionHandler(ValidationException exception) {
        log.warn("validationExceptionHandle [{}]", exception.toString());
        if (exception instanceof ConstraintViolationException) {
            ConstraintViolationException exs = (ConstraintViolationException) exception;
            Set<ConstraintViolation<?>> violations = exs.getConstraintViolations();
            for (ConstraintViolation<?> item : violations) {
                return R.restResult(ErrorCodeEnum.CHECK_ERROR, item.getMessage());
            }
        }
        return R.restResult(ErrorCodeEnum.CHECK_ERROR, exception.getMessage());
    }

}
