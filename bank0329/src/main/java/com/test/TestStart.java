package com.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan // 扫描servlet
public class TestStart {
    public static void main(String[] args) {
        SpringApplication.run(TestStart.class);
    }
}
