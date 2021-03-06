package shop.dbdbdip.back.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import shop.dbdbdip.back.dto.order.OrderForNoMemberCreateDto;
import shop.dbdbdip.back.mapper.OrderForNoMemberMapper;
import shop.dbdbdip.back.model.order.OrderForNoMemberResponseModel;

@Service
public class OrderForNoMemberServiceImpl implements OrderForNoMemberService {

	@Autowired
	private OrderForNoMemberMapper orderForNoMemberMapper;
	
	@Override
	public int postOrder(List<OrderForNoMemberCreateDto> orders) {
		
		System.out.println(orders);
		
		int result = orderForNoMemberMapper.insertOrder(orders);
		
		return result;
	}

	@Override
	public List<OrderForNoMemberResponseModel> getByOrderNumber(String orderNumber) {
		
		List<OrderForNoMemberResponseModel> orders = orderForNoMemberMapper.selectByOrderNumber(orderNumber);
		
		return orders;
	}

	
}
