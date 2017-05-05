package com.study.springcloud.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mushui
 * @created 2017-05-05 下午12:01
 */
@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private ProductService productService;

	@RequestMapping(value = "/getById.do", method = RequestMethod.GET, params = {"id"})
	public Product getProductById(int id) {
		return productService.getProductById(id);
	}
}
