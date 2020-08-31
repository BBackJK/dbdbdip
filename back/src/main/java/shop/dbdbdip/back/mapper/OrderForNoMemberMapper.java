package shop.dbdbdip.back.mapper;

import java.util.List;

import shop.dbdbdip.back.dto.order.OrderForNoMemberCreateDto;

public interface OrderForNoMemberMapper {
	
	int insertOrder(List<OrderForNoMemberCreateDto> orders);
}
