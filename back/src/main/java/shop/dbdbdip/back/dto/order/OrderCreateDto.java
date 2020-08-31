package shop.dbdbdip.back.dto.order;

public class OrderCreateDto {
	
	private String orderNumber;
	private int orderQuantity;
	private String totalPrice;
	private int user_id;
	private int product_id;
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public int getOrderQuantity() {
		return orderQuantity;
	}
	public void setOrderQuantity(int orderQuantity) {
		this.orderQuantity = orderQuantity;
	}
	public String getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
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
		return "OrderCreateDto [orderNumber=" + orderNumber + ", orderQuantity=" + orderQuantity + ", totalPrice="
				+ totalPrice + ", user_id=" + user_id + ", product_id=" + product_id + "]";
	}
}
