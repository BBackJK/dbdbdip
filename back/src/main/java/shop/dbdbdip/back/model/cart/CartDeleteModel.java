package shop.dbdbdip.back.model.cart;

public class CartDeleteModel {
	
	private int id;
	private String deletedAt;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDeletedAt() {
		return deletedAt;
	}
	public void setDeletedAt(String deletedAt) {
		this.deletedAt = deletedAt;
	}
	
}
