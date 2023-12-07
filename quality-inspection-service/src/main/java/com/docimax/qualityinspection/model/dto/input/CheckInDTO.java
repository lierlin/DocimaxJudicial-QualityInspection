package com.docimax.qualityinspection.model.dto.input;

import lombok.Data;

/**
 * @author : lierlin
 * @className : ClientController
 * @description : 客户端(应用)管理
 * @date : 2023/12/5 13:33
 */
@Data
public class CheckInDTO {
    private String bathId;
    private String picId;
}
