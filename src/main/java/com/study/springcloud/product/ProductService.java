package com.study.springcloud.product;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

/**
 * @author mushui
 * @created 2017-05-05 下午12:16
 */
@Service
public class ProductService {

	public Product getProductById(int id) {
		Product product = new Product();
		product.setId(id);
		product.setName("wine: " + id);
		product.setPrice(20);
		return product;
	}

	public List<Product> listProduct(List<Integer> idList) {
		return idList.stream()
					   .map(id -> getProductById(id))
					   .collect(Collectors.toList());
	}
}
