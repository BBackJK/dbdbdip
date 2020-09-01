package shop.dbdbdip.back.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import shop.dbdbdip.back.dto.ResponseMessage;
import shop.dbdbdip.back.dto.order.OrderCreateDto;
import shop.dbdbdip.back.dto.order.OrderForNoMemberCreateDto;
import shop.dbdbdip.back.model.order.OrderForNoMemberResponseModel;
import shop.dbdbdip.back.model.order.OrderGetByDateModel;
import shop.dbdbdip.back.model.order.OrderResponseModel;
import shop.dbdbdip.back.service.OrderForNoMemberService;
import shop.dbdbdip.back.service.OrderService;

@RestController
@CrossOrigin
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private OrderForNoMemberService orderForNoMemberService; 
	
	@RequestMapping(value="/register",method=RequestMethod.POST)
	public ResponseMessage post(@RequestBody List<OrderCreateDto> orders) {
		
		int result = orderService.postOrder(orders);
		
		if (result < 1) {
			ResponseMessage message = new ResponseMessage(HttpStatus.CONFLICT);
			
			return message;
		}
		
		ResponseMessage message = new ResponseMessage(HttpStatus.CREATED);
		
		return message;
	}
	
	@RequestMapping(value="/notuser/register",method=RequestMethod.POST)
	public ResponseMessage postForNoMember(@RequestBody List<OrderForNoMemberCreateDto> orders) {
		
		int result = orderForNoMemberService.postOrder(orders);
		
		if (result < 1) {
			ResponseMessage message = new ResponseMessage(HttpStatus.CONFLICT);
			
			return message;
		}
		
		ResponseMessage message = new ResponseMessage(HttpStatus.CREATED);
		
		return message;
	}
	
	@RequestMapping(value="/notuser", method=RequestMethod.GET)
	public ResponseMessage getbyOrderNumber(@RequestParam("orderNumber") String orderNumber) {
		
		List<OrderForNoMemberResponseModel> orders = orderForNoMemberService.getByOrderNumber(orderNumber);
		
		ResponseMessage message = new ResponseMessage(HttpStatus.OK);
		
		message.add("orders", orders);
		
		return message;
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseMessage getbyId(@RequestParam("id") int userId) {
		
		List<OrderResponseModel> orders = orderService.getById(userId);
		
		ResponseMessage message = new ResponseMessage(HttpStatus.OK);
		
		message.add("orders", orders);
		
		return message;
	}
	
	@RequestMapping(value="/date", method=RequestMethod.GET)
	public ResponseMessage getbyDate(@RequestParam("date") String searchDate, @RequestParam("userId") int userId) {
		
		OrderGetByDateModel order = new OrderGetByDateModel();
		
		order.setSearchDate(searchDate);
		order.setUserId(userId);
		
		List<OrderResponseModel> orders = orderService.getByDate(order);
		
		ResponseMessage message = new ResponseMessage(HttpStatus.OK);
		
		message.add("orders", orders);
		
		return message;
	}
}
