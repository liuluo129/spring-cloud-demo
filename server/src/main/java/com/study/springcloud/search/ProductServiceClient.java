package com.study.springcloud.search;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 使用@FeignClient("product")注解绑定product服务，还可以使用url参数指定一个URL。
 */
@FeignClient(name = "product", fallback = ProductServiceClient.HystrixClientFallback.class)
public interface ProductServiceClient {

	@RequestMapping("/product/getById.do")
	Product getById(@RequestParam("id") int id);

	@Component
	class HystrixClientFallback implements ProductServiceClient {
		private static final Logger LOGGER = LoggerFactory.getLogger(HystrixClientFallback.class);

		@Override
		public Product getById(@RequestParam("id") int id){
			LOGGER.error("fallback, id: {}", id);
			Product product = new Product();
			product.setId(-1);
			return product;
		}
	}
}