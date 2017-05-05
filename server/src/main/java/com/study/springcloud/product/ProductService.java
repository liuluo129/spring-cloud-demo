package com.study.springcloud.product;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author mushui
 * @created 2017-05-05 下午12:16
 */
@Service
public class ProductService {

	@Autowired
	private com.study.springcloud.product.ProductDAO productDAO;

	public Product getProductById(int id) {
		return productDAO.getProductById(id);
	}

	public List<Product> listProduct(List<Integer> idList) {
		return idList.stream()
					   .map(id -> getProductById(id))
					   .filter(product -> product != null)
					   .collect(Collectors.toList());
	}
}
