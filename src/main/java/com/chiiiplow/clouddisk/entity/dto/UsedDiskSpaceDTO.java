package com.chiiiplow.clouddisk.entity.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 已用磁盘空间
 *
 * @author CHIIIPLOW
 * @date 2024/06/30
 */
@Data
public class UsedDiskSpaceDTO implements Serializable {

    private float usedSpaceRate;

    private String usedDiskSpace;

    private String totalDiskSpace;




}
