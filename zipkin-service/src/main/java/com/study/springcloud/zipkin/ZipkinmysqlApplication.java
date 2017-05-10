package com.study.springcloud.zipkin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import zipkin.server.EnableZipkinServer;

@SpringBootApplication
@EnableZipkinServer
public class ZipkinmysqlApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZipkinmysqlApplication.class, args);
	}
}
