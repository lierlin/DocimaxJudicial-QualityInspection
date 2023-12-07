package com.docimax.qualityinspection.service.dao;

import com.docimax.erms.common.model.R;
import com.docimax.qualityinspection.model.db.RuleEntity;

import java.util.List;

/**
 * @author : lierlin
 * @className : ScanAbstract
 * @description : 扫描检测类
 * @date : 2023/12/7 14:59
 */
public abstract class  ScanAbstract {
    /**
     * 实现功能描述：扫描检测抽象
     * @author lierlin
     * @param
     * @return void
     * @date
     */
    public abstract R<String> check(String url, RuleEntity rule);
}
