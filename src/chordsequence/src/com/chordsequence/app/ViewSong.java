package com.chordsequence.app;

import java.util.ArrayList;
import java.util.HashMap;

import com.chordsequence.server.App;
import com.chordsequence.song.ChordChart;
import com.chordsequence.song.ChordPosition;
import com.chordsequence.song.Library;
import com.chordsequence.song.Song;
import com.chordsequence.song.Tab;
import com.chordsequence.song.TabChart;
import com.chordsequence.util.Log;
import com.chordsequence.util.Template;
import com.chordsequence.util.TemplateSectionHandler;

public class ViewSong implements View, TemplateSectionHandler {

	protected GuitarSongsApp app = null;
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
		Song song = songs.get(songId);		
		if (song == null) {
			String error = getClass().getName() + ": error, can't find song " + songId;
			Log.println(error);
			return(error);
		}

		if (section.equals("body")) {
			int width = 980;

			try {
				int w = Integer.parseInt(app.getParm("width"));
				width = w;
			} catch (Exception e) {
				
			}
			
			String result = "<div id=\"" + section + "\" >\n";
			
	  		result +=  song.toSvg(0, width, song.numLines(), false);	  		
			result += "</div>\n";
			return(result);
		} else if (section.equals("chordchart")) {
			String result = "<div id=\"" + section + "\">\n";
	  		song.getChordPosition("");	// need this to load chord positions

	  		result += "<table><tr>";
	  		ArrayList<String> sequence = song.getChordSequence();

	  		int chordCount = 0;
	  		for (int i = 0; i < sequence.size(); i++) {
	  			String ch = sequence.get(i);

				ChordChart chart = new ChordChart();
				
				ChordPosition position = song.getChordPosition(ch);

				if (!(ch.startsWith("riff") || ch.startsWith("Riff"))) {
					result += "<td>";
					result += chart.renderSmall(position, ch, "html", false);
					chordCount++;
					result += "</td>";
				}
				if (chordCount % 8 == 0) {
					result += "</tr></table><table><tr align=\"center\">";
				}
	  		}

	  		result += "</tr></table>";
	  		
	  		if (chordCount < sequence.size()) {
		  		for (int i = 0; i < sequence.size(); i++) {
		  			String ch = sequence.get(i);

					if (ch.startsWith("riff") || ch.startsWith("Riff")) {
						result += "<table><tr align=\"center\"><td>";
						
						Tab tab = null;
						
						if (song.getTabs() != null) {
							tab = song.getTabs().get(ch);
						}
						
						if (tab == null) {
							tab = new Tab(ch);
						}

						TabChart tch = new TabChart();
						
						result += tch.render(tab, "html", "medium", false);
						chordCount++;
						result += "</td></tr></table>";
					}
		  		}
	  		}
	  		
			result += "</div>\n";
			return (result);
		} else if (section.equals("title")) {
			String result = "<div id=\"" + section + "\">\n";
			ViewTitle title = new ViewTitle();
			title.setApp(app);
			title.setSong(song);
			title.setEditView("editsong");
			title.setView(name);
			
			result = title.render();
			result += "</div>\n";
			return(result);
		} else if (section.equals("nextsongid")) {
			String newSongId = lib.nextSongId(songId);
			if (newSongId == null) {
				newSongId = songId;
			}
			return(newSongId);
		} else if (section.equals("previoussongid")) {
			String newSongId = lib.previousSongId(songId);
			if (newSongId == null) {
				newSongId = songId;
			}
			return(newSongId);
		} else if (section.equals("songid")) {
			return("songid");
		}
		return null;
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
		template.addSectionHandler(this, "songid");
		template.addSectionHandler(this, "nextsongid");
		template.addSectionHandler(this, "previoussongid");
		
	}

}
