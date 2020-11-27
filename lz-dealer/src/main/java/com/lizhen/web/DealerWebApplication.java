package com.lizhen.web;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude={ DataSourceAutoConfiguration.class })
@EnableDubbo
@DubboComponentScan
@ComponentScan({ "com.lizhen.common","com.lizhen.web" })
public class DealerWebApplication {
    public static void main(String[] args) {
       SpringApplication.run(DealerWebApplication.class);
    }


}
