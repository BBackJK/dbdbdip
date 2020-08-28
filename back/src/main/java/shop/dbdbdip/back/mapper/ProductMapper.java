package shop.dbdbdip.back.mapper;

import java.util.List;

import shop.dbdbdip.back.dto.product.ProductCreateDto;
import shop.dbdbdip.back.model.product.ProductModel;

public interface ProductMapper {
	
	ProductModel selectById(int id); 
	
	List<ProductModel> selectAll(String category);
	
	int insertProduct(ProductCreateDto product);
}
