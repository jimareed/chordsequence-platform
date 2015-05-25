package com.chordsequence.app;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.chordsequence.image.Image;
import com.chordsequence.server.App;
import com.chordsequence.server.Comments;
import com.chordsequence.server.Server;
import com.chordsequence.server.Users;
import com.chordsequence.song.ChordPosition;
import com.chordsequence.song.ChordPositionLibrary;
import com.chordsequence.song.Library;
import com.chordsequence.song.SetList;
import com.chordsequence.song.Song;
import com.chordsequence.util.Log;
import com.chordsequence.util.Template;
import com.chordsequence.util.TextFile;
import com.chordsequence.util.TextHttpFile;


public class GuitarSongsApp implements App {
	
	static String WELCOME = "House of the Rising Sun\nAnimals\n      Am   C        D          F\nThere is a house in New Orleans,\n";
	
	static int MAXCOMMENTS = 50;
	static int MAXSONGS = 1000;
	
	private Library library = new Library();

	private Library sharedLibrary = null;
	
	private ChordPositionLibrary chordLibrary = new ChordPositionLibrary();
	private ChordPosition updateChordPosition = new ChordPosition();
	
	private HashMap<String, String> parms = new HashMap<String,String>();
	private String user = "";
	private Users users = new Users();
	private Server server = null;
	private boolean sessionExpired = false;
	private long lastRenderTime = 0;
	private String sessionId = "";
	private int commentCount = 0;
	private int songCount = 0;
	private int linesPerPage = 10;
	static private int MAXLINESPERPAGE = 1000;

	public void setSharedLibrary(Library lib) {
		sharedLibrary = lib;
	}

	public int getLinesPerPage() {
		return linesPerPage;
	}
	
	public synchronized String addSharedSong(Song song) {
		Song sharedSong = new Song(sharedLibrary);
		
		sharedSong.fromString(song.toString());
		
		String songId = sharedLibrary.addSong(sharedSong);
		
		if (!sharedLibrary.save()) {
			Log.println(getClass().getName() + ": error saving shared library");
			return null;
		}
		
		return songId;
	}

	public synchronized Song getSharedSong(String songId) {
		return sharedLibrary.getSongs().get(songId);
	}
	
	public synchronized SetList getSharedSetList(String setListName) {
		return sharedLibrary.getSetLists().get(setListName);
	}
	

	public long getLastRenderTime() {
		return lastRenderTime;
	}
	
	public void expireSession() {
		sessionExpired = true;
	}
	
	public boolean sessionExpired() {
		return sessionExpired;
	}

	public String getUser() {
		return user;
	}
	
	private boolean login(String userName, String password) {
		if (!users.login(userName.toLowerCase(), password)) {
			return false;
		}

		user = userName.toLowerCase();
		library.clear();
		library.load(user);
		
		if (server != null) {
			HashMap<String, GuitarSongsApp> sessions = server.getSessions();
			
			Iterator<String> it = sessions.keySet().iterator();

			while(it.hasNext()) {
				String session = it.next();
				GuitarSongsApp app = sessions.get(session);
				if (app != null && app != this) {
					if (app.user.equals(user)) {
						app.sessionExpired = true;
					}
				}
			}
		}
		
		return true;
	}
	
	private void logout() {
		user = "guest";
		library.load("guest");
	}
	
	public GuitarSongsApp(Server theServer) {
		
		server = theServer;
		
		library.setChordLib(chordLibrary);
		library.load(null);

		if (library.size() < 1) {
			Song song = new Song(null);
			TextFile file = new TextFile();
			
			file.setBuffer(song);

			file.fromString(WELCOME);
			library.addSong(song);
		}
	}

	public ChordPositionLibrary getChordLib() {
		return chordLibrary;
	}
	
	@Override
	public void setParm(String theParm, String value) {
	
		parms.put(theParm, value);
	}

	
	@Override
	public String render(String action, String view) {

		String songId = getParm("songid");
		String sline = getParm("line");
		String scolumn = getParm("column");			
		String chord = getParm("chord");
		String direction = getParm("direction");
		String string = getParm("string");
		String fret = getParm("fret");
		String theUser = getParm("user");
		String password = getParm("password");
		String sharedSongId = getParm("sharedsongid");
		
		lastRenderTime = System.currentTimeMillis();
		
		if (view == null || view.isEmpty()) {
			view = "viewsong";
			songId = library.firstSongId();
			setParm("view", view);
			setParm("songid", songId);
		}
		
		if (action == null) {
			action = "";
		}
		
		if (action.equals("addsong")) {
			if (songCount < MAXSONGS) {
				Song song = new Song(library);
				String selectedFile = getParm("selected-file");
				
				if (selectedFile != null && !selectedFile.isEmpty()) {
					TextHttpFile file = new TextHttpFile();
					
					file.setBuffer(song);
					file.load(selectedFile);
					
				} else {
					TextFile file = new TextFile();
					
					file.setBuffer(song);
					file.fromString(getParm("song"));
				}
				
				setParm("songid" , library.addSong(song));
				songCount++;
			}
		} else if (action.equals("login")) {
			if (!view.equals("login")) {
				if (!login(theUser, password)) {
					return "Invalid user name or password";
				}
				if (getParm("songid") == null || getParm("songid").isEmpty()) {
					songId = library.firstSongId();
					setParm("songid", songId);
				}
			}
		} else if (action.equals("logout")) {
			logout();
			songId = library.getDefaultSongId();
			setParm("songid", songId);
			view = "viewsong";
			setParm("view", view);
			setParm("songid", songId);
		} else if (action.equals("previousbasefret")) {
			String error = moveBaseFret(action);
			if (error != null) {
				return error;
			}
		} else if (action.equals("addcomment")) {
			if (commentCount < MAXCOMMENTS) {
				Comments comments = new Comments();
				
				comments.add(getParm("comments"), user, sessionId, library.getDataFolder());	
				
				commentCount++;
			}			
		} else if (action.equals("nextbasefret")) {
			String error = moveBaseFret(action);
			if (error != null) {
				return error;
			}
		} else if (action.equals("updatechordstart")) {
			if (songId == null ||
					chord == null ) {
					String error = ": unable to update chord null parameter";
					Log.println(getClass().getName() + error);
					return error;
			}

			Song song = library.getSongs().get(songId);
			
			if (song == null) {
				String error = ": unable to find songid " + songId;
				Log.println(getClass().getName() + error);
				return error;
			}
			
			if (song.getChordPosition(chord) == null) {
				String error = ": unable to find chord " + chord;
				Log.println(getClass().getName() + error);
				return error;
			}

			updateChordPosition.copy(song.getChordPosition(chord));
			
			
		} else if (action.equals("updatechordfinish")) {

			if (songId == null) {
					String error = ": unable to update chord null parameter";
					Log.println(getClass().getName() + error);
					return error;
			}

			Song song = library.getSongs().get(songId);
			
			if (song == null) {
				String error = ": unable to find songid " + songId;
				Log.println(getClass().getName() + error);
				return error;
			}
			
			if (updateChordPosition == null) {
				String error = ": unable to find chord " + chord;
				Log.println(getClass().getName() + error);
				return error;
			}

			if (song.getChordPosition(updateChordPosition.getChord()) == null) {
				String error = ": unable to find chord " + chord;
				Log.println(getClass().getName() + error);
				return error;
			}

			library.getChordLib().setChangeFlag(true);
			
			song.getChordPosition(updateChordPosition.getChord()).copy(updateChordPosition);

			if (!library.getChordLib().addAndSaveAppend(updateChordPosition)) {
				Log.println(getClass().getName() + ": add and save append error for " + updateChordPosition.getChord());
			}

			
		} else if (action.equals("updatechord")) {
			if (songId == null ||
					string == null || 
					fret == null || 
					chord == null ) {
					String error = ": unable to update chord null parameter";
					Log.println(getClass().getName() + error);
					return error;
			}

			if (updateChordPosition == null) {
				String error = ": unable to find chord " + chord;
				Log.println(getClass().getName() + error);
				return error;
			}

			try {
				updateChordPosition.updateString(Integer.parseInt(string), Integer.parseInt(fret));
			} catch (Exception e) {
				String error = ": unable to parse " + string + "," + fret;
				Log.println(getClass().getName() + error);
				return error;
			}
			
		} else if (action.equals("updatesong")) {
			String error = updateSong(songId,getParm("song"));
			
			if (error != null) {
				return error;
			}
		} else if (action.equals("renamechord")) {
			renameChord(songId,chord);
		} else if (action.equals("export")) {
			setParm("sharedsongid", export(songId));
		} else if (action.equals("setlinesperpage")) {
			setLinesPerPage(getParm("linesperpage"));
		} else if (action.equals("deletesong")) {
			library.deleteSong(songId);
		} else if (action.equals("deletechord")) {
			deleteChord(songId,chord);
		} else if (action.equals("addchord")) {
			addChord(songId,chord);
		} else if (action.equals("addchordref")) {

			if (songId == null || sline == null || scolumn == null ||
				chord == null ) {
				String error = ": unable to add chord null parameter " + songId + ", " + sline + ", " + scolumn + ", " + chord;
				Log.println(getClass().getName() + error);
				return error;
			}

			if (songId.isEmpty() || sline.isEmpty() || scolumn.isEmpty() ||
				chord.isEmpty()) {
					String error = ": unable to add chord empty parameter";
					Log.println(getClass().getName() + error);
					return error;
				}
			
			Song song = library.getSongs().get(songId);
			
			if (song == null) {
				String error = ": unable to find songid " + songId;
				Log.println(getClass().getName() + error);
				return error;
			}
			
			int line = 0;
			int column = 0;
			
			if (sline != null && !sline.isEmpty()) {
				try {
					line = Integer.parseInt(sline);
				} catch (Exception e) {
					String error = ": invalid line";
					Log.println(getClass().getName() + error);
					return error;
				}
			}

			if (scolumn != null && !scolumn.isEmpty()) {
				try {
					column = Integer.parseInt(scolumn);
				} catch (Exception e) {
					String error = ": invalid column";
					Log.println(getClass().getName() + error);
					return error;
				}
			}
			
			song.replaceChordRef(chord, line, column);
		} else if (action.equals("deletechordref")) {

			if (songId == null || sline == null || scolumn == null) {
				String error = ": unable to add chord null parameter";
				Log.println(getClass().getName() + error);
				return error;
			}

			if (songId.isEmpty() || sline.isEmpty() || scolumn.isEmpty()) {
					String error = ": unable to add chord empty parameter";
					Log.println(getClass().getName() + error);
					return error;
				}
			
			Song song = library.getSongs().get(songId);
			
			if (song == null) {
				String error = ": unable to find songid " + songId;
				Log.println(getClass().getName() + error);
				return error;
			}
			
			int line = 0;
			int column = 0;
			
			if (sline != null && !sline.isEmpty()) {
				try {
					line = Integer.parseInt(sline);
				} catch (Exception e) {
					String error = ": invalid line";
					Log.println(getClass().getName() + error);
					return error;
				}
			}

			if (scolumn != null && !scolumn.isEmpty()) {
				try {
					column = Integer.parseInt(scolumn);
				} catch (Exception e) {
					String error = ": invalid column";
					Log.println(getClass().getName() + error);
					return error;
				}
			}
			
			song.deleteChordRef(line, column);
		} else if (action.equals("movechord")) {

			if (songId == null ||
				sline == null || 
				scolumn == null || 
				direction == null ) {
				String error = getClass().getName() + ": unable to move chord null parameter";
				Log.println(getClass().getName() + error);
				return error;
			}

			if (songId.isEmpty() ||
				sline.isEmpty() ||
				scolumn.isEmpty() ||
				direction.isEmpty()) {
					String error = getClass().getName() + ": unable to move chord empty parameter";
					Log.println(getClass().getName() + error);
					return error;
				}
			
			int line = 0;
			int column = 0;
			
			try {
				line = Integer.parseInt(sline);
				column = Integer.parseInt(scolumn);
			} catch (Exception e) {
				String error = getClass().getName() + ": unable to determine line and column";
				Log.println(getClass().getName() + error);
				return error;
			}
			
			Song song = library.getSongs().get(songId);
			
			if (song == null) {
				String error = getClass().getName() + ": unable to find songid " + songId;
				Log.println(getClass().getName() + error);
				return error;
			}

			if (!song.moveChord(line, column, direction)) {
				String error = getClass().getName() + ": error moving chord " + direction;
				Log.println(getClass().getName() + error);
				return error;
			}
			if (direction.equals("right")) {
				column += 1;
				this.setParm("column", String.valueOf(column));
			}
			if (direction.equals("left")) {
				column -= 1;
				this.setParm("column", String.valueOf(column));
			}
			if (direction.equals("up")) {
				line -= 1;
				this.setParm("line", String.valueOf(line));
			}
			if (direction.equals("down")) {
				line += 1;
				this.setParm("line", String.valueOf(line));
			}
		}

		Template template = new Template();
		
		View v = null;

		if (view.equals("addsong")) {
			v = new ViewAddSong();
			template.addSectionHandler((ViewAddSong)v, "body");
		} else if (view.equals("title")) {
			v = new ViewTitle();
			template.addSectionHandler((ViewTitle)v, "name");
			template.addSectionHandler((ViewTitle)v, "songid");
		} else if (view.equals("test")) {
			v = new ViewTest();
			template.addSectionHandler((ViewTest)v, "body");
		} else if (view.equals("export")) {
			v = new ViewExport();
		} else if (view.equals("editsong")) {
			v = new ViewEditSong();
		} else if (view.equals("viewsong")) {
			v = new ViewSong();
		} else if (view.equals("viewsharedsong")) {
				v = new ViewSharedViewSong();
		} else if (view.equals("showmedia")) {
			v = new ViewShowMedia();
		} else if (view.equals("comment")) {
			v = new ViewComment();
		} else if (view.equals("editsongtext")) {
			v = new ViewEditSongText();
		} else if (view.equals("editchord")) {
			v = new ViewEditChord();
			((ViewEditChord)v).setChordPosition(updateChordPosition);
		} else if (view.equals("addchord")) {
			v = new ViewAddChord();
		} else if (view.equals("settings")) {
			v = new ViewSettings();
		} else if (view.equals("renamechord")) {
			v = new ViewAddChord();
			((ViewAddChord)v).setChord(getParm("chord"));
			view = "addchord";
		} else if (view.equals("login")) {
			v = new ViewLogin();
		} else if (view.equals("openbook")) {
			v = new ViewOpenBook();
			template.addSectionHandler((ViewOpenBook)v, "body");
		} else if (view.equals("zoominsong")) {
			v = new ViewZoomInSong();
			template.addSectionHandler((ViewZoomInSong)v, "body");
		} else {
			v = new ViewOpenBook();
			template.addSectionHandler((ViewOpenBook)v, "body");
		}
		
		v.setApp(this);
		v.setName(view);
		v.setTemplate(template);
 		
		TextFile file = new TextFile();
		file.setBuffer(template);
		
		if (!file.load("/projects/chordsequence/template/", view, "layout")) {
			String error = getClass().getName() + ": error loading view " + view; 
			Log.println(error);
			return error;
		}

		if (!(action.isEmpty() || action.equals("login") || action.equals("logout") || 
			  action.equals("updatechord") || action.equals("updatechordstart") ||
			  action.equals("nextbasefret") || action.equals("previousbasefret"))) {
			library.setChangeFlag(true);
		}
				
		return template.render();
	}

	private void setLinesPerPage(String numLinesStr) {
		
		if (numLinesStr == null || numLinesStr.isEmpty()) {
			return;
		}
		
		int i = 0;
		try {
			i = Integer.parseInt(numLinesStr);
		} catch (Exception e) {
			i = 0;
		}
		
		if (i > 0 && i < MAXLINESPERPAGE) {
			linesPerPage = i;
		}
		
	}
	
	private String export(String songId) {
		Song song = library.getSongs().get(songId);
		
		if (song == null) {
			Log.println(getClass().getName() + ": error getting " + songId);
			return null;
		}
		
		return addSharedSong(song);
	}

	private String addChord(String songId, String chord) {
		if (songId == null || chord == null ) {
			String error = ": unable to move base fret null parameter";
			Log.println(getClass().getName() + error);
			return error;
		}

		if (songId.isEmpty() || chord.isEmpty()) {
			String error = ": unable to move base fret empty parameter";
			Log.println(getClass().getName() + error);
			return error;
		}
		
		Song song = library.getSongs().get(songId);
		
		if (song == null) {
			String error = getClass().getName() + ": unable to find songid " + songId;
			Log.println(getClass().getName() + error);
			return error;
		}

		song.addChord(chord);
		
		return null;
	}

	private String renameChord(String songId, String chord) {
		if (songId == null || chord == null ) {
			String error = ": unable to move base fret null parameter";
			Log.println(getClass().getName() + error);
			return error;
		}

		if (songId.isEmpty() || chord.isEmpty()) {
			String error = ": unable to move base fret empty parameter";
			Log.println(getClass().getName() + error);
			return error;
		}
		
		Song song = library.getSongs().get(songId);
		
		if (song == null) {
			String error = getClass().getName() + ": unable to find songid " + songId;
			Log.println(getClass().getName() + error);
			return error;
		}

		song.renameChord(getParm("fromchord"), chord);
		
		return null;
	}

	private String deleteChord(String songId, String chord) {
		if (songId == null || chord == null ) {
			String error = ": unable to move base fret null parameter";
			Log.println(getClass().getName() + error);
			return error;
		}

		if (songId.isEmpty() || chord.isEmpty()) {
			String error = ": unable to move base fret empty parameter";
			Log.println(getClass().getName() + error);
			return error;
		}
		
		Song song = library.getSongs().get(songId);
		
		if (song == null) {
			String error = getClass().getName() + ": unable to find songid " + songId;
			Log.println(getClass().getName() + error);
			return error;
		}

		song.deleteChord(chord);
		
		return null;
	}

	private String updateSong(String songId, String songText) {
		
		if (songId == null || songText == null ) {
			String error = ": unable to update song null parameter";
			Log.println(getClass().getName() + error);
			return error;
		}

		if (songId.isEmpty() || songText.isEmpty()) {
			String error = ": unable to update song empty parameter";
			Log.println(getClass().getName() + error);
			return error;
		}
				
		Song song = library.getSongs().get(songId);

		if (song == null) {
			String error = ": error looking up song " + songId;
			Log.println(getClass().getName() + error);
			return error;
		}

		song.fromString(songText);

		return null;
	}
	
	
	private String moveBaseFret(String action) {
		
		if (updateChordPosition == null) {
			String error = ": unable to find chord position";
			Log.println(getClass().getName() + error);
			return error;
		}

		if (action.equals("nextbasefret")) {
			updateChordPosition.nextBaseFret();
		}
		if (action.equals("previousbasefret")) {
			updateChordPosition.previousBaseFret();
		}

		return null;
	}
	
	@Override
	public Library getDoc() {
		return library;
	}

	

	private Song getSong() {
		String songId = getParm("songid");
		
		HashMap<String, Song> songs = library.getSongs();
		
		return songs.get(songId);
	}
	
	@Override
	public String getParm(String parmName) {
		String parm = parms.get(parmName);
		
		if (parmName.equals("chord")) {
			if (parm == null || parm.isEmpty()) {
				if (getSong() == null || getSong().getChordSequence() == null || getSong().getChordSequence().size() == 0) {
					parm = "";
				} else {			
					parm = getSong().getChordSequence().get(0);
				}
			}
		}

		if (parmName.equals("pagenum")) {
			if (parm == null || parm.isEmpty()) {
				parm = "0";
			}
		}	
		
		return parm;
	}

	@Override
	public void setSessionId(String theSessionId) {
		sessionId = theSessionId;
		
	}

	@Override
	public void renderImage(String action, String view, OutputStream out) {

		render(action, view);
		
		Song song = getSharedSong(parms.get("sharedsongid"));
		
		if (song == null) {
			String error = getClass().getName() + ": error, can't find song " + parms.get("songid");
			Log.println(error);
		}

		
		Image image = new Image();
		
		image.loadSvg(song.toSvg());

		try {
			image.save(out);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
