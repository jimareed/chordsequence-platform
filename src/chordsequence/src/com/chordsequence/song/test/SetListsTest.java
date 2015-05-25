package com.chordsequence.song.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.chordsequence.song.SetLists;
import com.chordsequence.util.TextFile;

public class SetListsTest {

	static private String SETLISTS = "{setlists:}\n{setlist:0}\n{id:2}\n{id:3}\n{id:4}\n";

	@Test
	public void test_basic() {
		SetLists setLists = new SetLists();
		TextFile file = new TextFile();
		file.setBuffer(setLists);
		file.fromString(SETLISTS);
		
		assertTrue(setLists != null);
		assertTrue(setLists.size() == 1);
	}	
	
	@Test
	public void test_tostring() {
		SetLists setLists = new SetLists();
		TextFile file = new TextFile();
		file.setBuffer(setLists);
		file.fromString(SETLISTS);

		String s = setLists.toString();
		
		assertTrue(s.contains("{setlists:}\n"));
		assertTrue(s.contains("{setlist:0}\n"));
		assertTrue(s.contains("{id:3}\n"));
	
	}	
}
