package com.ziemo.redirect;

import com.ziemo.redirect.model.Server;

import java.io.IOException;

public class App {

	public static void main(String[] args) {
		try {
			Server server = new Server(8000);
			server.setup();
			server.runServer();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}


// look at this project
// look at prevoius project
