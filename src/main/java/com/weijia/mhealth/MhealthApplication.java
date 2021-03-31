package com.weijia.mhealth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.weijia.mhealth.mapper")
public class MhealthApplication {

	public static void main(String[] args) {
		SpringApplication.run(MhealthApplication.class, args);
	}

}
