package shop.dbdbdip.back.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import shop.dbdbdip.back.dto.cart.CartCreateDto;
import shop.dbdbdip.back.mapper.CartMapper;
import shop.dbdbdip.back.model.cart.CartModel;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private CartMapper cartMapper;
	
	@Override
	public int postCart(CartCreateDto cart) {
		
		CartModel duplicatedCart = cartMapper.checkProductId(cart.getProduct_id());
		
		System.out.println(duplicatedCart);
		
		if (duplicatedCart != null) {
			return 0;
		}
	
		int result = cartMapper.insertCart(cart);
		
		return result;
	}

	@Override
	public List<CartModel> getByUserId(int userId) {
		
		List<CartModel> carts = cartMapper.selectByUserId(userId);
		
		System.out.println(carts);
		
		return carts;
	}

}
