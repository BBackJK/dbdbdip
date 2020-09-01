package shop.dbdbdip.back.mapper;

import java.util.List;

import shop.dbdbdip.back.dto.order.OrderCreateDto;
import shop.dbdbdip.back.model.order.OrderGetByDateModel;
import shop.dbdbdip.back.model.order.OrderResponseModel;

public interface OrderMapper {
	
	int insertOrder(List<OrderCreateDto> orders);
	
	List<OrderResponseModel> selectById(int userId);
	
	List<OrderResponseModel> selectByDate(OrderGetByDateModel order);
}
