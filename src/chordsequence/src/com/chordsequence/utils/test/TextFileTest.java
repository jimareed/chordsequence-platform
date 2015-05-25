package com.chordsequence.utils.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.chordsequence.util.TextFile;
import com.chordsequence.util.TextFileBufferString;

public class TextFileTest {

	protected String rootFolder = "/projects/chordsequence/temp/";
	protected String extension = "txt";
	static protected String SAMPLEFILE = "hello\nthere\n";

	@Test
	public void test_basicHandler() {

		TextFile file = new TextFile();

		TextFileBufferString buffer = new TextFileBufferString(SAMPLEFILE);
		
		file.setBuffer(buffer);

		assertTrue(file.save(rootFolder, "test", extension));
		
		buffer.clear();
		
		assertTrue(buffer.toString().isEmpty());		
		assertTrue(file.load(rootFolder, "test", extension));
		assertTrue(buffer.toString().equals(SAMPLEFILE));		
	}
}
