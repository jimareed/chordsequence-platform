package com.chordsequence.app;

import com.chordsequence.server.App;
import com.chordsequence.song.ChordChart;
import com.chordsequence.song.ChordPosition;
import com.chordsequence.song.Library;
import com.chordsequence.song.Song;
import com.chordsequence.util.Log;
import com.chordsequence.util.Template;
import com.chordsequence.util.TemplateSectionHandler;

public class ViewEditChord implements View, TemplateSectionHandler {

	GuitarSongsApp app = null;
	ChordPosition chordPosition = null;
	protected String name = "";

	@Override
	public void setName(String viewName) {
		name = viewName;		
	}
	
	public void setChordPosition(ChordPosition cp) {
		chordPosition = cp;
	}
	
	@Override
	public String renderSection(String section) {
		
		Library lib = (Library)app.getDoc();
		
		String songId = app.getParm("songid");
		String chord = app.getParm("chord");
		String pageNum = app.getParm("pagenum");
		
		if (songId == null || songId.isEmpty()) {
			String error = getClass().getName() + ": unable to edit chord, missing songid";
			Log.println(getClass().getName() + error);
			return error;
		}

		if (pageNum == null) {
			Log.println(getClass().getName() + ": error, missing page num");
			pageNum = "0";
		}
		
		Song song = lib.getSongs().get(songId);
		
		String result = "";
		
		if (section.equals("body")) {
			result = "<div id=\"" + section + "\">\n";

	  		ChordChart chart = new ChordChart();

	  		ChordPosition position = chordPosition;
	  		
	  		if (position == null) {
		  		position = song.getChordPosition(chord);
	  		}
	  		
			result += chart.renderZoomMore(position, chord);

			result += "</div>\n";
	  	}
		
		if (section.equals("songid")) {
			result = songId;
		}
		if (section.equals("chordnoencode")) {
			result = chord;
		}
		if (section.equals("chord")) {
			result = encode(chord);
		}
		
		if (section.equals("pagenum")) {
			result = pageNum;
		}
			
		return result;
	}

	private String encode(String s) {
		String e =  s.replaceAll("#", "%23");
		return e;		
	}
	
	
	@Override
	public void setApp(App theApp) {
		app = (GuitarSongsApp)theApp;
		
	}

	@Override
	public void setTemplate(Template template) {
		template.addSectionHandler(this, "chord");
		template.addSectionHandler(this, "chordnoencode");
		template.addSectionHandler(this, "songid");
		template.addSectionHandler(this, "pagenum");
		template.addSectionHandler(this, "body");
	}

}
