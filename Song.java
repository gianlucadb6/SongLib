package model;

import java.io.*;

import javafx.collections.*;

public class Song {

	private String name;
	private String artist;
	private String album;
	private int year;


	//Constructor 
	public Song(String name, String artist, String album, int year){
		this.name = name;
		this.artist = artist;
		this.album = album;
		this.year = year;
	}

	//Constructor for only name and artist
	public Song(String name, String artist){
		this(name, artist, "", 0);
	}

	public String toString() {
		if(!this.album.equals("") && this.year != 0) {
			return "Title: " + this.name + "\nArtist: " + this.artist + 
					"\nAlbum: " + this.album + "\nYear: " + this.year;
		}else {
			return "Title: " + this.name + "\nArtist: " + this.artist;
		}
	}

	public String getName() {
		return this.name;
	}

	public String getArtist() {
		return this.artist;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String listDisplay() {
		return this.name + ", " + this.artist;
	}

	public void writeToFile() {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("./src/model/library.txt"));
			String info = this.name+", "+this.artist+", "+this.album+", "+this.year+"\n";
			writer.write(info);
			writer.flush();
			writer.close();
		}catch (IOException e){
			System.out.println("invalid file path.");
		}
	}

	public static void read(ObservableList list) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader("./src/model/library.txt"));
			String line = reader.readLine();
			while(line != null) {
				String[] s = line.split(", ");
				list.add(new Song(s[0], s[1], s[2], Integer.parseInt(s[3])));
				line = reader.readLine();
			}
			reader.close();
		}catch(IOException e) {
			System.out.println("invalid file path");
		}
	}
}
