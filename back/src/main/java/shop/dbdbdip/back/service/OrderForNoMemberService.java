package shop.dbdbdip.back.service;

import java.util.List;

import shop.dbdbdip.back.dto.order.OrderForNoMemberCreateDto;

public interface OrderForNoMemberService {
	
	int postOrder(List<OrderForNoMemberCreateDto> orders);

}
