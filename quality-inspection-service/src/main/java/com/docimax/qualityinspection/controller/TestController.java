package com.docimax.qualityinspection.controller;

import com.docimax.qualityinspection.configuration.config.SystemConfig;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 测试接口
 * @author : lierlin
 */
@Slf4j
@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/test")
@Tag(name = "测试模块", description = "测试模块操作接口")
public class TestController {

    @Autowired
    private SystemConfig systemConfig;


    @PostMapping("/getNacos")
    public String getNacos() {
        return systemConfig.getAuthUrl();
    }

}
