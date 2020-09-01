package shop.dbdbdip.back.service;

import java.util.List;

import shop.dbdbdip.back.dto.order.OrderCreateDto;
import shop.dbdbdip.back.model.order.OrderGetByDateModel;
import shop.dbdbdip.back.model.order.OrderResponseModel;

public interface OrderService {
	
	int postOrder(List<OrderCreateDto> orders);
	
	List<OrderResponseModel> getById(int userId);
	
	List<OrderResponseModel> getByDate(OrderGetByDateModel order);

}
