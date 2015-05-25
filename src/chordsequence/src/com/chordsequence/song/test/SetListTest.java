package com.chordsequence.song.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.chordsequence.song.SetList;
import com.chordsequence.util.TextFile;

public class SetListTest {

	static private String SETLIST = "{setlist:list1}\n{id:2}\n{id:3}\n{id:4}\n";

	@Test
	public void test_basic() {
		SetList setList = new SetList();
		TextFile file = new TextFile();
		file.setBuffer(setList);
		file.fromString(SETLIST);
		
		assertTrue(setList != null);
		assertTrue(setList.getName().equals("list1"));
		assertTrue(setList.size() == 3);
		assertTrue(setList.get(1).equals("3"));
	}	
	
	@Test
	public void test_tostring() {
		SetList setList = new SetList();
		TextFile file = new TextFile();
		file.setBuffer(setList);
		file.fromString(SETLIST);

		String s = setList.toString();
		
		assertTrue(s.contains("{setlist:list1}\n"));
		assertTrue(s.contains("{id:3}\n"));
	}
}
