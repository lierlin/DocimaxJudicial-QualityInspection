package com.docimax.qualityinspection.model.dto.output;

import lombok.Data;

import java.util.List;

/**
 * @author : lierlin
 * @className : ClientController
 * @description : 客户端(应用)管理
 * @date : 2023/12/5 13:36
 */
@Data
public class CheckOutDTO {
    private String catalogueId;
    private String catalogueName;
    private String pagination;
    private List<String> errInfo;
}
