package shop.dbdbdip.back.mapper;

import java.util.List;

import shop.dbdbdip.back.dto.order.OrderCreateDto;

public interface OrderMapper {
	
	int insertOrder(List<OrderCreateDto> orders);
}
