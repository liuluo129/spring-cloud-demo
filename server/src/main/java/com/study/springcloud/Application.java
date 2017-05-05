package com.study.springcloud;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

/**
 * @author mushui
 * @created 2017-05-05 下午12:05
 */
@SpringBootApplication
@EnableEurekaClient
public class Application {

	@Bean
	CommandLineRunner runner(DiscoveryClient dc) {
		return args -> {
			dc.getInstances("product")
					.forEach(si -> System.out.println(String.format(
							"Found %s %s:%s", si.getServiceId(), si.getHost(), si.getPort())));
		};
	}

	/**
	 * The load balanced rest template
	 */
	@LoadBalanced
	@Bean
	public RestTemplate loadBalanced() {
		return new RestTemplate();
	}

	@Primary
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	public static void main(String[] args){
		new SpringApplicationBuilder(Application.class).run(args);
	}
}
