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
		
		obsList = FXCollections.observableArrayList(song1, song2);
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
		}else if(b == delete) {
			int index = listView.getSelectionModel().getSelectedIndex();
			obsList.remove(index);
		}
	}
}
