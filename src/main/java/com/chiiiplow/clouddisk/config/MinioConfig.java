package com.chiiiplow.clouddisk.config;

import com.chiiiplow.clouddisk.exception.CustomException;
import io.minio.MinioClient;
import io.minio.errors.MinioException;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;


/**
 * minio 配置
 *
 * @author yangzhixiong
 * @date 2024/06/04
 */
@Configuration
@ConfigurationProperties(prefix = "minio")
@Data
public class MinioConfig implements Serializable {

    private String accessKey;
    private String secretKey;
    private String bucketName;
    private String host;




    @Bean
    public MinioClient minioClient() {
        try {
            return new MinioClient(host, accessKey, secretKey);
        } catch (MinioException e) {
            throw new CustomException("Error creating MinioClient", e);
        }
    }


}
