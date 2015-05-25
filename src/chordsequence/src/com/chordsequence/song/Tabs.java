package com.chordsequence.song;

import java.util.HashMap;

public class Tabs {

	private HashMap<String, Tab> tabs = new HashMap<String, Tab>();	
	private Tab currentTab = null;
	
	public Tab getCurrentTab() {
		return currentTab;
	}
	
	public int size() {
		return tabs.size();
	}
	
	public String toString() {
		
		String s = "";
		
		for (int i = 0; i < size(); i++) {
			Tab tab = get(i);
			
			if (tab != null) {
				s += tab.getName() + ":\n";
				s += tab.toString();
			}
		}
		
		return s;
	}
	
	public void clearCurrentTab() {
		currentTab = null;
	}
	
	public Tab add(String s) {
		
		int riffNum = tabs.size() + 1;
		
		String riff = "riff " + Integer.toString(riffNum);

		Tab tab = new Tab(riff);
		
		tab.add(s);

		currentTab = tab;
		
		tabs.put(riff, tab);
		
		return tab;
	}
	
	public Tab get(int i) {
		String name = "riff " + Integer.toString(i+1);
		return get(name);
	}

	public Tab get(String name) {
		return tabs.get(name);
	}
}
