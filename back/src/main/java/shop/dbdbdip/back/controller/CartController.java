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
import shop.dbdbdip.back.dto.cart.CartCreateDto;
import shop.dbdbdip.back.model.cart.CartModel;
import shop.dbdbdip.back.service.CartService;

@RestController
@CrossOrigin
@RequestMapping("/cart")
public class CartController {

	
	@Autowired
	private CartService cartService;
	
	@RequestMapping(value="/register",method=RequestMethod.POST)
	public ResponseMessage post(@RequestBody CartCreateDto cart) {
		
		System.out.println(cart);
		
		int result = cartService.postCart(cart);
		
		if (result != 1) {
			ResponseMessage message = new ResponseMessage(HttpStatus.CONFLICT);
			
			return message;
		}
		
		ResponseMessage message = new ResponseMessage(HttpStatus.CREATED);
		
		return message;
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseMessage getbyId(@RequestParam("id") int id) {
		
		List<CartModel> carts = cartService.getByUserId(id);
		
		ResponseMessage message = new ResponseMessage(HttpStatus.OK);
		
		message.add("carts", carts);
		
		return message;
	}
}
