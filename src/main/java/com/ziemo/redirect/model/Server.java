package com.ziemo.redirect.model;

import com.sun.net.httpserver.HttpServer;
import com.ziemo.redirect.controller.Static;
import com.ziemo.redirect.controller.StudentController;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Server {

	private HttpServer server;
	private final int port;

	public Server(int port) {
		this.port = port;
	}

	public void setup() throws IOException {
			server = HttpServer.create(new InetSocketAddress(port), 0);
			server.createContext("/", new StudentController());
			server.createContext("/static", new Static());
			server.setExecutor(null);
	}

	public void runServer() {
		server.start();
	}
}
