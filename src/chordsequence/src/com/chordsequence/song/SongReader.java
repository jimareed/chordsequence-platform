package com.chordsequence.song;

public interface SongReader {

	public Chords getChords();
	public Lyrics getLyrics();
	public String getTitle();
	public String getArtist();
	public Tabs getTabs();
	
	public void addLine(String s);

	public void copy(SongReader reader);
	
	public void setSong(Song song);
}
