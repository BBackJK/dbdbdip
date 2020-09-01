package shop.dbdbdip.back.service;

import java.util.List;

import shop.dbdbdip.back.dto.order.OrderForNoMemberCreateDto;
import shop.dbdbdip.back.model.order.OrderForNoMemberResponseModel;

public interface OrderForNoMemberService {
	
	int postOrder(List<OrderForNoMemberCreateDto> orders);
	
	List<OrderForNoMemberResponseModel> getByOrderNumber(String orderNumber);

}
