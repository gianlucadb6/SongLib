package model;


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
		this(name, artist, "", -1);
	}

	//Constructor for name, artist and album
	public Song(String name, String artist, String album){
		this(name, artist, album, -1);
	}
	
	//Constructor for name, artist and year
	public Song(String name, String artist, int year){
		this(name, artist, "", year);
	}
	
	public String toString() {
		return this.name + ", " + this.artist;
	}

	public String getName() {
		return this.name;
	}

	public String getArtist() {
		return this.artist;
	}

	public String getAlbum() {
		return this.album;
	}
	
	public int getYear() {
		return this.year;
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

	public String displaySong(){
		//All fields are filled
		if(!this.album.equals("") && this.year >= 1000) {
			return "Title: " + this.name + "\nArtist: " + this.artist + 
					"\nAlbum: " + this.album + "\nYear: " + this.year;
		}
		//Name, artist and year
		else if(this.year >= 1000 && this.album.equals("")) {
			return "Title: " + this.name + "\nArtist: " + this.artist + 
					 "\nYear: " + this.year;
		}
		//Name, artist and album
		else if(this.year < 999 && !this.album.equals("")) {
			return "Title: " + this.name + "\nArtist: " + this.artist + 
					"\nAlbum: " + this.album;
		}
		//Name and artist
		else {
			return "Title: " + this.name + "\nArtist: " + this.artist;
		}
	}
	
}
