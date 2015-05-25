package com.chordsequence.app;

import java.util.ArrayList;
import java.util.HashMap;

import com.chordsequence.server.App;
import com.chordsequence.song.ChordChart;
import com.chordsequence.song.ChordPosition;
import com.chordsequence.song.Library;
import com.chordsequence.song.Song;
import com.chordsequence.util.Log;
import com.chordsequence.util.Template;
import com.chordsequence.util.TemplateSectionHandler;

public class ViewShowMedia implements View, TemplateSectionHandler {

	GuitarSongsApp app = null;
	protected String name = "";

	@Override
	public void setName(String viewName) {
		name = viewName;		
	}
	
	@Override
	public String renderSection(String section) {
		
		Library lib = (Library)app.getDoc();
		
		String songId = app.getParm("songid");

		HashMap<String, Song> songs = lib.getSongs();
		
		String result = "";
		
		Song song = songs.get(songId);
		
		if (song == null) {
			String error = getClass().getName() + ": error, can't find song " + songId;
			Log.println(error);
		}
		

		if (section.equals("body")) {
			result = song.toSvg();
		}
		
		return result;
	}

	
	@Override
	public void setApp(App theApp) {
		app = (GuitarSongsApp)theApp;
		
	}

	@Override
	public void setTemplate(Template template) {

		template.addSectionHandler(this, "body");
		template.addSectionHandler(this, "chordchart");
		template.addSectionHandler(this, "title");
	}

}
