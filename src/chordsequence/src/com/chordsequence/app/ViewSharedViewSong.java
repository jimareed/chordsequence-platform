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

public class ViewSharedViewSong implements View, TemplateSectionHandler {

	GuitarSongsApp app = null;
	protected String name = "";

	@Override
	public void setName(String viewName) {
		name = viewName;		
	}
	
	@Override
	public String renderSection(String section) {
		
		String sharedSongId = app.getParm("sharedsongid");
		String songId = app.getParm("songid");

		String result = "";
		
		Song song = app.getSharedSong(sharedSongId);
		
		if (song == null) {
			String error = getClass().getName() + ": error, can't find song " + songId;
			Log.println(error);
		}

		if (section.equals("title")) {
			result = "<h1>" + song.getTitle() + "</h1>";
		}
		else if (section.equals("urltitle")) {
			result = song.getTitle();
			if (!song.getArtist().isEmpty()) {
				result += " by " + song.getArtist();
			}
			result = result.replace(" ", "%20");
		}
		else if (section.equals("sharedsongid")) {
			result = sharedSongId;
		}
		else if (section.equals("songid")) {
			result = songId;
		}
		else if (section.equals("artist")) {
			result = "<b>" + song.getArtist() + "</b>";
		}
		else if (section.equals("chordchart")) {
			result = "<div id=\"" + section + "\">\n";
	  		song.getChordPosition("");	// need this to load chord positions

	  		result += "<table><tr>";
	  		ArrayList<String> sequence = song.getChordSequence();

	  		int numOfRiffs = 0;
	  		
	  		for (int i = 0; i < sequence.size(); i++) {
	  			String ch = sequence.get(i);

				ChordChart chart = new ChordChart();
				
				ChordPosition position = song.getChordPosition(ch);

				if ((ch.startsWith("riff") || ch.startsWith("Riff"))) {
					numOfRiffs++;
				} else {
					result += "<td>";
					result += chart.renderSmall(position, ch, "html", false);
					result += "</td>";
				}
				if ((i != sequence.size()-1) && (i+1) % 8 == 0) {
					result += "</tr></table><table><tr align=\"center\">";
				}
	  		}
	  		
	  		if (numOfRiffs > 0) {
	  			int riffCount = 0;
				result += "</tr></table><table><tr align=\"center\">";
		  		for (int i = 0; i < sequence.size(); i++) {
		  			String ch = sequence.get(i);

					if (ch.startsWith("riff") || ch.startsWith("Riff")) {
						Tab tab = null;
						
						if (song.getTabs() != null) {
							tab = song.getTabs().get(ch);
						}
						
						if (tab == null) {
							tab = new Tab(ch);
						}
						
						TabChart chart = new TabChart();
						result += "<td>";
						result += chart.render(tab, "html", "medium", false);
						result += "</td>";
						riffCount++;
					}
					if ((riffCount != numOfRiffs) && (riffCount) % 1 == 0) {
						result += "</tr></table><table><tr align=\"center\">";
					}
		  		}
	  		}

	  		
	  		result += "</tr></table>";
			result += "</div>\n";
		
		}
		else if (section.equals("body")) {
			int width = 980;

			try {
				int w = Integer.parseInt(app.getParm("width"));
				width = w;
			} catch (Exception e) {
				
			}
			
			result = "<div id=\"" + section + "\" >\n";
			
	  		result +=  song.toSvg(0, width);	  		
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
		template.addSectionHandler(this, "chordchart");
		template.addSectionHandler(this, "title");		
		template.addSectionHandler(this, "urltitle");		
		template.addSectionHandler(this, "artist");		
		template.addSectionHandler(this, "sharedsongid");		
		template.addSectionHandler(this, "songid");		
	}

}
