package com.docimax.qualityinspection.model.base;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author ZiHeng Wang
 */
@Data
@Accessors(chain = true)
public class CommonPageQo {
    Integer current = 1;
    Integer pageSize = 15;
}