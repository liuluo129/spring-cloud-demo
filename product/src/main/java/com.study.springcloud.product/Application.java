package com.study.springcloud.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author mushui
 * @created 2017-05-05 下午12:05
 */
@SpringBootApplication
@EnableEurekaClient
public class Application {


    @Autowired
    private JdbcTemplate jdbcTemplate;

	@Bean
    public CommandLineRunner init() {
        return new CommandLineRunner() {
            @Override
            public void run(String... strings) throws Exception {

                jdbcTemplate.update("CREATE TABLE `wine` (`id` int NOT NULL AUTO_INCREMENT,"
                    + "`name` varchar(255),"
                    + "`price` int,"
                    + "PRIMARY KEY (`id`)\n"
                    + ")");
                jdbcTemplate.update("insert into wine(`name`, price) values('wine1', 10)");
                jdbcTemplate.update("insert into wine(`name`, price) values('wine2', 30)");
            }
        };
    }

	public static void main(String[] args){
		new SpringApplicationBuilder(Application.class).run(args);
	}
}
