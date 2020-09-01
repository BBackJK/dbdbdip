package shop.dbdbdip.back.model.order;

public class OrderGetByDateModel {
	private String searchDate;
	private int userId;
	public String getSearchDate() {
		return searchDate;
	}
	public void setSearchDate(String searchDate) {
		this.searchDate = searchDate;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
}
