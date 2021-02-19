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
		if(year >= 1000 && year < 2021) {
			this.year = year;
		}else {
			this.year = -1;
		}
	}

	//Constructor for only name and artist
	public Song(String name, String artist){
		this(name, artist, "", -1);
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

	public String displaySong() {
		if(!this.album.equals("") && this.year > 0) {
			return "Title: " + this.name + "\nArtist: " + this.artist + 
					"\nAlbum: " + this.album + "\nYear: " + this.year;
		}else {
			return "Title: " + this.name + "\nArtist: " + this.artist;
		}
	}

}
