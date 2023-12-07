package com.docimax.qualityinspection.service.impl;

import com.docimax.erms.common.model.R;
import com.docimax.qualityinspection.model.db.RuleEntity;
import com.docimax.qualityinspection.model.dto.input.CheckInDTO;
import com.docimax.qualityinspection.model.dto.output.CheckOutDTO;
import com.docimax.qualityinspection.util.ReflectionInvokeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : lierlin
 * @className : CheckImpl
 * @description : 检测业务层
 * @date : 2023/12/7 14:53
 */
public class CheckImpl {

    /**
     * 实现功能描述：扫描检测
     *
     * @param
     * @return com.docimax.erms.common.model.R<com.docimax.qualityinspection.model.dto.output.CheckOutDTO>
     * @author lierlin
     * @date
     */
    public R<CheckOutDTO> scanCheck(CheckInDTO checkInDTO) {
        try {
            CheckOutDTO checkOutDTO = new CheckOutDTO();
            List<String> errInfo = new ArrayList<>();
            //1. 基于参数获取到 图片的地址
            //2. 查询数据库获取当前扫描场景需检测项
            List<RuleEntity> ruleList = null;
            //3. 循环RuleList
            for (RuleEntity rule : ruleList) {
                //调用
                Object o = ReflectionInvokeUtils.invokeMethod("", "");
                //解析
                R<String> booleanR = analyticReturnValue(o);
                if (!booleanR.getSuccess()) {
                    errInfo.add(booleanR.getData());
                }
            }
            checkOutDTO.setErrInfo(errInfo);
            return R.success(checkOutDTO);
        } catch (Exception exception) {
            return R.error("scanCheck执行异常" + exception.getMessage());
        }
    }

    /**
     * 实现功能描述：解析返回值
     *
     * @param
     * @return com.docimax.erms.common.model.R<java.lang.Boolean>
     * @author lierlin
     * @date
     */
    public R<String> analyticReturnValue(Object o) {
        return new R<>();
    }
}
