package com.chordsequence.song;

public class TabChart {
	
	public String render(Tab tab, String format, String size, boolean highlight) {
		String result = "";

		int xText = 22 - (tab.getName().length()-1) * 6;
		
		if (xText < 0) {
			xText = 0;
		}

		if (format.equals("html")) {
			result += "<table><tr><td>";
			result += "<svg xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" width=\"55\" height=\"20\">";
		}
		
		if (format.equals("html")) {
			result += "<rect x=\"0\" y=\"" + Integer.toString(0) + "\" width=\"55\" height=\"20\" style=\"fill:rgb(0,0,0);stroke-width:0;stroke:rgb(0,0,0) stroke-opacity=\"0.0\" fill-opacity=\"0.0\" onclick=\"editchord('" + tab.getName() + "')\"/>";
			
			result += "<text x = \"" + Integer.toString(xText) + "\" y = \"16\" font-size = \"16\" font-weight=\"bold\" onclick=\"edittab('" + tab.getName() + "')\">" + tab.getName() + "</text>\n";

			
			result += "</svg>";

			result += "<div id=\"" + tab.getName() + "\" style=\"height: 50%\" draggable=\"true\" ondragstart=\"drag(event)\" >";
		}

		result += render(tab, size, highlight);

		if (format.equals("html")) {
			result += "</div></td></tr></table>";
		}		

		
		return result;
	}
	

	public String renderImage(Tab tab, int height, int imageWidth) {
		int xOffset = 60;
		int yOffset = height;
		
		String result = "<text x = \"" + Integer.toString(7+xOffset) + "\" y = \"" + Integer.toString(yOffset-2) + "\" font-size = \"12\" font-weight=\"bold\">" + tab.getName() + "</text>";

		result += render(tab, "medium", false, xOffset, yOffset, false);

		return result;
	}
	
	public String render(Tab tab, String size, boolean highlight) {
		return render(tab, size, true, 0, 0, highlight);
	}
	
	private String encode(String s) {
		String e =  s.replaceAll("#", "%23");
		return e;		
	}
	
	private String render(Tab tab, String size, boolean addSvgElement, int xOffset, int yOffset, boolean highlight) {
		
		String result = "";
		

		int xText = 22 - (tab.getName().length()-1) * 6;
		
		if (xText < 0) {
			xText = 0;
		}

		int width = 45;
		int maxNumOfTabChars = 6;
		
		if (size.equals("medium")) {
			maxNumOfTabChars = 200;
			width = tab.getWidth()*6+20;
		}
		
		if (addSvgElement) {
			result += "<svg width=\"" + Integer.toString(width+10) + "\" height=\"" + Integer.toString(40+yOffset) + "\">";
		}

		String rectStrokeOpacity = "0.0";
		if (highlight) {
			rectStrokeOpacity = "1.0";
		}
		
		result += "<rect x=\"0\" y=\"" + Integer.toString(0) + "\" width=\"" + Integer.toString(width+10) + "\" height=\"" + Integer.toString(40+yOffset) + "\" style=\"fill:rgb(0,0,0);stroke-width:2;stroke:rgb(255,0,0)\" stroke-opacity=\""+ rectStrokeOpacity + "\" fill-opacity=\"0.0\" onclick=\"selectchord('" + encode(tab.getName()) + "')\"/>";
		
		result += 
		  "<line x1=\"" + Integer.toString(7+xOffset) + "\" y1=\"" + Integer.toString(5+yOffset) + "\" x2=\"" + Integer.toString(7+xOffset) + "\" y2=\"" +  Integer.toString(37+yOffset)  + "\" style=\"stroke:rgb(0,0,0);stroke-width:3\" />" +
		  "<line x1=\"" + Integer.toString(7+xOffset) + "\" y1=\"" + Integer.toString(6+yOffset) + "\" x2=\"" + Integer.toString(width+xOffset) + "\" y2=\"" + Integer.toString(6+yOffset) + "\" style=\"stroke:rgb(0,0,0);stroke-width:.5\" />" +
		  "<line x1=\"" + Integer.toString(7+xOffset) + "\" y1=\"" + Integer.toString(12+yOffset) + "\" x2=\"" + Integer.toString(width+xOffset) + "\" y2=\"" + Integer.toString(12+yOffset) + "\" style=\"stroke:rgb(0,0,0);stroke-width:.5\" />" +
		  "<line x1=\"" + Integer.toString(7+xOffset) + "\" y1=\"" + Integer.toString(18+yOffset) + "\" x2=\"" + Integer.toString(width+xOffset) + "\" y2=\"" + Integer.toString(18+yOffset) + "\" style=\"stroke:rgb(0,0,0);stroke-width:.5\" />" +
		  "<line x1=\"" + Integer.toString(7+xOffset) + "\" y1=\"" + Integer.toString(24+yOffset) + "\" x2=\"" + Integer.toString(width+xOffset) + "\" y2=\"" + Integer.toString(24+yOffset) + "\" style=\"stroke:rgb(0,0,0);stroke-width:.5\" />" +
		  "<line x1=\"" + Integer.toString(7+xOffset) + "\" y1=\"" + Integer.toString(30+yOffset) + "\" x2=\"" + Integer.toString(width+xOffset) + "\" y2=\"" + Integer.toString(30+yOffset) + "\" style=\"stroke:rgb(0,0,0);stroke-width:.5\" />" +
		  "<line x1=\"" + Integer.toString(7+xOffset) + "\" y1=\"" + Integer.toString(36+yOffset) + "\" x2=\"" + Integer.toString(width+xOffset) + "\" y2=\"" + Integer.toString(36+yOffset) + "\" style=\"stroke:rgb(0,0,0);stroke-width:.5\" />" +
		  "<line x1=\"" + Integer.toString(width+xOffset) + "\" y1=\"" + Integer.toString(6+yOffset) + "\" x2=\"" + Integer.toString(width+xOffset) + "\" y2=\"" + Integer.toString(36+yOffset) + "\" style=\"stroke:rgb(0,0,0);stroke-width:.5\" />";

		if (tab != null) {
			for (int i = 1; i <= tab.numStrings(); i++) {
				String s = tab.getString(i);
				if (s != null) {
					int maxPos = s.length();
					if (maxPos > maxNumOfTabChars) {
						maxPos = maxNumOfTabChars;
					}
					for (int pos = 0; pos < maxPos; pos++) {
						String value = s.substring(pos,pos+1);
						if (!value.equals("-")) {
							result += renderTabPosition(pos+1, i, value, xOffset, yOffset);
						}
					}
				}
			}
		}

		if (addSvgElement) {
			result += "</svg>";
		}

		return result;
	}

	private String renderTabPosition(int x, int y, String value, int xOffset, int yOffset) {
		if (value.equals("-")) {
			return "";
		}
		return "<text x = \"" + Integer.toString(x*6+4+xOffset) + "\" y = \"" + Integer.toString(y*6+3 + yOffset) + "\" font-size = \"10\" font-family=\"Courier\" font-weight=\"bold\">" + value + "</text>\n";
	}
}
