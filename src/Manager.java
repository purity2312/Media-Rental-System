import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Manager {
	
	// attribute
	private ArrayList<Media> mediaObjects;	// array to store media objects
	private String directory;	// directory of the folder
	private File fileslist[];	// used to store all the text files in a folder

	
	// constructor
	public Manager() {
		
		// initialize empty media object list
		mediaObjects = new ArrayList<Media>();
		
	}

	// load all media files from a directory; assumes file name convention starts with the title followed by the id
	public Manager(String directory) throws FileNotFoundException {
		
		this.directory = directory;
		
		// initialize empty media object list
		mediaObjects = new ArrayList<Media>();
		
		// create a file object for the directory
		File directoryPath = new File(directory);
		
		// get a list of all files and directories
		fileslist = directoryPath.listFiles();

		// local variables
		Media media = null;
		String line = null;
		Scanner scan = null;

		
		// Process each media file
		for (File file: fileslist) {
			// open and read the line
			scan = new Scanner(file);
			line = scan.nextLine();
			
			// search information in the file
			String mediaType = line.substring(line.indexOf(""), line.indexOf(" "));
			int id = Integer.parseInt(line.substring(line.indexOf("id=") + 3, line.indexOf(",")));
			String title = line.substring(line.indexOf("title=") + 6, line.indexOf(", year"));
			Boolean isAvailable = Boolean.parseBoolean(line.substring(line.indexOf("available=") + 10, line.indexOf("]")));
			
			// search different information based on the media type
			if (mediaType.equalsIgnoreCase("EBook")) {
				int year = Integer.parseInt(line.substring(line.indexOf("year=") + 5, line.indexOf(", chapters")));
				int chapters = Integer.parseInt(line.substring(line.indexOf("chapters=") + 9, line.indexOf(", available")));
				media = new EBook(id, title, year, chapters, isAvailable);
				
			} else if (mediaType.equalsIgnoreCase("MovieDVD")) {
				int year = Integer.parseInt(line.substring(line.indexOf("year=") + 5, line.indexOf(", size")));
				double size = Double.parseDouble(line.substring(line.indexOf("size=") + 5, line.indexOf("MB")));
				media = new MovieDVD(id, title, year, size, isAvailable);
				
			} else if (mediaType.equalsIgnoreCase("MusicCD")) {
				int year = Integer.parseInt(line.substring(line.indexOf("year=") + 5, line.indexOf(", length")));
				int length = Integer.parseInt(line.substring(line.indexOf("length=") + 7, line.indexOf("min")));
				media = new MusicCD(id, title, year, length, isAvailable);
			}
			
			mediaObjects.add(media);


		}
		
		
	}
	

	// method to load Media objects
	public void loadMedia(String directory) throws IOException {
		PrintWriter out = null;
		
		// for all media objects create files using the media title and id value as the filename
		for (int i=0; i< mediaObjects.size(); i++) {
			String filename = directory + "/" + mediaObjects.get(i).getTitle() + "-" + mediaObjects.get(i).getId() + ".txt";
			out = new PrintWriter(new FileWriter(filename));
			out.println(mediaObjects.get(i).toString());
			out.flush();
			out.close();
		}
		
	}
	
	// method to add new Media object to its Media list
	public void addMedia(Media media) {
		mediaObjects.add(media);
	}
	
	// method to find all media objects for a specific title
	public ArrayList<String> findMedia(String title) throws FileNotFoundException {
		
		// variables
		Scanner scan = null;
		String line;
		ArrayList<String> matchingMedia = new ArrayList<>();
		
		// parse files
		for (File file : fileslist) {
			if (file.getName().contains(title)) {
				scan = new Scanner(file);
				line = scan.nextLine();
				matchingMedia.add(line);
			}

		}
		return matchingMedia;

	}
	
	
	// method to rent Media based on id (updates rental status on media, updates file, returns rental fee)
	public String rentMedia(int id) throws IOException {
		// variables
		String response = "";
		PrintWriter out = null;
		
		// parse files; let the user rent the media if it is available
		for (int i = 0; i < mediaObjects.size(); i++) {
			if (mediaObjects.get(i).getId() == id) {
				if (mediaObjects.get(i).getIsAvailable() == true) {
					mediaObjects.get(i).setIsAvailable(false);		// update rental status on media
					// update the file
					String filename = directory + "/" + mediaObjects.get(i).getTitle() + "-" + mediaObjects.get(i).getId() + ".txt";
					out = new PrintWriter(new FileWriter(filename));
					out.println(mediaObjects.get(i).toString());
					out.flush();
					out.close();
					// return rental fee
					response = String.format("Media was successfully rented. Rental fee = $%.2f", mediaObjects.get(i).calculateRentalFee());
				} else {
					response = "This media is not available.";

				}
			}
							
		}
		return response;
	}
	

}
