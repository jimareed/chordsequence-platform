package com.chordsequence.util;

public class TextFileBufferString implements TextFileBuffer {

	protected String buffer = "";

	public TextFileBufferString(String s) {
		buffer = s;
	}
	
	@Override
	public void addLine(String s) {
		buffer += s + "\n";
		
	}
	
	@Override
	public String toString() {
		return buffer;
	}
	
	public void clear() {
		buffer = "";
	}

	@Override
	public void openBuffer() {
		clear();
		
	}

	@Override
	public void closeBuffer() {
		// TODO Auto-generated method stub
		
	}

}
