package com.chiiiplow.clouddisk;

import com.chiiiplow.clouddisk.constant.MinioProperties;
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
    private MinioProperties minioProperties;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (!minioClient.bucketExists(minioProperties.getBucketName())) {
            minioClient.makeBucket(minioProperties.getBucketName());
            log.info("create bucket");
        }
        log.info("bucket ready");
    }
}
