import java.util.Calendar;

public class MusicCD extends Media {
	
	// attributes
	private int length; // in minutes
	
	// constructor
	public MusicCD(int id, String title, int year, int length, Boolean isAvailable) {
		super(id, title, year, isAvailable);
		this.length = length;
	}
	
	// get method
	public int getLength() {
		return length;
	}
	
	// set method
	public void setLength(int length) {
		this.length = length;
	}
	
	// method to calculate the rental fee
	@Override
	public double calculateRentalFee() {
		double fee = length * 0.02;		// basic fee
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		if (this.getYear() == currentYear) {
			fee += 1;	// add $1.00 fee
		}
		
		return fee;

	}
	
	// method to send information
	@Override
	public String toString() {
		return "MusicCD [ id=" + getId() + ", title=" + getTitle() + ", year="
				+ getYear() + ", length=" + length + "min" + ", available=" + getIsAvailable() + "]";
	}


}
