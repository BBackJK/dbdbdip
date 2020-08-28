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
import shop.dbdbdip.back.dto.product.ProductCreateDto;
import shop.dbdbdip.back.model.product.ProductModel;
import shop.dbdbdip.back.service.ProductService;

@RestController
@CrossOrigin
@RequestMapping("/product")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseMessage getbyId(@RequestParam("id") int id) {
		
		System.out.println(id);
		
		ProductModel product = productService.getById(id);
		
		ResponseMessage message = new ResponseMessage(HttpStatus.OK);
		
		message.add("product", product);
		
		return message;
	}
	
	@RequestMapping(value="/all",method=RequestMethod.GET)
	public ResponseMessage getbyId(@RequestParam("category") String category) {
		
		System.out.println(category);
		
		List<ProductModel> products = productService.getAll(category);
		
		ResponseMessage message = new ResponseMessage(HttpStatus.OK);
		
		message.add("products", products);
		
		return message;
	}
	
	@RequestMapping(value="/register",method=RequestMethod.POST)
	public ResponseMessage post(@RequestBody ProductCreateDto product) {
		
		int result = productService.postProduct(product);
		
		if (result != 1) {
			ResponseMessage message = new ResponseMessage(HttpStatus.CONFLICT);
			
			return message;
		}
		
		ResponseMessage message = new ResponseMessage(HttpStatus.CREATED);
		
		return message;
	}
}
