package com.chordsequence.song;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import com.chordsequence.util.Log;
import com.chordsequence.util.TextFile;
import com.chordsequence.util.TextFileBuffer;

public class ChordPositionLibrary implements TextFileBuffer {

	protected HashMap<String, ChordPosition> library = new HashMap<String, ChordPosition>(); 
	protected boolean hasChanged = false;
	protected String folder = DEFAULTFOLDER;

	static protected String DEFAULTFOLDER = "/projects/chordsequence/data/users/guest/";
	static protected String FILENAME = "chordlibrary";
	static protected String EXTENSION = "dat";

	
	
	public synchronized boolean load(String rootFolder) {
		return loadNoSync(rootFolder);
	}

	private boolean loadNoSync(String rootFolder) {

		if (rootFolder != null && !rootFolder.isEmpty()) {
			folder = rootFolder;
		}
		
		TextFile file = new TextFile();
		file.setBuffer(this);
		
		if (!file.load(folder, FILENAME, EXTENSION)) {
			if (!file.load(folder, FILENAME, "txt")) {
				Log.println(getClass().getName() + ": error loading file " + folder + FILENAME);
				return false;
			}
			file.save(folder, FILENAME, EXTENSION);
		}

		return true;
	}
	
	public int size() {
		return library.size();
	}

	public void clear() {
		library.clear();
	}
	
	public void setChangeFlag(boolean changed) {
		hasChanged = changed;
	}
	
	public boolean hasChanged() {
		return hasChanged;
	}
	
	
	public ChordPosition getPosition(String ch) {
		if (library.size() == 0) {
			load(null);
		}
		
		return library.get(ch);
	}

	public String toString() {
		String str = "";

		Set<String> keyset = library.keySet();
		
		for (Iterator<String> it = keyset.iterator(); it.hasNext();) {
			ChordPosition cp = library.get(it.next());
			if (cp != null) {
				str += cp.toString() + "\n";
			}
		}
		
		return str;
	}

	
	public synchronized boolean save() {
		return saveNoSync();
	}

	private boolean saveNoSync() {
		TextFile file = new TextFile();

		file.setBuffer(this);
		
		if (!file.save(folder, FILENAME, EXTENSION)) {
			Log.println("error saving " + folder + FILENAME);
			return false;
		}

		hasChanged = false;
		
		return true;
	}

	public void add(ChordPosition pos) {
		
		if (!pos.isEmpty()) {
			library.put(pos.getChord(), pos);
			hasChanged = true;
		}
	}

	public synchronized boolean addAndSaveAppend(ChordPosition pos) {

		add(pos);
		
		ChordPositionLibrary tempLib = new ChordPositionLibrary();
		
		if (!tempLib.loadNoSync(folder)) {
			Log.println(getClass().getName() + ": error loading chord position library " + folder);
			return false;
		}
		
		if (tempLib.getPosition(pos.getChord()) == null) {
			tempLib.add(pos);
			if (!tempLib.saveNoSync()) {
				Log.println(getClass().getName() + ": error saving chord position library " + folder);
				return false;
			}
		}
		return true;
	}	
	
	@Override
	public void addLine(String s) {
		ChordPosition position = new ChordPosition(s);
		
		if (!position.getChord().isEmpty()) {
			library.put(position.getChord(), position);
		}
	}

	@Override
	public void openBuffer() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void closeBuffer() {
		// TODO Auto-generated method stub
		
	}
}
