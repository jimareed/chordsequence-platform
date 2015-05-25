package com.chordsequence.song.test;

import java.util.ArrayList;

import org.junit.* ;

import com.chordsequence.song.Library;
import com.chordsequence.song.SetList;
import com.chordsequence.song.SetLists;
import com.chordsequence.song.Song;
import com.chordsequence.util.TextFile;

import static org.junit.Assert.* ;

public class LibraryTest {

	static String HOUSEOFTHERISINGSUN = "House of the Rising Sun\nAnimals\n      Am   C        D          F\nThere is a house in New Orleans,\n";
	static String AMAZINGGRACE = "Amazing Grace\nTraditional\nVerse 1\nG                               C             G\nAmazing grace how sweet the sound\n";
	
	static String BASICLIBRARY = "{id:0}\n{t:s1}\n{st:a1}\nThen add [Am]some [C]lyrics\n";
	static String LIBWITHSETLISTS = "{id:0}\n{t:s1}\n{st:a1}\nThen add [Am]some [C]lyrics\n{setlists:}\n{setlist:0}\n{id:0}\n";

	static String THREESONGLIBRARY = "{id:0}\n{t:s1}\n{st:a1}\nThen add [Am]some [C]lyrics\n{id:1}\n{t:s2}\n{st:a2}\nThen add [Am]some [C]lyrics\n{id:2}\n{t:s3}\n{st:a3}\nThen add [Am]some [C]lyrics\n";
	
	@Test
	public void test_basic() {

		Library lib = new Library();
		Song song = new Song(lib);
		Song song2 = new Song(lib);
		
		assertTrue(lib.size() == 0) ;

		lib.addSong(song);
		
		assertTrue(lib.size() == 1) ;

		lib.addSong(song2);
		
		assertTrue(lib.size() == 2) ;
	}

	@Test
	public void test_load() {
		Library lib = new Library();

		lib.load(null);

		assertTrue(lib.size() == 1);
		
	}
	

	@Test
	public void test_clear() {

		Library lib = new Library();
		Song song = new Song(lib);
		assertTrue(lib.size() == 0) ;

		lib.addSong(song);
		
		assertTrue(lib.size() == 1) ;

		lib.clear();
		
		assertTrue(lib.size() == 0) ;
	}

	@Test
	public void test_save() {

		Library lib = new Library();

		Song song = new Song(lib);
		song.fromString(HOUSEOFTHERISINGSUN);
		lib.addSong(song);
		
		assertTrue(lib.size() == 1);
		assertTrue(lib.save("test"));

		lib.clear();

		assertTrue(lib.size() == 0);
		assertTrue(lib.load("test"));
		assertTrue(lib.size() == 1);
		assertTrue(lib.findSong("House of the Rising Sun") != null);

		Song s2 = new Song(lib);
		s2.fromString(AMAZINGGRACE);
		lib.addSong(s2);
	
		assertTrue(lib.size() == 2);
		assertTrue(lib.save("test"));

		lib.clear();

		assertTrue(lib.size() == 0);
		assertTrue(lib.load("test"));
		assertTrue(lib.size() == 2);
		assertTrue(lib.findSong("House of the Rising Sun") != null);
		assertTrue(lib.findSong("Amazing Grace") != null);
		
	}

	@Test
	public void test_tostring() {
		
		Library lib = new Library();

		Song song = new Song(lib);
		song.fromString(HOUSEOFTHERISINGSUN);
		lib.addSong(song);

		String s = lib.toString();

		assertTrue(!s.isEmpty());
		assertTrue(s.contains("House of the Rising Sun"));
		assertTrue(!s.contains("Amazing Grace"));
		
		Song s2 = new Song(lib);
		s2.fromString(AMAZINGGRACE);
		lib.addSong(s2);

		s = lib.toString();

		assertTrue(!s.isEmpty());
		assertTrue(s.contains("House of the Rising Sun"));
		assertTrue(s.contains("Amazing Grace"));
		
	}
	
	@Test
	public void test_deletesong() {

		Library lib = new Library();

		Song song = new Song(lib);
		song.fromString(HOUSEOFTHERISINGSUN);
		lib.addSong(song);
		
		Song s2 = new Song(lib);
		s2.fromString(AMAZINGGRACE);
		lib.addSong(s2);

		Song s3 = new Song(lib);
		s3.fromString(HOUSEOFTHERISINGSUN);
		lib.addSong(s3);
		
		Song s4 = new Song(lib);
		s4.fromString(AMAZINGGRACE);
		lib.addSong(s4);
		
		assertTrue(lib.size() == 4);
		
		assertTrue(lib.deleteSong("1"));

		assertTrue(lib.size() == 3) ;
		
		song = lib.getSongs().get("0");
		assertTrue(song != null && song.getId().equals("0"));
		
		song = lib.getSongs().get("1");
		assertTrue(song == null);
		
		song = lib.getSongs().get("2");
		assertTrue(song != null && song.getId().equals("2"));

		song = lib.getSongs().get("3");
		assertTrue(song != null && song.getId().equals("3"));
	}

	@Test
	public void test_fromstring() {
		Library lib = new Library();
		TextFile file = new TextFile();
		file.setBuffer(lib);

		assertTrue(lib.size() == 0);
	
		file.fromString(BASICLIBRARY);
		assertTrue(lib.size() == 1);		
		
		Song song = lib.getSongs().get("0");
		assertTrue(song != null);		
		
		ArrayList<String> chords = song.getUniqueChords();
		assertTrue(chords != null);		
		assertTrue(chords.size() == 2);
	}
	
	@Test
	public void test_setlists() {
		Library lib = new Library();
		TextFile file = new TextFile();
		file.setBuffer(lib);

		assertTrue(lib.size() == 0);
	
		file.fromString(LIBWITHSETLISTS);
		assertTrue(lib.size() == 1);		
		
		Song song = lib.getSongs().get("0");
		assertTrue(song != null);		
		
		ArrayList<String> chords = song.getUniqueChords();
		assertTrue(chords != null);		
		assertTrue(chords.size() == 2);
		
		SetLists setLists = lib.getSetLists();
		assertTrue(setLists != null);		
		assertTrue(setLists.size() == 1);	
		
		SetList setList = setLists.get("0");
		assertTrue(setList != null);
		assertTrue(setList.size() == 1);
		
		String s = lib.toString();

		Library lib2 = new Library();
		TextFile file2 = new TextFile();
		file2.setBuffer(lib2);
		
		file2.fromString(s);
		assertTrue(lib2.size() == 1);		
		
		song = lib2.getSongs().get("0");
		assertTrue(song != null);		
		
		chords = song.getUniqueChords();
		assertTrue(chords != null);		
		assertTrue(chords.size() == 2);
		
		setLists = lib2.getSetLists();
		assertTrue(setLists != null);		
		assertTrue(setLists.size() == 1);	
		
		setList = setLists.get("0");
		assertTrue(setList != null);
		assertTrue(setList.size() == 1);
		
	}

	@Test
	public void test_copy() {
		Library lib = new Library();
		TextFile file = new TextFile();
		file.setBuffer(lib);

		assertTrue(lib.size() == 0);
	
		file.fromString(BASICLIBRARY);
		assertTrue(lib.size() == 1);		
		
		Song song = lib.getSongs().get("0");
		assertTrue(song != null);		
		assertTrue(song.getTitle().equals("s1"));
		
		Library lib2 = new Library();

		assertTrue(lib2.size() == 0);
		
		assertTrue(lib2.copy(song) != null);
		assertTrue(lib2.size() == 1);
	}

	@Test
	public void test_nextsong() {
		Library lib = new Library();
		TextFile file = new TextFile();
		file.setBuffer(lib);

		assertTrue(lib.size() == 0);
	
		file.fromString(THREESONGLIBRARY);
		assertTrue(lib.size() == 3);	
		assertTrue(lib.nextSongId("0") != null);
		assertTrue(lib.nextSongId("0").equals("1"));
		assertTrue(lib.nextSongId("1") != null);
		assertTrue(lib.nextSongId("1").equals("2"));
		assertTrue(lib.nextSongId("2") == null);
		assertTrue(lib.previousSongId("2") != null);
		assertTrue(lib.previousSongId("2").equals("1"));
		assertTrue(lib.previousSongId("1") != null);
		assertTrue(lib.previousSongId("1").equals("0"));
		assertTrue(lib.previousSongId("0") == null);
	}
	
}
