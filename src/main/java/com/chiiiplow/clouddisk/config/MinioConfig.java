package com.chiiiplow.clouddisk.config;

import com.chiiiplow.clouddisk.constant.MinioProperties;
import io.minio.MinioClient;
import io.minio.errors.MinioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * minio 配置
 *
 * @author yangzhixiong
 * @date 2024/06/04
 */
@Configuration
public class MinioConfig {


    @Autowired
    private MinioProperties minioProperties;


    @Bean
    public MinioClient minioClient() {
        try {
            return new MinioClient(minioProperties.getHost(), minioProperties.getAccessKey(), minioProperties.getSecretKey());
        } catch (MinioException e) {
            throw new RuntimeException("Error creating MinioClient", e);
        }
    }


}
