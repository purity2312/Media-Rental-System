 // Name: Lim, Yechan
// Date: 7/11/2023
// CMIS 242/6384
// Description: This program provides a media rental service for the user using GUI menus. The user can load media objects,
//				search for specific media objects, and rent a media.

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;

public class MediaRentalSystem extends JFrame {
	
	// main method opens a GUI menus
	public static void main(String[] args) {

		JFrame window = new MediaRentalSystem();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}
	
	// attributes
	//private JTextArea text;
	private JFileChooser fileDialog;	// file dialog for use in loading media objects
	private File selectedFile;		// file directory for use in loading media and finding media objects
	private Manager mgt;		// manager object to load media objects
	private Manager mgt2;		// manager to do finding, renting, and updating media objects
	
	public MediaRentalSystem() {
		
		// create a window with a menu bar
		super("Welcome to Media Rental System");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(400, 200);
		setJMenuBar(makeMeues());

		
		// create manager object
		mgt = new Manager();
		
		// create some media objects
		Media ebook = new EBook(123, "Forever Young", 2018, 20, true);
		Media cd = new MusicCD(124, "Beyond Today", 2020, 114, true);
		Media dvd = new MovieDVD(125, "After Tomorrow", 2020, 120, true);
		Media dvd2 = new MovieDVD(126, "Forever Young", 2020, 140, false);
		
		// add media objects to the manager's list
		mgt.addMedia(ebook);
		mgt.addMedia(cd);
		mgt.addMedia(dvd);
		mgt.addMedia(dvd2);
		
		
	}
	
	// make a menu bar containing menu items including loading, finding, and renting media object
	private JMenuBar makeMeues() {
		
		ActionListener listener = new ActionListener() {	
			// action listener for menu items
			public void actionPerformed(ActionEvent e) {
				String cmd = e.getActionCommand();
				if (cmd.equals("Load Media object..."))
					loadMedia();
				else if (cmd.equals("Find Media object..."))
					findMedia();
				else if (cmd.equals("Rent Media object..."))
					rentMedia();
				else if (cmd.equals("Quit"))
					quit();
			}

		};
		
		// create a menu
		JMenu fileMenu = new JMenu("Menu");
		
		// create menu items and add them to the menu
		JMenuItem loadCmd = new JMenuItem("Load Media object...");
		loadCmd.addActionListener(listener);
		fileMenu.add(loadCmd);
		
		JMenuItem findCmd = new JMenuItem("Find Media object...");
		findCmd.addActionListener(listener);
		fileMenu.add(findCmd);
		
		JMenuItem rentCmd = new JMenuItem("Rent Media object...");
		rentCmd.addActionListener(listener);
		fileMenu.add(rentCmd);
		
		fileMenu.addSeparator();
		
		JMenuItem quitCmd = new JMenuItem("Quit");
		quitCmd.addActionListener(listener);
		fileMenu.add(quitCmd);

		JMenuBar bar = new JMenuBar();
		bar.add(fileMenu);
		return bar;
		
	}
	
	// method to load media
	private void loadMedia() {
		// set file dialog for the user to choose which directory to load data
		if (fileDialog == null)
			fileDialog = new JFileChooser();
		fileDialog.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fileDialog.setDialogTitle("Open");
		fileDialog.setSelectedFile(null);	// no file is initially selected
		int option = fileDialog.showOpenDialog(this);	
		if (option != JFileChooser.APPROVE_OPTION)
			return;		// user canceled or closed the dialog box
		selectedFile = fileDialog.getSelectedFile();

		try {
			// load media objects in the selected directory
			mgt.loadMedia(String.valueOf(selectedFile));
			
		} catch (IOException e) {
			e.printStackTrace();	// print trace if there are issues
		}
		
	}
	
	// method to search for the media object with the specific title
	private void findMedia() {
		
		// variables
		ArrayList<String> result;	// array to store media objects found
		String output = "";		// this variable will be updated after searching media objects
		JFrame subWindow = new JFrame();		// frame for the message dialog
		
		// prompt the user for the title
		String input = JOptionPane.showInputDialog("Enter the title");

		try {
			// create manager object finding media objects
			mgt2 = new Manager(String.valueOf(selectedFile));
			result = mgt2.findMedia(input);	
			
			// update output variable
			for (int i = 0; i < result.size(); i++) {
				output += result.get(i) + "\n";
			}
			if (output.isEmpty() || output.isBlank())
				output = "There is no media with this title: " + input;
			JOptionPane.showMessageDialog(subWindow, output);	// show the output

		} catch (NullPointerException e) {
			// pop a message dialog if the user closes the input dialog or typed a title that is not in the database
			JOptionPane.showMessageDialog(subWindow, "Invalid title value");	
		} catch (FileNotFoundException e2) {
			e2.printStackTrace();	// print trace if there are any other issues
		} 

	}
	
	// method to rent media objects based on the id
	private void rentMedia() {
		
		JFrame message = new JFrame();	// frame for the message dialog

		
		
		try {
			// prompt the user for the id
			String input = JOptionPane.showInputDialog("Enter the id");
			// change data type from string to integer
			int id = Integer.parseInt(input);
			// create manager object renting media objects
			mgt2 = new Manager(String.valueOf(selectedFile));
			String result = mgt2.rentMedia(id);
			if (result.isEmpty() || result.isBlank())
				result = " The media object id=" + id + " is not found";
			JOptionPane.showMessageDialog(message, result);		// show the output
		} catch (IOException e) {
			e.printStackTrace();	// print trace if there are any other issues
		} catch (NullPointerException e2) {
			JOptionPane.showMessageDialog(message, "No directory selected");
		} catch (NumberFormatException e3) {
			// when the user close the window by clicking X
			JOptionPane.showMessageDialog(message, "Invalid id value");		
		}
	}
	
	// method to terminate the program when the user clicks quit
	private void quit() {
		System.exit(0);
	}

	
}
