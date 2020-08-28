package shop.dbdbdip.back.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import shop.dbdbdip.back.dto.product.ProductCreateDto;
import shop.dbdbdip.back.dto.product.ProductResponseDto;
import shop.dbdbdip.back.mapper.ProductMapper;
import shop.dbdbdip.back.model.product.ProductModel;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductMapper productMapper;
	
	@Override
	public ProductModel getById(int id) {
		
		ProductModel product = productMapper.selectById(id);
		
		return product;
	}

	@Override
	public List<ProductModel> getAll(String category) {
		
		List<ProductModel> productList = productMapper.selectAll(category);
		
		System.out.println(productList);
		
		return productList;
	}

	@Override
	public int postProduct(ProductCreateDto product) {
		
		int result = productMapper.insertProduct(product);
		
		return result;
	}

}
