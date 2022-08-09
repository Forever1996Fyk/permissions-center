package com.ah.cloud.permissions;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2021-12-03 11:56
 **/
@EnableEncryptableProperties
@SpringBootApplication(scanBasePackages = {"com.ah.cloud"})
@MapperScan(basePackages = {"com.ah.cloud.permissions.biz.infrastructure.repository.mapper", "com.ah.cloud.permissions.task.infrastructure.repository.mapper", "com.ah.cloud.permissions.edi.infrastructure.repository.mapper"} )
public class PermissionsApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(PermissionsApiApplication.class, args);
    }
}
