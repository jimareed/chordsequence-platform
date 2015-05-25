package com.chordsequence.server;

import java.util.HashMap;

import com.chordsequence.app.GuitarSongsApp;

public interface Server {

	HashMap<String, GuitarSongsApp> getSessions();
}
