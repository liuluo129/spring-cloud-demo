package com.study.springcloud.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 * @author mushui
 * @created 2017-05-05 下午1:41
 */
@Repository
public class ProductDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private RowMapper<Product> productRowMapper = (rs, index) -> {
		Product product = new Product();
		product.setId(rs.getInt("id"));
		product.setName(rs.getString("name"));
		product.setPrice(rs.getInt("price"));
		return product;
	};

	public Product getProductById(int id) {
		try {
			return jdbcTemplate.queryForObject("select * from wine where id=?", productRowMapper, id);
		} catch (EmptyResultDataAccessException ex) {
			return null;
		}
	}
}
