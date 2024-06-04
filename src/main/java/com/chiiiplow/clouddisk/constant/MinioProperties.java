package com.chiiiplow.clouddisk.constant;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Minio 属性
 *
 * @author yangzhixiong
 * @date 2024/06/04
 */
@Data
@ConfigurationProperties(prefix = "minio")
@Component
public class MinioProperties implements Serializable {

    private String accessKey;
    private String secretKey;
    private String bucketName;
    private String host;

}
