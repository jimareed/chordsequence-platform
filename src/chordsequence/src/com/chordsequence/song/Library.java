package com.chordsequence.song;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import com.chordsequence.server.Doc;
import com.chordsequence.util.Log;
import com.chordsequence.util.TextFile;
import com.chordsequence.util.TextFileBuffer;
import com.chordsequence.util.TextFileBufferString;


public class Library implements Doc, TextFileBuffer {

	private static String DEFAULTNAME = "guest";
	static protected String EXTENSION = "dat";
	
	protected HashMap<String, Song> songs = new HashMap<String, Song>(); 
	protected int nextId = 0;
	protected String name = DEFAULTNAME;
	protected String rootFolder = "/projects/chordsequence/data/";
	protected String userFolder = rootFolder + "users/";
	protected ChordPositionLibrary chordLib = new ChordPositionLibrary();
	protected boolean hasChanged = false;
	protected SetLists setLists = new SetLists();
	protected boolean addingSetList = false;
	
	
	public SetLists getSetLists() {
		return setLists;
	}
	
	public int getNextId() {
		return nextId;
	}

	public int getMaxId() {
		int id = 0;
		int maxId = 0;

		Set<String> keyset = songs.keySet();
		
		try {
			for (Iterator<String> it = keyset.iterator(); it.hasNext();) {
				id = Integer.parseInt(it.next());
				if (id > maxId) {
					maxId = id;
				}
			}
		} catch (Exception e) {
			//
		}
		
		return maxId;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDefaultSongId() {
		return firstSongId();
	}
	
	public HashMap<String, Song> getSongs() {
		return songs;
	}
	
	private void initialize(String theName) {
		
		
		if (theName.equals(DEFAULTNAME)) {
			TextFile file = new TextFile();
			TextFileBufferString buffer = new TextFileBufferString("");

			file.setBuffer(buffer);

			if (!file.load(userFolder + DEFAULTNAME, "library", EXTENSION)) {
				
				file.load(userFolder + DEFAULTNAME, "library", "txt");
				file.save(userFolder + DEFAULTNAME, "library", EXTENSION);
			}

			if (!file.load(userFolder + DEFAULTNAME, "chordlibrary", EXTENSION)) {
				
				file.load(userFolder + DEFAULTNAME, "chordlibrary", "txt");
				file.save(userFolder + DEFAULTNAME, "chordlibrary", EXTENSION);
			}
		} else {
			(new File(userFolder + theName )).mkdirs();
			
			TextFile file = new TextFile();
			TextFileBufferString buffer = new TextFileBufferString("");

			file.setBuffer(buffer);

			if (!file.load(userFolder + theName, "library", EXTENSION)) {
				
				file.load(userFolder + DEFAULTNAME, "library", EXTENSION);
				file.save(userFolder + theName, "library", EXTENSION);
			}

			if (!file.load(userFolder + theName, "chordlibrary", EXTENSION)) {
				
				file.load(userFolder + DEFAULTNAME, "chordlibrary", EXTENSION);
				file.save(userFolder + theName, "chordlibrary", EXTENSION);
			}
			
		}
		
		
	}

	public void setChangeFlag(boolean changed) {
		hasChanged = changed;
	}
	
	public boolean hasChanged() {
		return hasChanged;
	}
	
	public void setChordLib(ChordPositionLibrary lib) {
		chordLib = lib;
	}
	
	public ChordPositionLibrary getChordLib() {
		return chordLib;
	}

	public boolean load(String theName) {

		clear();
		
		if (theName != null && !theName.isEmpty()) {
			name = theName;
		} else {
			name = DEFAULTNAME;
		}
		
		initialize(name);
		
		TextFile file = new TextFile();
		String folder = userFolder + name + "/";

		file.setBuffer(this);

		if (!file.load(folder, "library", EXTENSION)) {
			Log.println("error loading song");
			returnLastId();
			return false;
		}
				
		chordLib.load(folder);
		
		return true;
	}
	
	
	public int size() {
		return songs.size();
	}

	public Song findSong(String songTitle) {
		
		Set<String> keyset = songs.keySet();
		
		for (Iterator<String> it = keyset.iterator(); it.hasNext();) {
			Song s = songs.get(it.next());
			if (s != null && songTitle.equals(s.getTitle())) {
				return s;
			}
		}
		
		return null;
	}
	
	private String generateNewId() {
		return Integer.toString(nextId++);
	}

	private void returnLastId() {
		nextId--;
	}
	
	public String addSong(Song song) {
		
		if (song.getId() == null || song.getId().isEmpty()) {
			song.setId(generateNewId());
		}
		
		songs.put(song.getId(), song);
		
		return song.getId();
	}

	public boolean deleteSong(String songId) {

		songs.remove(songId);

		return true;
	}
	
	
	public void clear() {
		songs.clear();
		setLists.clear();
		nextId = 0;
	}
	
	public String toString() {
		String str = "";

		Set<String> keyset = songs.keySet();
		
		for (Iterator<String> it = keyset.iterator(); it.hasNext();) {
			Song s = songs.get(it.next());
			if (s != null) {
				if (!str.isEmpty() && !str.endsWith("\n")) {
					str += "\n";
				}
				str += "{id:" + s.getId() + "}\n" + s.toString();
				
				Set<String> keyset2 = s.getChordPositions().keySet();
				
				for (Iterator<String> it2 = keyset2.iterator(); it2.hasNext();) {
					String chord = it2.next();
					if (Chord.isAChord(chord) && chordLib.getPosition(chord) == null) {
						ChordPosition pos = s.getChordPositions().get(chord);
						if (pos != null) {
							chordLib.add(pos);
						}
					}
				}

			}
			
			
		}
		
		if (setLists.size() > 0) {
			str += setLists.toString();
		}
		
		return str;
	}

	public String firstSongId() {
		int songNum = 0;

		while (songs.get(Integer.toString(songNum)) == null && songNum < getNextId()) {
			songNum++;
		}
		if (songNum > getMaxId()) {
			return null;
		}
		String newSongId = Integer.toString(songNum);
	
		return newSongId;
	}

	public String nextSongId(String songId) {
		int songNum = 0;
		try {
			songNum = Integer.parseInt(songId);
		} catch (Exception e) {
			return null;
		}

		songNum++;
		while (songs.get(Integer.toString(songNum)) == null && songNum < getNextId()) {
			songNum++;
		}
		if (songNum > getMaxId()) {
			songNum = getMaxId();
		}
		String newSongId = Integer.toString(songNum);

		if (songId.equals(newSongId)) {
			return null;
		}
		
		return newSongId;
	}

	public String previousSongId(String songId) {
		int songNum = 0;
		try {
			songNum = Integer.parseInt(songId);
		} catch (Exception e) {
			return null;
		}

		songNum--;
		while (songs.get(Integer.toString(songNum)) == null && songNum >= 0) {
			songNum--;
		}
		if (songNum < 0) {
			return null;
		}
		return Integer.toString(songNum);
	}
	
	public String copy(Song song) {
		
		Song s = new Song(this);
		s.fromString(song.toString());
		
		Song oldSong = findSong(s.getTitle());
		
		if (oldSong != null && oldSong.getArtist().equals(s.getArtist())) {
			deleteSong(oldSong.getId());
		}
		
		return addSong(s);
	}
	
	public boolean save(String theName) {
		name = theName;
		return save();
	}
	
	public boolean save() {

		if (!name.equals(DEFAULTNAME)) {
			TextFile file = new TextFile();
			String folder = userFolder + name + "/";

			file.setBuffer(this);
			
			if (!file.save(folder, "library", EXTENSION)) {
				Log.println(getClass().getName() + "error saving library");
				return false;
			}
		}

		hasChanged = false;
		
		return true;
	}

	private Song song = null;
	
	private String getSongIdFromLine(String s) {
		String id = s.substring(4,s.indexOf('}'));

		try {
			int nId = Integer.parseInt(id);
			
			if (nId >= nextId) {
				nextId = nId + 1;
			}
			
		} catch (Exception e) {
			//
		}
		return id;
	}
	
	@Override
	public void addLine(String s) {
		
		if (s.startsWith("{setlists:")) {
			addingSetList = true;
		}
		
		if (addingSetList) {
			setLists.addLine(s);
		} else {
			if (s.startsWith("{id:") && s.endsWith("}")) {
				
				if (song != null) {
					song.refreshChordPositions();
				}
				// move this to add line
				String songId = getSongIdFromLine(s);
				song = new Song(this);
				song.setId(songId);
				songs.put(songId, song);
			}
			
			song.addLine(s);
		}
	}

	@Override
	public void openBuffer() {
		addingSetList = false;
	}

	@Override
	public void closeBuffer() {
		addingSetList = false;
		if (song != null) {
			song.refreshChordPositions();
		}
		
	}

	@Override
	public String getDataFolder() {
		return rootFolder;
	}
	
}
