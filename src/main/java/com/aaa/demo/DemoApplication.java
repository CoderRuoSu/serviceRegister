package com.aaa.demo;

import com.aaa.demo.util.MetaDataManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Created by ruosu on 2018/4/26.
 */
@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class,args);
    }

    @Bean
    MetaDataManager metaDataManager() {
        return new MetaDataManager(new String[] {"com.aaa.demo.controller"});
    }
}
