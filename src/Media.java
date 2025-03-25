
public abstract class Media {

	// attribute
	private int id;
	private String title;
	private int year;
	private Boolean isAvailable;
	
	// constructor
	public Media (int id, String title, int year, Boolean isRented) {
		this.id = id;
		this.title = title;
		this.year = year;
		this.isAvailable = isRented;
		
	}
	
	// get methods
	public int getId() {
		return id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public int getYear() {
		return year;
	}
	
	public Boolean getIsAvailable() {
		return isAvailable;
	}
	
	// set methods
	public void setTitle(String title) {
		this.title = title;
	}

	public void setYear(int year) {
		this.year = year;
	}
	
	public void setIsAvailable(Boolean isRented) {
		this.isAvailable = isRented;
	}
	
	// method to calculate the rental fee
	public double calculateRentalFee() {
		return 3.50;
	}

}
