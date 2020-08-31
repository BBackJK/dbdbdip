package shop.dbdbdip.back.service;

import java.util.List;

import shop.dbdbdip.back.dto.order.OrderCreateDto;

public interface OrderService {
	
	int postOrder(List<OrderCreateDto> orders);
}
