package com.chordsequence.song.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.chordsequence.song.Tab;

public class TabTest {
	
	static protected String BASICTAB = "E-0--------0----------------0-0------0-----------|\nB-2-0---0--3------3---------2-----0--3--3--------|\nG-2---2----2----2---2-------2---2----2----2------|\nD-2--------3--3-------3-----2--------3------3----|\nA-0--------0------------0---0--------0--------0--|\nD------------------------------------------------|\n";
	static protected String INVALIDTAB = "hello";
	static protected String VALIDTAB = "E-0--------0----------------0-0------0-----------|";
	static protected String WIDTH12 = "E123456789012------------------------------------|";
	static protected String WIDTH20 = "E-------------------x----------------------------|";
	static protected String WIDTH5 = "E----1|\nE-2---|";

	
	@Test
	public void test_basic() {
		
		assertTrue(Tab.isATab(BASICTAB));
		assertTrue(!Tab.isATab(INVALIDTAB));
		assertTrue(Tab.isATab(VALIDTAB));
	}
	
	@Test
	public void test_basic2() {
		
		Tab tab = new Tab("riff 1");
		tab.fromString(BASICTAB);
		assertTrue(tab.numStrings() == 6);
		String s = tab.getString(2);
		assertTrue(s != null);
		s = tab.getStringNote(2);
		assertTrue(s.startsWith("B"));
	}

	@Test
	public void test_tostring() {
		
		Tab tab = new Tab("riff 1");
		tab.fromString(BASICTAB);
		assertTrue(tab.numStrings() == 6);
		String s = tab.toString();
		assertTrue(s.contains("B-2-0---0--3------3---------2-----0--3--3--------|"));

	
	}
	
	@Test
	public void test_getwidth() {
		
		Tab tab = new Tab("riff 1");
		tab.fromString(WIDTH12);
		assertTrue(tab.getWidth() == 12);
		tab.fromString(WIDTH20);
		assertTrue(tab.getWidth() == 20);
		tab.fromString(WIDTH5);
		assertTrue(tab.getWidth() == 5);

	
	}
	
}
