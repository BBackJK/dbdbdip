package shop.dbdbdip.back.service;

import java.util.List;

import shop.dbdbdip.back.dto.cart.CartCreateDto;
import shop.dbdbdip.back.model.cart.CartModel;

public interface CartService {
	
	int postCart(CartCreateDto cart);
	
	List<CartModel> getByUserId(int userId);
}
