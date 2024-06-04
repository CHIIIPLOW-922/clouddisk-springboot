package com.chiiiplow.clouddisk.component;

import com.chiiiplow.clouddisk.constant.MinioProperties;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;


/**
 * Minio 组件
 *
 * @author yangzhixiong
 * @date 2024/06/04
 */
@Component
public class MinioComponent {

    @Autowired
    private MinioProperties minioProperties;

    @Autowired
    private MinioClient minioClient;

    public void uploadFile(MultipartFile multipartFile) throws Exception{
        if (!minioClient.bucketExists(minioProperties.getBucketName())) {
            minioClient.makeBucket(minioProperties.getBucketName());
        }

    }
}
