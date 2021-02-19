package view;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Song;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;


/*TO DO:
 * alphabetical ordering of list items - done
 * list items don't show all the info, just song name and maybe artist/album - done
 * file that holds on to library info - done
 * edit option - done
 * delete option - done
 * empty list delete - done
 * empty list select - done
 * no item display - done
 * no duplicates (case insensitive?)
 * assure that year is an int? or one arg constructor to check if it is album or year
 * when app is closed, REWRITE everything to an empty file - done
 */

public class SongLibController {
	@FXML ListView<Song> listView;
	@FXML Text display;
	@FXML Button add;
	@FXML Button delete;
	@FXML Button edit;
	@FXML TextField title;
	@FXML TextField artist;
	@FXML TextField album;
	@FXML TextField year;


	private ObservableList<Song> obsList;

	public void start(Stage mainStage) {
		obsList = FXCollections.observableArrayList();
		read(obsList);
		listView.setItems(obsList);
		//Assure the list is not empty so selecting the first song does not throw an error
		//in the case that the lib is empty
		try {
			listView.getSelectionModel().select(0);
			display.setText(listView.getSelectionModel().getSelectedItem().displaySong());
		}catch(Exception e) {
			display.setText("No Item Selected");
		}
		listView
		.getSelectionModel()
		.selectedIndexProperty()
		.addListener(
				(obs, oldVal, newVal) -> 
				showSong(mainStage));

		//When library gets closed, write all currently stored songs to our library file
		mainStage.setOnCloseRequest(new EventHandler<WindowEvent> () {
			public void handle(WindowEvent we) {
				writeToFile(obsList);
				display.setText("Saving Data...");
				mainStage.close();
			}
		});

	}

	//method that displays song info when a song is selected
	private void showSong(Stage mainStage) {
		try {
			display.setText(listView.getSelectionModel().getSelectedItem().displaySong());
		}catch(Exception e) {
			display.setText("No Item Selected");
		}
	}


	//method for taking action when buttons are clicked
	public void convertButton(ActionEvent e) {
		Button b = (Button)e.getSource();
		if(b == add) {
			if(isDuplicate(title.getText(),artist.getText(),obsList) == false) {
				if(title.getText().equals("") || artist.getText().contentEquals("")) {
					if(title.getText().equals("")) {
						title.setPromptText("Enter a a valid title.");
					}
					if(artist.getText().contentEquals("")) {
						artist.setPromptText("Enter a valid artist.");					
					}
				}else {
					//If only name and artist filled
					if(album.getText().equals("") && year.getText().equals("")) {
						Song newSong = new  Song(title.getText(), artist.getText());
						addSong(newSong,obsList);
						listView.getSelectionModel().select(getIndex(newSong,obsList));
					}
					//If only name artist and album are filled
					else if(!album.getText().equals("") && year.getText().equals("")) {
						Song newSong = new Song(title.getText(), artist.getText(),album.getText());
						addSong(newSong,obsList);
						listView.getSelectionModel().select(getIndex(newSong,obsList));
					}
					//If only name artist and year are filled
					else if(album.getText().equals("") && !year.getText().equals("")) {
						if(isValidYear(year.getText())) {
							Song newSong = new Song(title.getText(), artist.getText(),album.getText(), Integer.parseInt(year.getText()));
							addSong(newSong,obsList);
							listView.getSelectionModel().select(getIndex(newSong,obsList));
						}else {
							Alert alert = new Alert(AlertType.WARNING);
							alert.setTitle("Invalid Year");
							alert.setContentText("You've entered an invalid year. Year must be between 1000 and 2021");
							alert.showAndWait();
						}
						
						
					}
					//If all fields are filed
					else {

						Song newSong = new Song(title.getText(), artist.getText(),
								album.getText(), Integer.parseInt(year.getText()));
						addSong(newSong,obsList);
						listView.getSelectionModel().select(getIndex(newSong,obsList));

					}
				}
			}else {
				//Not sure how we want to do this but tell user that song already exists
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Duplicate");
				alert.setContentText("Song you've entered is already in you library");
				alert.showAndWait();

			}
		//Delete button
		}else if(b == delete) {
			if(obsList.isEmpty() == false) {
				int index = listView.getSelectionModel().getSelectedIndex();
				if(obsList.size() == 1) {
					listView.getSelectionModel().clearSelection();
					obsList.remove(index);
				}else if((obsList.size()-1) == index) {
					listView.getSelectionModel().select(index-1);
					obsList.remove(index);

				}else {
					listView.getSelectionModel().select(index+1);
					obsList.remove(index);
				}
			}else {
				//Not sure how we want to do this but tell user list is empty
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Empty List");
				alert.setContentText("You have no songs in your library");
				alert.showAndWait();
			}
		}
		title.clear();
		artist.clear();
		album.clear();
		year.clear();
	}
	//Edit Button
	public void editButton(ActionEvent e) {
		if(obsList.isEmpty() == false) {	
			Song oldSong = listView.getSelectionModel().getSelectedItem();
			int index = listView.getSelectionModel().getSelectedIndex();
			Song newSong = null;
			//Checks if your is entered/valid
			if(!year.getText().equals("")) {
				if(isValidYear(year.getText())) {
					 newSong = new Song(title.getText(), artist.getText(),album.getText(), Integer.parseInt(year.getText()));
				}else {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Invalid Year");
					alert.setContentText("You've entered an invalid year. Year must be between 1000 and 2021");
					alert.showAndWait();
				}
			}else {
				 newSong = new Song(title.getText(), artist.getText(),album.getText());
			}
			//Gets correct values for edited song
			if(title.getText().equals("")){
				newSong.setName(oldSong.getName());
			}
			if(artist.getText().equals("")){
				newSong.setArtist(oldSong.getArtist());
			}
			if(album.getText().equals("")) {
				newSong.setAlbum(oldSong.getAlbum());
			}
			if(Integer.parseInt(year.getText()) == 0) {
				newSong.setYear(oldSong.getYear());
			}

			//Edit only selected song year and/or album
			if(title.getText().equals("") && artist.getText().equals("")) {
				obsList.remove(index);
				addSong(newSong,obsList);
				listView.getSelectionModel().select(getIndex(newSong,obsList));

			}
			//Edit atleast name or artist
			else if(!isDuplicate(newSong.getName(),newSong.getArtist(), obsList)) {
				obsList.remove(index);
				addSong(newSong,obsList);
				listView.getSelectionModel().select(getIndex(newSong,obsList));
			}else {
				//Tell user song already exists
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Song Already Exists");
				alert.setContentText("Song your trying to change already exists");
				alert.showAndWait();
			}

		}
		else{
			//Tell user list is empty
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Empty List");
			alert.setContentText("You have no songs in your library");
			alert.showAndWait();
		}
		listView.refresh();
		title.clear();
		artist.clear();
		album.clear();
		year.clear();
	}

	//Places song in correct position in list
	public static void addSong(Song newSong, ObservableList<Song> songList ) {
		if(songList.isEmpty()) {
			songList.add(newSong);
			return;
		}
		if(songList.size() == 1) {
			if(isBefore(newSong, songList.get(0))) {
				songList.add(0, newSong);
				return;
			}else {
				songList.add(newSong);
				return;
			}
		}
		if(isBefore(newSong,songList.get(0))) {
			songList.add(0, newSong);
			return;
		}
		for(int index = 1; index < songList.size(); index++) {
			if(!isBefore(newSong,songList.get(index-1)) && isBefore(newSong,songList.get(index))) {
				songList.add(index, newSong);
				return;
			}

		}
		songList.add(newSong);
	}

	//Checks whether song is alphabetically before or after another song
	public static boolean isBefore(Song song1, Song song2) {
		String name1 = song1.getName().toLowerCase();
		String name2 = song2.getName().toLowerCase();
		String artist1 = song1.getArtist().toLowerCase();
		String artist2 = song2.getArtist().toLowerCase();

		//Song 1 comes first in alphabet by title
		if(name1.compareTo(name2) < 0) {
			return true;
		}
		//Song 2 comes first in alphabet by title
		else if(name1.compareTo(name2) > 0) {
			return false;

		}else {
			//Song 1 comes first in alphabet by artist
			if(artist1.compareTo(artist2) <  0) {
				return true;
			}
			else {
				return false;
			}
		}

	}


	//Checks if song is in Songlist
	public boolean isDuplicate(String name, String artist, ObservableList<Song> songList) {
		for(int x = 0; x<songList.size(); x++) {
			if(name.compareTo(songList.get(x).getName()) == 0 && artist.compareTo(songList.get(x).getArtist()) == 0) {
				return true;
			}
		}
		return false;
	}

	public int getIndex(Song song, ObservableList<Song> songList) {
		for(int x = 0; x<songList.size(); x++) {
			if(song.getName().compareTo(songList.get(x).getName()) == 0 && song.getArtist().compareTo(songList.get(x).getArtist()) == 0) {
				return x;
			}
		}
		return -1;
	}

	//Write function that writes all currently stored 
	public void writeToFile(ObservableList<Song> list) {
		try {
			FileWriter writer = new FileWriter("./src/model/library.txt");
			for(Song i : list) {
				String info = i.getName()+", "+i.getArtist()+", "+i.getAlbum()+", "+i.getYear()+"\n";
				writer.write(info);
				writer.flush();
			}
			writer.close();
		}catch (IOException e){
			System.out.println("invalid file path.");
		}
	}

	//Read function to load songs that have been stored in library file
	//Used to restore data from previous sessions
	public static void read(ObservableList<Song> list) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader("./src/model/library.txt"));
			String line = reader.readLine();
			while(line != null) {
				String[] s = line.split(", ");
				Song song = new Song(s[0], s[1], s[2], Integer.parseInt(s[3]));
				addSong(song, list);
				line = reader.readLine();
			}
			reader.close();
		}catch(IOException e) {
			System.out.println("invalid file path");
		}
	}
	
	public boolean isValidYear(String year) {
	    try {
	    	Integer.parseInt(year);
	    	if(Integer.parseInt(year) > 999 && Integer.parseInt(year) <= 2021) {
	    		return true;
	    	}else {
	    		return false;
	    	}
	    } catch (NumberFormatException e) {
	        return false;
	    }
	}
}
