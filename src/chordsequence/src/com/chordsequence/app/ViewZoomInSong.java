package com.chordsequence.app;

import java.util.HashMap;

import com.chordsequence.server.App;
import com.chordsequence.song.Library;
import com.chordsequence.song.Song;
import com.chordsequence.util.Log;
import com.chordsequence.util.Template;
import com.chordsequence.util.TemplateSectionHandler;

public class ViewZoomInSong implements View, TemplateSectionHandler {

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

		String sx = app.getParm("x");
		String sy = app.getParm("y");
		String sline = app.getParm("line");
		String scolumn = app.getParm("column");
		String pageNum = app.getParm("pagenum");

		if (sx == null || sx.isEmpty()) {
			sx = "0";
		}
		if (sy == null || sy.isEmpty()) {
			sy = "0";
		}

		if (songId == null || songId.isEmpty()) {
			String error = getClass().getName() + ": unable to zoom in missing parameters";
			Log.println(getClass().getName() + error);
			return error;
		}

		if (pageNum == null) {
			Log.println(getClass().getName() + ": error, missing page num");
			pageNum = "0";
		}
		
		int line = -1;
		int column = -1;

		if (sline != null && !sline.isEmpty()) {
			line = Integer.parseInt(sline);
		}
		if (scolumn != null && !scolumn.isEmpty()) {
			column = Integer.parseInt(scolumn);
		}
		
		HashMap<String, Song> songs = lib.getSongs();
		
		String result = "";
		
		Song song = songs.get(songId);
		
		if (song == null) {
			String error = getClass().getName() + ": error, can't find song " + songId;
			Log.println(error);
		}
		
		
		if (section.equals("body")) {
			result = "<div id=\"" + section + "\" ondrop=\"drop(event)\" ondragover=\"allowDrop(event)\">\n";
	  		result +=  song.toSvgZoomed(line, column, songId, pageNum);	  		
			result += "</div>\n";
	  	}
		
		if (section.equals("songid")) {
			result = songId;
		}
		
		if (section.equals("pagenum")) {
			result = pageNum;
		}
		
		return result;
	}

	/*
	private String export(Song song) {
		String result = "";

		String lines[] = song.toString().split("\n");
		
		for (int i = 0; i < lines.length; i++) {
				result += "<tr><td>" + lines[i] + "</td></tr>";
		}
		
		return "<table>" + result + "</table>";
	}*/
	
	@Override
	public void setApp(App theApp) {
		app = (GuitarSongsApp)theApp;
		
	}

	@Override
	public void setTemplate(Template template) {

		template.addSectionHandler(this, "songid");
		template.addSectionHandler(this, "pagenum");
		template.addSectionHandler(this, "body");
	}

}
