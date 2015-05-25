package com.chordsequence.song.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.chordsequence.song.ChordPosition;
import com.chordsequence.song.ChordPositionLibrary;

public class ChordPositionTest {

	@Test
	public void test_G() {

		ChordPositionLibrary lib = new ChordPositionLibrary();
		ChordPosition position = new ChordPosition();
		position.copy(lib.getPosition("G"));
		
		assertTrue(position.toString().contains("3 2 0 0 0 3"));
	}

	@Test
	public void test_tostring() {

		ChordPositionLibrary lib = new ChordPositionLibrary();
		ChordPosition position = new ChordPosition();
		position.copy(lib.getPosition("G"));
		
		position.fromString(position.toString());
		
		assertTrue(position.getEl().equals("3"));
		assertTrue(position.getA().equals("2"));
		assertTrue(position.getD().equals("0"));
		assertTrue(position.getG().equals("0"));
		assertTrue(position.getB().equals("0"));
		assertTrue(position.getEh().equals("3"));
	}
	
	@Test
	public void test_E() {

		ChordPositionLibrary lib = new ChordPositionLibrary();
		ChordPosition position = new ChordPosition();
		position.copy(lib.getPosition("E"));
		
		assertTrue(position.toString().contains("0 2 2 1 0 0"));
	}

	@Test
	public void test_equals() {

		ChordPositionLibrary lib = new ChordPositionLibrary();
		ChordPosition position = new ChordPosition();
		position.copy(lib.getPosition("E"));
		
		assertTrue(position.equals(lib.getPosition("E")));
		assertTrue(!position.equals(lib.getPosition("C")));
	}

	@Test
	public void test_nextbasefret() {

		ChordPositionLibrary lib = new ChordPositionLibrary();
		ChordPosition position = new ChordPosition();
		position.copy(lib.getPosition("E"));

		assertTrue(position.getBaseFret().equals("0"));

		position.nextBaseFret();

		assertTrue(position.getBaseFret().equals("1"));

		position.previousBaseFret();

		assertTrue(position.getBaseFret().equals("0"));

		position.previousBaseFret();

		assertTrue(position.getBaseFret().equals("0"));
}
	
	@Test
	public void test_updatestring() {

		ChordPositionLibrary lib = new ChordPositionLibrary();
		ChordPosition position = new ChordPosition();
		position.copy(lib.getPosition("E"));
		
		assertTrue(position.toString().contains("0 2 2 1 0 0"));
		
		position.updateString(3, 2);
		
		assertTrue(position.toString().contains("0 2 0 1 0 0"));
		
	}

	@Test
	public void test_updatestringtox() {

		ChordPositionLibrary lib = new ChordPositionLibrary();
		ChordPosition position = new ChordPosition();
		position.copy(lib.getPosition("E"));
		
		assertTrue(position.toString().contains("0 2 2 1 0 0"));
		
		position.updateString(1, 0);
		
		assertTrue(position.toString().contains("x 2 2 1 0 0"));
		
	}
	
	@Test
	public void test_EtoSvg() {

		ChordPositionLibrary lib = new ChordPositionLibrary();
		ChordPosition position = new ChordPosition();
		position.copy(lib.getPosition("E"));
		
		assertTrue(position.toSvg().contains("circle"));
	}

	@Test
	public void test_toSvgZoom() {

		ChordPositionLibrary lib = new ChordPositionLibrary();
		ChordPosition position = new ChordPosition();
		position.copy(lib.getPosition("E"));
		
		assertTrue(position.toSvgZoom().contains("selectchordstring('E','6','0')"));
	}

	@Test
	public void test_toSvgZoom2() {

		ChordPositionLibrary lib = new ChordPositionLibrary();
		ChordPosition position = new ChordPosition("fred");
		if (lib.getPosition("fred") != null) {
			position.copy(lib.getPosition("fred"));
		}
		
		assertTrue(position.toSvgZoom().contains("selectchordstring('fred','6','0')"));
	}

	
	@Test
	public void test_Eposition() {

		ChordPositionLibrary lib = new ChordPositionLibrary();
		ChordPosition position = new ChordPosition();
		position.copy(lib.getPosition("E"));
		
		assertTrue(position.getEl().equals("0"));
		assertTrue(position.getA().equals("2"));
		assertTrue(position.getD().equals("2"));
		assertTrue(position.getG().equals("1"));
		assertTrue(position.getB().equals("0"));
		assertTrue(position.getEh().equals("0"));
	}

	@Test
	public void test_copy() {

		ChordPositionLibrary lib = new ChordPositionLibrary();
		ChordPosition position = new ChordPosition();
		ChordPosition p2 = new ChordPosition();
		position.copy(lib.getPosition("D"));
		p2.copy(lib.getPosition("E"));

		position.copy(p2);
		
		assertTrue(position.getEl().equals("0"));
		assertTrue(position.getA().equals("2"));
		assertTrue(position.getD().equals("2"));
		assertTrue(position.getG().equals("1"));
		assertTrue(position.getB().equals("0"));
		assertTrue(position.getEh().equals("0"));
	}

	@Test
	public void test_fromlibrary() {

		ChordPositionLibrary lib = new ChordPositionLibrary();
		ChordPosition position = new ChordPosition();

		position.copy(lib.getPosition("E"));
		
		assertTrue(position.getEl().equals("0"));
		assertTrue(position.getA().equals("2"));
		assertTrue(position.getD().equals("2"));
		assertTrue(position.getG().equals("1"));
		assertTrue(position.getB().equals("0"));
		assertTrue(position.getEh().equals("0"));
	}
	
}
