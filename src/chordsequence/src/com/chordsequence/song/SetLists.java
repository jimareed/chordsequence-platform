package com.chordsequence.song;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.chordsequence.util.TextFileBuffer;

public class SetLists implements TextFileBuffer {

	HashMap<String,SetList> lists = new HashMap<String,SetList>();
	SetList setList = null;
	int nextName = 0;

	public int size() {
		return lists.size();
	}
	
	public void clear() {
		lists.clear();
		setList = null;
		nextName = 0;
	}
	
	public SetList get(String name) {
		return lists.get(name);
	}
	
	private int parseName(String s) {
		int i = 0;
		try {
			i = Integer.parseInt(s);
		} catch (Exception e) {
			i = -1;
		}

		if (nextName <= i) {
			nextName = i+1;
		}

		return i;
	}
	
	@Override
	public String toString() {
		String s = "{setlists:}\n";
		
		
		Set<String> keyset = lists.keySet();
		
		for (Iterator<String> it = keyset.iterator(); it.hasNext();) {
			SetList setList = lists.get(it.next());
			if (setList != null) {
				s += setList.toString();
			}
		}
		
		return s;
	}
	
	@Override
	public void openBuffer() {
		lists = new HashMap<String,SetList>();
	}

	@Override
	public void addLine(String s) {

		Pattern p = Pattern.compile("\\{setlist\\:(.+?)\\}");
		Matcher matcher = p.matcher(s.trim());
		if (matcher.matches()) {
			setList = new SetList();
			
			if (parseName(matcher.group(1).trim()) >= 0) {
				lists.put(matcher.group(1).trim(), setList);
			}
		}

		if (setList != null) {
			setList.addLine(s);		
		}
	}

	@Override
	public void closeBuffer() {
	}

}
