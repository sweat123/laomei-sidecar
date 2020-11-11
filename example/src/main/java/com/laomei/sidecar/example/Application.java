package com.laomei.sidecar.example;

import com.laomei.sidecar.EnableEnhanceSidecar;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author luobo.hwz on 2020/11/11 20:28
 */
@EnableEnhanceSidecar
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
