package com.chordsequence.app;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import com.chordsequence.server.App;
import com.chordsequence.song.Library;
import com.chordsequence.song.Song;
import com.chordsequence.util.Template;
import com.chordsequence.util.TemplateSectionHandler;

public class ViewOpenBook implements View, TemplateSectionHandler {

	GuitarSongsApp app = null;
	protected String name = "";

	@Override
	public void setName(String viewName) {
		name = viewName;		
	}

	private String renderSong(Song song, boolean editMode, String title) {
		String result = "<tr height=\"30\"><td width=\"50\" /><td><a href=\"javascript:viewsong(" + song.getId() +
				")\">" +
				title + 
				"</a></td>";
		
		if (editMode) {
			result += "<td><a href=\"/chordsequence?view=openbook&action=deletesong&openmode=edit&songid=" + song.getId() + "\"><img height=\"30\" width=\"30\" src=\"/images/delete.png\"/></a></td>";
		}
		 
		result += "</tr>\n";
		
		return result; 
	}

	@Override
	public String renderSection(String section) {
		
		String result = "";

		if (section.equals("body")) {
			Library lib = (Library)app.getDoc();

			HashMap<String, Song> songs = lib.getSongs();
			
			String action = app.getParm("openmode");
			
			boolean editMode = action != null && action.equals("edit");
			
			result += "<table>";

			Song welcome = lib.findSong("Welcome to Chord Sequence");
			
			if (welcome != null) {
				result += renderSong(welcome, editMode, "Introduction");
			}
			
			for (String id = app.getDoc().firstSongId(); id != null; id = app.getDoc().nextSongId(id)) {
				Song song = songs.get(id);
	
				if (song != null && !song.getTitle().equals("Welcome to Chord Sequence")) {
					result += renderSong(song, editMode, song.getTitle());
				}
			}
			
			result += "</table>";
		} else if (section.equals("title")) {

			result = "<div id=\"" + section + "\">\n";
			ViewTitle title = new ViewTitle();
			title.setApp(app);
			title.setSong(null);
			title.setEditView("openbook");
			title.setView(name);
			
			result = title.render();
			result += "</div>\n";
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
		template.addSectionHandler(this, "title");
	}

}
