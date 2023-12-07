package com.docimax.qualityinspection.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.docimax.erms.common.model.R;
import com.docimax.qualityinspection.model.dto.input.CheckInDTO;
import com.docimax.qualityinspection.model.dto.output.CheckOutDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : lierlin
 * @className : CheckController
 * @description : 检测文件
 * @date : 2023/12/5 13:29
 */
@Slf4j
@RequiredArgsConstructor
@Validated
@RestController
@Tag(name = "检测文件模块", description = "检测文件模块操作接口")
public class CheckController {

    /**
     * 实现功能描述：检测文件接口
     * @author lierlin
     * @param
     * @return com.docimax.erms.common.model.R<com.docimax.qualityinspection.model.dto.output.CheckOutDTO>
     * @date
     */
    @PostMapping("/check")
    public R<CheckOutDTO> check(@RequestBody CheckInDTO checkInDTO) {
        return R.success(new CheckOutDTO());
    }
}
