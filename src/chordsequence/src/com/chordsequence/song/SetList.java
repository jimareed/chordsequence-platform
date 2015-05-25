package com.chordsequence.song;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.chordsequence.util.TextFileBuffer;

public class SetList implements TextFileBuffer {

	private String name = "";
	private ArrayList<String> ids = new ArrayList<>();
	
	public String getName() {
		return name;
	}

	public void setName(String n) {
		name = n;
	}
	
	public int size() {
		return ids.size();
	}

	public String get(int i) {
		return ids.get(i);
	}
	
	@Override
	public void openBuffer() {
		name = "";
		if (ids.size() > 0) {
			ids = new ArrayList<>();
		}
	}
	
	@Override
	public String toString() {
		String s = "{setlist:" + name + "}\n";
		
		for (int i = 0; i < ids.size(); i++) {
			s += "{id:" + ids.get(i) + "}\n";
		}
		
		return s;
	}

	@Override
	public void addLine(String s) {

		Pattern p = Pattern.compile("\\{setlist\\:(.+?)\\}");
		Matcher matcher = p.matcher(s.trim());
		if (matcher.matches()) {
			name = matcher.group(1).trim();
		} 

		p = Pattern.compile("\\{id\\:(.+?)\\}");
		matcher = p.matcher(s.trim());
		if (matcher.matches()) {
			ids.add(matcher.group(1).trim());
		} 
		
	}

	@Override
	public void closeBuffer() {
		
	}

}
