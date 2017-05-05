package com.study.springcloud.product;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author mushui
 * @created 2017-05-05 下午12:05
 */
@SpringBootApplication
@EnableEurekaClient
public class Application {

	public static void main(String[] args){
		new SpringApplicationBuilder(Application.class).run(args);
	}
}
