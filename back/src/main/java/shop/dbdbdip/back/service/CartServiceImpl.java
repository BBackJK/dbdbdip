package shop.dbdbdip.back.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import shop.dbdbdip.back.dto.cart.CartCreateDto;
import shop.dbdbdip.back.mapper.CartMapper;
import shop.dbdbdip.back.model.cart.CartDeleteModel;
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

	@Override
	public int deleteCart(int id) {
		
		CartDeleteModel deleted = new CartDeleteModel();
		
		deleted.setId(id);
		deleted.setDeletedAt(this.getDate());
			
		int result = cartMapper.deleteCart(deleted);
		
		return result;
	}
	
	private String getDate() {
		
		Date today = new Date();
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		String date = format.format(today);
		
		return date;
	}
}
