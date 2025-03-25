import java.util.Calendar;

public class EBook extends Media {
	
	// attributes
	private int numChapters;
	
	public EBook (int id, String title, int year, int numChapters, Boolean isAvailable){
		super(id, title, year, isAvailable);
		this.numChapters = numChapters;
	}
	
	// get method
	public int getNumChapters() {
		return numChapters;
	}
	
	// set method
	public void setNumChapters(int numChapters) {
		this.numChapters = numChapters;
	}
	
	// method to calculate rental fees
	@Override
	public double calculateRentalFee() {
		double fee = numChapters * 0.1;  // basic fee
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		if (this.getYear() == currentYear) {
			fee += 1;
		} 
		return fee;

	}
	
	// method to send information
	@Override
	public String toString() {
		return "EBook [ id=" + getId() + ", title=" + getTitle() + ", year="
				+ getYear() + ", chapters=" + numChapters + ", available=" + getIsAvailable() + "]";
	}

}
