package shop.dbdbdip.back.model.cart;

import java.util.Date;

import shop.dbdbdip.back.model.product.ProductModel;
import shop.dbdbdip.back.model.user.UserResponseModel;

public class CartModel {
	private int id;
	private int orderQuantity;
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
	public int getOrderQuantity() {
		return orderQuantity;
	}
	public void setOrderQuantity(int orderQuantity) {
		this.orderQuantity = orderQuantity;
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
	@Override
	public String toString() {
		return "CartModel [id=" + id + ", orderQuantity=" + orderQuantity + ", createdAt=" + createdAt + ", updatedAt="
				+ updatedAt + ", deletedAt=" + deletedAt + ", user=" + user + ", product=" + product + "]";
	}
	
	
	
	
}
