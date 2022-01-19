package com.ah.cloud.permissions.test;

import com.google.common.collect.Lists;
import org.apache.logging.log4j.util.Strings;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


/**
 * @program: ypsx-ofc
 * @description: 基础测试类, 如果需要配置SpringBoot 可以继承这个类, 如果只是单纯代码测试, 不需要继承
 * @author: YuKai Fan
 * @create: 2021-09-06 14:36
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class BaseTest {

}
