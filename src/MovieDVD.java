
public class MovieDVD extends Media {
	
	// attribute
	private double size; // in MB
	
	// constructor
	public MovieDVD(int id, String title, int year, double size, Boolean isAvailable) {
		super(id, title, year, isAvailable);
		this.size = size;
	}
	
	// get method 
	public double getSize() {
		return size;
	}
	
	// set method 
	public void setSize(double size) {
		this.size = size;
	}
	
	// method to send information
	@Override
	public String toString() {
		return "MovieDVD [ id=" + getId() + ", title=" + getTitle() + ", year="
				+ getYear() + ", size=" + size + "MB" + ", available=" + getIsAvailable() + "]";
	}

}
