package shop.dbdbdip.back.service;

import java.util.List;

import shop.dbdbdip.back.dto.product.ProductCreateDto;
import shop.dbdbdip.back.model.product.ProductModel;

public interface ProductService {
	
	ProductModel getById(int id);
	
	List<ProductModel> getAll(String category);
	
	int postProduct(ProductCreateDto product);
}
