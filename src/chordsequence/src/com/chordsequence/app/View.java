package com.chordsequence.app;

import com.chordsequence.server.App;
import com.chordsequence.util.Template;

public interface View {

	public void setApp(App app);
	public void setName(String viewName);
	public void setTemplate(Template template);
}
