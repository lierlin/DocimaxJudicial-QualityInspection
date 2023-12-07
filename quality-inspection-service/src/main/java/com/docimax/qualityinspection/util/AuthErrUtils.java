package com.docimax.qualityinspection.util;


import com.docimax.erms.common.model.R;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthErrUtils<T> {

    public  R<T> AuthErr(String msg) {
        R<T> r = new R<>();
        r.setCode("401");
        r.setSuccess(false);
        r.setMessage(msg);
        return r;
    }

}
