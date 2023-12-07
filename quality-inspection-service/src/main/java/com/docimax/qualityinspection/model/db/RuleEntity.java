package com.docimax.qualityinspection.model.db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.docimax.qualityinspection.model.base.BaseDBModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author : lierlin
 * @className : ClientController
 * @description : 客户端(应用)管理
 * @date : 2023/12/7 15:06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_rule")
public class RuleEntity extends BaseDBModel implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * ID 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 场景 1:扫描 ,2:校验
     */
    @TableField(value = "scene")
    private String scene;
    /**
     * 约束条件
     */
    @TableField(value = "constraint_conditions")
    private String constraintConditions;

    /**
     * 规则名称
     */
    @TableField(value = "rule_name")
    private String ruleName;

    /**
     * 检测项
     */
    @TableField(value = "detection_item")
    private String detectionItem;

    /**
     * 规则标准类型
     */
    @TableField(value = "rule_standard_type")
    private String ruleStandardType;

    /**
     * 规则标准
     */
    @TableField(value = "rule_standard")
    private String ruleStandard;

    /**
     * 错误信息
     */
    @TableField(value = "error_message")
    private String errorMessage;

}
