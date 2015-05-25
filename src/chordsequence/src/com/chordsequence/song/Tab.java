package com.chordsequence.song;

public class Tab {

	private String name = "";
	private String string[] = null;

	
	public Tab(String s) {
		name = s;
	}
	
	public boolean fromString(String t) {
		
		string = t.split("\n");
		return true;
	}
	
	public String toString() {
		if (string == null) {
			return null;
		}
		
		String s = "";
		for (int i = 0; i < string.length; i++) {
			s += string[i] + "\n";
		}
		
		return s;
	}

	public int numStrings() {
		
		if (string == null) {
			return 0;
		}
		
		return string.length;
	}
	
	public String getString(int i) {
		if (string == null || i < 1 || i > string.length) {
			return null;
		}
		String s = string[i-1].substring(1, string[i-1].length()-1);
		return s;
	}

	public String getStringNote(int i) {
		if (string == null || i < 1 || i > string.length) {
			return null;
		}
		String s = string[i-1].substring(0, 1);
		return s;
	}
	
	public void add(String t) {
		if (string == null) {
			string = new String[1];
			string[0] = t;
		} else {
			String s[] = null;
			s = new String[string.length+1];
			for (int i = 0; i < string.length; i++) {
				s[i] = string[i];
			}
			s[string.length] = t;
			string = s;
		}
	}

	public void setName(String n) {
		name = n;
	}
	
	public String getName() {
		return name;
	}
	
	public int getWidth() {

		if (string == null) {
			return 0;
		}

		int width = 0;
		
		for (int i = 0; i < string.length; i++) {
			for (int j = 1; j < string[i].length()-1; j++) {
				if (string[i].charAt(j) != '-') {
					if (width < j) {
						width = j;
					}
				}
			}
		}
		return width;
	}
	
	
	static public boolean isATab(String s) {
		
		String t = s.trim();
		
		if ((t.startsWith("A") || 
			t.startsWith("B") ||
			t.startsWith("C") || 
			t.startsWith("D") || 
			t.startsWith("E") || 
			t.startsWith("F") || 
			t.startsWith("G") || 
			t.startsWith("a") ||
			t.startsWith("b") || 
			t.startsWith("c") || 
			t.startsWith("d") || 
			t.startsWith("e") || 
			t.startsWith("f") || 
			t.startsWith("g")) &&
			t.contains("-") &&
			t.endsWith("|")) {
			return true;
		}
			
		return false;
	}
}
