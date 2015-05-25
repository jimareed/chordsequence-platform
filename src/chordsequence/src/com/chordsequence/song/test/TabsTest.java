package com.chordsequence.song.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.chordsequence.song.Tabs;


public class TabsTest {

	static protected String BASICTAB = "E-0--------0----------------0-0------0-----------|\nB-2-0---0--3------3---------2-----0--3--3--------|\nG-2---2----2----2---2-------2---2----2----2------|\nD-2--------3--3-------3-----2--------3------3----|\nA-0--------0------------0---0--------0--------0--|\nD------------------------------------------------|\n";

	@Test
	public void test_basic() {
		
		Tabs tabs = new Tabs();

		tabs.add(BASICTAB);
		assertTrue(tabs.size() == 1);
		
	}

	@Test
	public void test_toString() {
		
		Tabs tabs = new Tabs();

		tabs.add(BASICTAB);
		String s = tabs.toString();

		assertTrue(s != null);
		assertTrue(s.contains("B-2-0---0--3------3---------2-----0--3--3--------|"));
		
	}
	
}
