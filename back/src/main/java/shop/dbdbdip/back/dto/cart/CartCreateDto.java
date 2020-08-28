package shop.dbdbdip.back.dto.cart;

public class CartCreateDto {
	private int orderQuantity;
	private int user_id;
	private int product_id;
	public int getOrderQuantity() {
		return orderQuantity;
	}
	public void setOrderQuantity(int orderQuantity) {
		this.orderQuantity = orderQuantity;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getProduct_id() {
		return product_id;
	}
	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}
	@Override
	public String toString() {
		return "CartCreateDto [orderQuantity=" + orderQuantity + ", user_id=" + user_id + ", product_id=" + product_id
				+ "]";
	}
	
	
}
