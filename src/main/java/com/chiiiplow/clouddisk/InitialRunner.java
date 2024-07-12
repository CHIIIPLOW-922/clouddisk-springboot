package com.chiiiplow.clouddisk;

import com.chiiiplow.clouddisk.config.MinioConfig;
import io.minio.MinioClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 初始运行
 *
 * @author yangzhixiong
 * @date 2024/07/04
 */
@Slf4j
@Component
public class InitialRunner implements ApplicationRunner {

    @Autowired
    private MinioClient minioClient;

    @Autowired
    private MinioConfig minioConfig;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (!minioClient.bucketExists(minioConfig.getBucketName())) {
            minioClient.makeBucket(minioConfig.getBucketName());
            log.info("create bucket");
        }
        log.info("bucket ready");
    }
}
