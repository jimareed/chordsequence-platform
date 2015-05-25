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

public class ViewOpenSong implements View, TemplateSectionHandler {

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
		String pageNumStr = app.getParm("pagenum");

		HashMap<String, Song> songs = lib.getSongs();
		
		String result = "";
		
		Song song = songs.get(songId);
		
		if (song == null) {
			String error = getClass().getName() + ": error, can't find song " + songId;
			Log.println(error);
		}
		
		if (pageNumStr == null || pageNumStr.isEmpty()) {
			pageNumStr = "0";
		}		

		int pageNum = 0;
		int songNum = 0;

		try {
			pageNum = Integer.parseInt(pageNumStr);
			songNum = Integer.parseInt(songId);
			
		} catch (Exception e) {
		}
		
		if (section.equals("body")) {
			int width = 980;

			try {
				int w = Integer.parseInt(app.getParm("width"));
				width = w;
			} catch (Exception e) {
				
			}
			
			result = "<div id=\"" + section + "\" >\n";
			
	  		result +=  song.toSvg(pageNum, width);	  		
			result += "</div>\n";
	  	}
		else if (section.equals("pagenum")) {
			return(pageNumStr);
	  	}
		else if (section.equals("nextpagenum")) {
			if ((pageNum == song.numPages()-1) && songNum < lib.size()-1) {
				pageNum = 0;
			} else if (pageNum < song.numPages()-1) {
				pageNum++;
			}
			
			return(Integer.toString(pageNum));
	  	}
		else if (section.equals("previouspagenum")) {
			if (pageNum == 0 && songNum > 0) {
				songNum--;
				Song newSong = lib.getSongs().get(Integer.toString(songNum));
				if (newSong != null) {
					pageNum = newSong.numPages()-1;
				}
			} else if (pageNum > 0) {
				pageNum--;
			}
			
			return(Integer.toString(pageNum));
	  	}
		else if (section.equals("nextsongid")) {
			String newSongId = songId;

			if (pageNum == song.numPages()-1) {
				songNum++;
				while (songs.get(Integer.toString(songNum)) == null && songNum < lib.getNextId()) {
					songNum++;
				}
				if (songNum > lib.getMaxId()) {
					songNum = lib.getMaxId();
				}
				newSongId = Integer.toString(songNum);
			} else {
				newSongId = songId;
			}
			
			return(newSongId);
	  	}
		else if (section.equals("previoussongid")) {
			String newSongId = songId;

			if (pageNum == 0) {
				songNum--;
				while (songs.get(Integer.toString(songNum)) == null && songNum >= 0) {
					songNum--;
				}
				if (songNum < -1) {
					songNum = -1;
				}
				newSongId = Integer.toString(songNum);
			} else {
				newSongId = songId;
			}
			
			return(newSongId);
	  	}
		else if (section.equals("songid")) {
			return(songId);
	  	}
		else if (section.equals("title")) {

			result = "<div id=\"" + section + "\">\n";
			ViewTitle title = new ViewTitle();
			title.setApp(app);
			title.setSong(song);

			result = title.render();
			result += "</div>\n";
	  	}
		else if (section.equals("chordchart")) {
			result = "<div id=\"" + section + "\">\n";
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

	  		if (chordCount < sequence.size()) {
		  		for (int i = 0; i < sequence.size(); i++) {
		  			String ch = sequence.get(i);

					if (ch.startsWith("riff") || ch.startsWith("Riff")) {
						result += "<td>";
						
						Tab tab = null;
						
						if (song.getTabs() != null) {
							tab = song.getTabs().get(ch);
						}
						
						if (tab == null) {
							tab = new Tab(ch);
						}

						TabChart tch = new TabChart();
						
						result += tch.render(tab, "html", "small", false);
						chordCount++;
						result += "</td>";
					}
					if (chordCount % 8 == 0) {
						result += "</tr></table><table><tr align=\"center\">";
					}
		  		}
	  		}
	  		
	  		if (sequence.size() > 0 && sequence.size() % 8 == 0) {
				result += "</tr></table><table><tr align=\"center\">";
	  		}
			
			result += "<td><p><a href=\"/chordsequence?view=addchord&songid=" + song.getId() + "\">Add...</p></td>";

	  		result += "</tr></table>";
			result += "</div>\n";
		
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

		template.addSectionHandler(this, "body");
		template.addSectionHandler(this, "chordchart");
		template.addSectionHandler(this, "title");
		template.addSectionHandler(this, "songid");
		template.addSectionHandler(this, "pagenum");
		template.addSectionHandler(this, "nextsongid");
		template.addSectionHandler(this, "previoussongid");
		template.addSectionHandler(this, "nextpagenum");
		template.addSectionHandler(this, "previouspagenum");
	}

}
