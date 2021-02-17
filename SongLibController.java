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
		Song song1 = new Song("Fear", "Kendrick");
		Song song2 = new Song("10cc", "Biggie", "1995", "Ready to Die");
		Song song3 = new Song("Let Down", "Radiohead", "1997", "Ok Computer");
		Song song4 = new Song("Airbag", "Radiohead", "1997", "Ok Computer");
		
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
	 * when adding name, artist, year combo or name, artist, album combo only info saved is name and artist
	 * edit option
	 * delete option: if delete last part in list the selection box needs to go blank
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
						obsList.add(new Song(title.getText(), artist.getText()));
					}else {
						obsList.add(new Song(title.getText(), artist.getText(),
								album.getText(), year.getText()));
					}
				}
			}else {
				//Not sure how we want to do this but tell user that song already exists
				
				
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
				newSong.setYear(year.getText());
			}
			if(!isDuplicate(newSong.getName(),newSong.getArtist(),obsList)) {
				int index = listView.getSelectionModel().getSelectedIndex();
				obsList.remove(index);
				obsList.add(newSong);
				listView.getSelectionModel().select(index);
			}else {
				//Tell user song trying to edit already exists
			}
		}
		else{
			
			//Tell user list is empty
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
