package com.study.springcloud.search;

import java.util.Collections;
import java.util.List;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author mushui
 * @created 2017-05-05 下午12:17
 */
@RestController
@RequestMapping("/search")
public class SearchController {
	private static final Logger LOGGER = LoggerFactory.getLogger(SearchController.class);

	@Autowired
	@LoadBalanced
	private RestTemplate loadBalanced;

	@RequestMapping(value = "/go.do", method = RequestMethod.GET, params = {"key"})
	@HystrixCommand(fallbackMethod = "fallback")
	public List<Product> go(String key) throws Exception {
		LOGGER.info("search request: {}", key);

		List<Integer> pidList = search(key);
		String url = "http://PRODUCT/product/getById.do?id=" + pidList.get(0);
		return Collections.singletonList(loadBalanced.getForObject(url, Product.class));
	}

	private List<Product> fallback(String key) {
		LOGGER.error("fallback, key: {}", key);
		return Collections.emptyList();
	}

	private List<Integer> search(String key) {
		return Collections.singletonList(1);
	}
}
