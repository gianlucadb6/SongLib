package view;



import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.Song;
import javafx.event.ActionEvent;


public class SongLibController {
	@FXML ListView<Song> listView;
	@FXML Text display;
	@FXML Button add;
	@FXML Button delete;
	@FXML Button edit;
	@FXML TextField title;
	@FXML TextField artist;
	@FXML TextField year;
	@FXML TextField album;
	
	private ObservableList<Song> obsList;
	
	public void start(Stage mainStage) {
		Song song1 = new Song("10cc", "Biggie", "1995", "Ready to Die");
		Song song2= new Song("Airbag", "Radiohead", "Ok Computer", "1997");
		Song song3 = new Song("Fear", "Kendrick");
		Song song4 = new Song("Let Down", "Radiohead", "Ok Computer", "1997");

		
		obsList = FXCollections.observableArrayList(song1, song2, song3, song4);
		listView.setItems(obsList);
		listView.getSelectionModel().select(0);
		display.setText(listView.getSelectionModel().getSelectedItem().toString());
		
		listView
		.getSelectionModel()
		.selectedIndexProperty()
		.addListener(
				(obs, oldVal, newVal) -> 
				showSong(mainStage));
		

	}
	/*TO DO:
	 * alphabetical ordering of list items
	 * list items don't show all the info, just song name
	 * file that holds on to library info
	 * edit option
	 * delete option - done
	 * empty list select(0)
	 */
	private void showSong(Stage mainStage) {
		display.setText(listView.getSelectionModel().getSelectedItem().toString());
	}

	

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
					if(album.getText().equals("") && year.getText().equals("")) {
						Song newSong = new  Song(title.getText(), artist.getText());
						addSong(newSong,obsList);
						listView.getSelectionModel().select(getIndex(newSong,obsList));
					}else {
						
						Song newSong = new Song(title.getText(), artist.getText(),
								album.getText(), year.getText());
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
	}
	
	//Edit Button
	public void editButton(ActionEvent e) {
		
		if(obsList.isEmpty() == false) {	
			Song oldSong = listView.getSelectionModel().getSelectedItem();
			Song newSong = new Song(title.getText(), artist.getText(),album.getText(),year.getText());
			int index = listView.getSelectionModel().getSelectedIndex();
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
			if(year.getText().equals("")) {
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
}

