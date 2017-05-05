package com.study.springcloud;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author mushui
 * @created 2017-05-05 下午12:05
 */
@SpringBootApplication
public class Application {

	public static void main(String[] args){
		new SpringApplicationBuilder(Application.class).run(args);
	}
}
