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
	
	public String toString() {
		if(!this.album.equals("") && !this.year.equals("")) {
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

	    public void setYear(String year) {
	        this.year = year;
 	   }

}
