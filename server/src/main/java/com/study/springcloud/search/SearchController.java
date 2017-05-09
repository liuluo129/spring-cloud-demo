package com.study.springcloud.search;

import java.util.Collections;
import java.util.List;

import com.study.springcloud.product.Product;
import com.study.springcloud.product.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mushui
 * @created 2017-05-05 下午12:17
 */
@RestController
@RequestMapping("/search")
public class SearchController {

	private static final Logger LOGGER = LoggerFactory.getLogger(SearchController.class);

	@Autowired
	private ProductService productService;

	@RequestMapping(value = "/go.do", method = RequestMethod.GET, params = {"key"})
	public List<Product> go(String key) {
		LOGGER.info("search request: {}", key);
		return productService.listProduct(search(key));
	}

	private List<Integer> search(String key) {
		return Collections.singletonList(1);
	}
}
