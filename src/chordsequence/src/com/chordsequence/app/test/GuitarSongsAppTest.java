package com.chordsequence.app.test;

import org.junit.* ;

import com.chordsequence.app.GuitarSongsApp;
import com.chordsequence.server.App;
import com.chordsequence.song.Library;

import static org.junit.Assert.* ;

public class GuitarSongsAppTest {

	static String HOUSEOFTHERISINGSUN = "House of the Rising Sun\nAnimals\n      Am   C        D          F\nThere is a house in New Orleans,\n";
	
	@Test
	public void test_main() {

		App app = new GuitarSongsApp(null);
		
		String result = app.render("", "");

		assertTrue(result.contains("<html>")) ;
	}

	@Test
	public void test_title() {
		App app = new GuitarSongsApp(null);
		
		String result = app.render("", "title");
		
		assertTrue(result.contains("<div id=\"title\""));
	}

	@Test
	public void test_addsong() {

		App app = new GuitarSongsApp(null);
		
		Library lib = (Library)app.getDoc();

		int size = lib.size();

		app.setParm("song" , HOUSEOFTHERISINGSUN);

		String result = app.render("addsong", "editsong");

		assertTrue(result.contains("<html>"));
		assertTrue(lib.size() == size+1);
	}

	
	@Test
	public void test_updatechord() {

		App app = new GuitarSongsApp(null);
		
		app.setParm("songid" , "0");
		app.setParm("chord" , "C");
		app.setParm("string" , "3");
		app.setParm("fret" , "2");

		String result = app.render("updatechord", "editchord");
		
		assertTrue(result.contains("<html>"));
	}
	
	@Test
	public void test_editsong2() {

		App app = new GuitarSongsApp(null);
		
		app.setParm("songid" , "0");

		String result = app.render("", "editsong");

		assertTrue(result.contains("<html>"));
//		assertTrue(result.contains("Click on a [Am]chord"));
	}

	@Test
	public void test_editchord() {

		App app = new GuitarSongsApp(null);
		
		app.setParm("songid" , "0");
		app.setParm("chord" , "C");

		String result = app.render("", "editchord");

		assertTrue(result.contains("<html>"));
		assertTrue(result.contains("<svg"));
	}

	@Test
	public void test_login() {

		App app = new GuitarSongsApp(null);
		
		String result = app.render("", "login");

		assertTrue(result.contains("<html>"));
	}

	@Test
	public void test_actionlogin() {

		App app = new GuitarSongsApp(null);

		app.setParm("user" , "share");
		
		String result = app.render("login", "editsong");

		assertTrue(result.contains("<html>"));
	}
	
	@Test
	public void test_editsong() {

		App app = new GuitarSongsApp(null);
		
		app.setParm("songid" , "0");

		String result = app.render("", "editsong");

		assertTrue(result.contains("<html>"));
		assertTrue(result.contains("&songid=0"));
	}

	@Test
	public void test_addsharedsong() {

		App app = new GuitarSongsApp(null);
		Library lib = new Library();
		
		assertTrue(true);
		
		
	}
	
}
