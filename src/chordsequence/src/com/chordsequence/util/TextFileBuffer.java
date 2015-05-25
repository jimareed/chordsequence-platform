package com.chordsequence.util;

public interface TextFileBuffer {

	public void openBuffer();
	public void addLine(String s);
	public void closeBuffer();

	public String toString();
}
