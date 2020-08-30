package shop.dbdbdip.back.mapper;

import java.util.List;

import shop.dbdbdip.back.dto.cart.CartCreateDto;
import shop.dbdbdip.back.model.cart.CartDeleteModel;
import shop.dbdbdip.back.model.cart.CartModel;

public interface CartMapper {
	
	int insertCart(CartCreateDto cart);
	
	CartModel checkProductId(int productId);
	
	int deleteCart(CartDeleteModel cart);
 
	List<CartModel> selectByUserId(int userId);

}
