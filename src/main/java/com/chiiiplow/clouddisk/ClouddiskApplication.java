package com.chiiiplow.clouddisk;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * CloudDisk应用
 *
 * @author CHIIIPLOW
 * @date 2024/06/02
 */
@MapperScan(basePackages = "com.chiiiplow.clouddisk.dao")
@SpringBootApplication(scanBasePackages = "com.chiiiplow.clouddisk")
public class ClouddiskApplication {
    public static void main(String[] args) {
        SpringApplication.run(ClouddiskApplication.class);
    }
}
