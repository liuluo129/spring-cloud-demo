package com.study.springcloud.product;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mushui
 * @created 2017-05-05 下午12:01
 */
@RestController
@RequestMapping("/product")
@RefreshScope
public class ProductController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

	@Autowired
	private ProductService productService;

	@Value("${name}")
	private String name;


	@RequestMapping(value = "/name.do", method = RequestMethod.GET)
	public String name() {
		return name;
	}

	@RequestMapping(value = "/getById.do", method = RequestMethod.GET, params = {"id"})
	public Product getProductById(int id) {
		LOGGER.info("get by id: {}", id);
		return productService.getProductById(id);
	}
}
