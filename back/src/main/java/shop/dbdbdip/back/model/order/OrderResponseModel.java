package shop.dbdbdip.back.model.order;

import java.util.Date;

import shop.dbdbdip.back.model.product.ProductModel;
import shop.dbdbdip.back.model.user.UserResponseModel;

public class OrderResponseModel {
	private int id;
	private String orderNumber;
	private int orderQuantity;
	private String totalPrice;
	private String status;
	private Date createdAt;
	private Date updatedAt;
	private Date deletedAt;
	private UserResponseModel user;
	private ProductModel product;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	public Date getDeletedAt() {
		return deletedAt;
	}
	public void setDeletedAt(Date deletedAt) {
		this.deletedAt = deletedAt;
	}
	public UserResponseModel getUser() {
		return user;
	}
	public void setUser(UserResponseModel user) {
		this.user = user;
	}
	public ProductModel getProduct() {
		return product;
	}
	public void setProduct(ProductModel product) {
		this.product = product;
	}
	
}
