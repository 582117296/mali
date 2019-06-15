package com.gjt.mali;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.gjt.mali.mapper")
public class MaliApplication {

    public static void main(String[] args) {
        SpringApplication.run(MaliApplication.class, args);
    }

}
