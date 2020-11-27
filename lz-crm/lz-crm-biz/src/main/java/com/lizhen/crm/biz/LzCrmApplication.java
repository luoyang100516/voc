package com.lizhen.crm.biz;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
@MapperScan(basePackages = "com.lizhen.crm.kernel.dao")
@ComponentScan(basePackages = { "com.lizhen"})
@EnableDubbo
public class LzCrmApplication {

    public static void main(String[] args) {
        //不分配端口启动，如需分配端口，去掉
        new SpringApplicationBuilder(LzCrmApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);
        /*SpringApplication.run( LzCrmApplication.class );*/
    }


}
