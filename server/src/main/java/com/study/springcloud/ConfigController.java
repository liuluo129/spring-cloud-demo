package com.study.springcloud;

import javax.sql.DataSource;

import com.netflix.config.AbstractPollingScheduler;
import com.netflix.config.DynamicConfiguration;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.config.FixedDelayPollingScheduler;
import com.netflix.config.PolledConfigurationSource;
import com.netflix.config.sources.JDBCConfigurationSource;
import org.apache.commons.configuration.AbstractConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mushui
 * @created 2017-05-10 上午11:25
 */
@RestController
public class ConfigController {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private DataSource dataSource;

	@Bean
	public PolledConfigurationSource source() {
		return new JDBCConfigurationSource(dataSource, "select name, value from params", "name", "value");
	}

	@Bean
	public AbstractPollingScheduler scheduler() {
		return new FixedDelayPollingScheduler(10000, 5000, false);
	}

	@Bean
	public AbstractConfiguration dynamicConfiguration(PolledConfigurationSource source, AbstractPollingScheduler scheduler) {
		return new DynamicConfiguration(source, scheduler);
	}

	@Value("${name}")
	private String name;


	@RequestMapping(value = "/name.do", method = RequestMethod.GET)
	public String configName() {
		return name;
	}

	@GetMapping("/value")
	@ResponseBody
	public String name() {
		return DynamicPropertyFactory.getInstance().getStringProperty("name", "not found").getValue();
	}


	@GetMapping("/change")
	@ResponseBody
	public String change(String value) {
		jdbcTemplate.update("update params set value=? where name=?", value, "name");
		return "ok";
	}
}
