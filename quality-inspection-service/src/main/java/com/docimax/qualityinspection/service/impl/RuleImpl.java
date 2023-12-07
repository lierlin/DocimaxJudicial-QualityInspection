package com.docimax.qualityinspection.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.XML;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.docimax.base.dto.CommonResult;
import com.docimax.erms.common.db.base.service.BaseService;
import com.docimax.erms.common.model.PageModel;
import com.docimax.erms.common.model.R;
import com.docimax.qualityinspection.mapper.dao.RuleMapper;
import com.docimax.qualityinspection.model.db.RuleEntity;
import com.docimax.qualityinspection.service.dao.RuleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static com.docimax.erms.common.model.R.error;
import static com.docimax.erms.common.model.R.success;

/**
 * 规则服务
 *
 * @author Wangzh
 */
@Slf4j
@RefreshScope
@Service("RuleImpl")
public class RuleImpl extends BaseService<RuleMapper, RuleEntity> implements RuleService {

}