package shop.dbdbdip.back.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import shop.dbdbdip.back.dto.order.OrderCreateDto;
import shop.dbdbdip.back.mapper.CartMapper;
import shop.dbdbdip.back.mapper.OrderMapper;
import shop.dbdbdip.back.model.cart.CartDeleteModel;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderMapper orderMapper;
	
	@Autowired
	private CartMapper cartMapper;
	
	@Override
	public int postOrder(List<OrderCreateDto> orders) {
		
		int result = orderMapper.insertOrder(orders);
		
		if (result  < 1) {
			return result;
		}
		
		List<CartDeleteModel> carts = new ArrayList<CartDeleteModel>();
		
		for(int i = 0 ; i < orders.size(); i++) {
			CartDeleteModel cart = new CartDeleteModel();
			cart.setId(orders.get(i).getProduct_id());
			cart.setDeletedAt(this.getDate());
			carts.add(cart);
		}	
		
		int deleteResult = cartMapper.bulkDeleteCart(carts);
		
		System.out.println(deleteResult);
		
		return result;
	}
	
	private String getDate() {
		
		Date today = new Date();
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		String date = format.format(today);
		
		return date;
	}

}
