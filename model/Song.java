package model;

public class Song {

	private String name;
	private String artist;
	private String album;
	private String year;
	
	//Constructor 
	public Song(String name, String artist, String album, String year){
		this.name = name;
		this.artist = artist;
		this.album = album;
		this.year = year;
	}
	
	//Constructor for only name and artist
	public Song(String name, String artist){
		this(name, artist, "", "");
	}

	

}
