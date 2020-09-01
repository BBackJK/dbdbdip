package shop.dbdbdip.back.mapper;

import java.util.List;

import shop.dbdbdip.back.dto.order.OrderForNoMemberCreateDto;
import shop.dbdbdip.back.model.order.OrderForNoMemberResponseModel;

public interface OrderForNoMemberMapper {
	
	int insertOrder(List<OrderForNoMemberCreateDto> orders);
	
	List<OrderForNoMemberResponseModel> selectByOrderNumber(String orderNumber);
}
