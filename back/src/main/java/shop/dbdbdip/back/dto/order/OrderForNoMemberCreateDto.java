package shop.dbdbdip.back.dto.order;

public class OrderForNoMemberCreateDto {
	private String orderNumber;
	private int orderQuantity;
	private String totalPrice;
	private int product_id;
	private String username;
	private String userEmail;
	private String userPhone;
	private String userZipcode;
	private String userAddress;
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
	public int getProduct_id() {
		return product_id;
	}
	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	public String getUserZipcode() {
		return userZipcode;
	}
	public void setUserZipcode(String userZipcode) {
		this.userZipcode = userZipcode;
	}
	public String getUserAddress() {
		return userAddress;
	}
	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}
	@Override
	public String toString() {
		return "OrderForNoMemberCreateDto [orderNumber=" + orderNumber + ", orderQuantity=" + orderQuantity
				+ ", totalPrice=" + totalPrice + ", product_id=" + product_id + ", username=" + username
				+ ", userEmail=" + userEmail + ", userPhone=" + userPhone + ", userZipcode=" + userZipcode
				+ ", userAddress=" + userAddress + "]";
	}
	
	
}
