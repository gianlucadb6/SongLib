package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Song;
import javafx.event.ActionEvent;


/*TO DO:
 * alphabetical ordering of list items
 * list items don't show all the info, just song name and maybe artist/album
 * file that holds on to library info - done
 * edit option
 * delete option - done
 * empty list delete - done
 * empty list select - done
 * no item display - done
 * no duplicates (case insensitive?)
 * assure that year is an int? or one arg constructor to check if it is album or year
 * when app is closed, REWRITE everything to wiped file
 * 
 */

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
		obsList = FXCollections.observableArrayList();
		Song.read(obsList);
		listView.setItems(obsList);
		if(!obsList.isEmpty()) {
			listView.getSelectionModel().select(0);
			display.setText(listView.getSelectionModel().getSelectedItem().toString());

		}
		listView
		.getSelectionModel()
		.selectedIndexProperty()
		.addListener(
				(obs, oldVal, newVal) -> 
				showSong(mainStage));
		


	}
	
	private void showSong(Stage mainStage) {
		try {
			display.setText(listView.getSelectionModel().getSelectedItem().toString());
		}catch(Exception e) {
			display.setText("No Item Selected");
		}
	}



	public void convertButton(ActionEvent e) {
		Button b = (Button)e.getSource();
		if(b == add) {
			if(title.getText().equals("") || artist.getText().contentEquals("")) {
				if(title.getText().equals("")) {
					title.setPromptText("Enter a a valid title.");
				}
				if(artist.getText().contentEquals("")) {
					artist.setPromptText("Enter a valid artist.");					
				}
			}else {
				if(album.getText().equals("") && year.getText().equals("")) {
					obsList.add(new Song(title.getText(), artist.getText()));
				}else {
					try {
					obsList.add(new Song(title.getText(), artist.getText(),
							album.getText(), Integer.parseInt(year.getText())));		
					}catch(Exception ex) {
						album.setPromptText("Enter a valid year.");
					}
				}
			}
			title.setPromptText("Title");
			artist.setPromptText("Artist");
			year.setPromptText("Year");
			album.setPromptText("Album");
		}else if(b == delete) {
			if(listView.getSelectionModel().getSelectedIndex() >= 0) {
				int index = listView.getSelectionModel().getSelectedIndex();
				obsList.remove(index);
				/*
				 * not really needed of try/catch in showSong()
				if(!obsList.isEmpty()) {
					display.setText(listView.getSelectionModel().getSelectedItem().toString());
				}
				*/
			}
		}
	}
	//Edit Button
		public void editButton(ActionEvent e) {
			
			if(obsList.isEmpty() == false) {	
				Song oldSong = listView.getSelectionModel().getSelectedItem();
				Song newSong = oldSong;
				if(!title.getText().equals("")){
					newSong.setName(title.getText());
				}
				if(!artist.getText().equals("")){
					newSong.setArtist(artist.getText());
				}
				if(!album.getText().equals("")){
					newSong.setName(album.getText());
				}
				if(!year.getText().equals("")){
					newSong.setYear(Integer.parseInt(year.getText()));
				}
				if(!isDuplicate(newSong.getName(),newSong.getArtist(),obsList)) {
					int index = listView.getSelectionModel().getSelectedIndex();
					obsList.remove(index);
					addSong(newSong,obsList);
				}else {
					//Tell user song trying to edit already exists
				}
			}
			else{
				
				//Tell user list is empty
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
	}
