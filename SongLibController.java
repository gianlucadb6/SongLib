package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.event.ActionEvent;

public class SongLibController {
	@FXML ListView<String> listview;
	@FXML Button add;
	@FXML Button edit;
	@FXML Button delete;
	
	private ObservableList<String> songList;
	
	public void start() {
		songList = FXCollections.observableArrayList("Fear by Kendrick",
													"10CC by Biggie", 
													"Everybody Loves the Sunshine");
		listview.setItems(songList);
		
	}
	
	public void convertButton(ActionEvent e) {
		Button b = (Button)e.getSource();
		if(b == add) {//add button was pressed
			
		}else if(b == edit) {//edit button was pressed
			
		}else {//delete button was pressed
			
		}
	}
}
