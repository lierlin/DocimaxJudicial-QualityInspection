package com.docimax.qualityinspection.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.docimax.erms.common.model.R;
import com.docimax.qualityinspection.model.db.RuleEntity;
import com.docimax.qualityinspection.model.dto.input.CheckInDTO;
import com.docimax.qualityinspection.model.dto.output.CheckOutDTO;
import com.docimax.qualityinspection.service.dao.RuleService;
import com.docimax.qualityinspection.util.ReflectionInvokeUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : lierlin
 * @className : CheckImpl
 * @description : 检测业务层
 * @date : 2023/12/7 14:53
 */
public class CheckImpl {
    @Autowired
    private RuleService ruleService;

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
            R<List<RuleEntity>> ruleList1 = getRuleList("1");
            //2.1规则拿不到直接短路异常
            if (!ruleList1.getSuccess()) {
                return R.error(ruleList1.getMessage());
            }
            //2.2无规则直接返回成功
            if (ruleList1.getData().size() == 0) {
                return R.success();
            }
            List<RuleEntity> ruleList = ruleList1.getData();
            //3. 循环RuleList
            for (RuleEntity rule : ruleList) {
                //调用
                R<String> stringR = ReflectionInvokeUtils.invokeMethod(rule.getDetectionItem(), "");
                if (!stringR.getSuccess()) {
                    errInfo.add(stringR.getData());
                }
            }
            checkOutDTO.setErrInfo(errInfo);
            return R.success(checkOutDTO);
        } catch (Exception exception) {
            return R.error("scanCheck执行异常" + exception.getMessage());
        }
    }

    /**
     * 实现功能描述：通过数据库获取规则信息
     *
     * @param
     * @return com.docimax.erms.common.model.R
     * @author lierlin
     * @date
     */
    public R<List<RuleEntity>> getRuleList(String type) {
        try {
            LambdaQueryWrapper<RuleEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(RuleEntity::getScene, type);
            List<RuleEntity> list = ruleService.list(lambdaQueryWrapper);
            return R.success(list);
        } catch (Exception exception) {
            return R.error("getRuleList异常" + exception.getMessage());
        }
    }


}
