package com.ziemo.redirect.controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import com.ziemo.redirect.helper.URIParser;
import com.ziemo.redirect.model.Student;
import com.ziemo.redirect.view.WebDisplay;

import java.io.*;
import java.net.URLDecoder;
import java.util.*;

public class StudentController implements HttpHandler {

	private final WebDisplay display = new WebDisplay();

	private String method;
	private String response;
	private HttpExchange exchange;

	private String name;
	private String last;
	private String age;
	private String value = null;

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		method = exchange.getRequestMethod();
		this.exchange = exchange;

		String data = URIParser.readRoute(exchange).get("data");
		value = URIParser.readRoute(exchange).get("action");

		switch (data) {
			case "index":
				index();
				break;
			case "add":
				add();
				break;
			case "edit":
				edit();
				break;
			case "delete":
				delete();
				break;
			case "redirect":
				redirect();
				break;
		}

		exchange.sendResponseHeaders(200, response.length());

		OutputStream os = exchange.getResponseBody();
		os.write(response.getBytes());
		os.close();
	}

	private void redirect() throws IOException {
		exchange.getResponseHeaders().set("Location", "/students");
		exchange.sendResponseHeaders(302, -1);
	}

	private void index() throws IOException {
		response = display.getRecordsPage(value);
	}

	private void add() throws IOException {
		if (method.equalsIgnoreCase("POST")) {
			readData();
			redirect();
			response = display.getRecordsPage("record added!");
		} else {
			response = display.getAmendPage();
		}
	}

	private void readData() throws IOException {

		InputStreamReader isr = new InputStreamReader(exchange.getRequestBody());
		BufferedReader br = new BufferedReader(isr);

		String inputs = br.readLine();


		Map<String, String> parsedInputs = parseInputs(inputs);

		name = parsedInputs.get("name");
		last = parsedInputs.get("last");
		age = parsedInputs.get("age");

		handleTypeAction();
	}

	private void handleTypeAction() {
		if (value == null) {
			new Student(name, last, Integer.valueOf(age));
		} else {
			List<String> newData = Arrays.asList(name, last, age);
			Student.edit(value, newData);
		}
	}

	private Map<String, String> parseInputs(String inputs) throws UnsupportedEncodingException {

		Map<String, String> parsedInputs = new HashMap<>();

		String[] pairs = inputs.split("&");

		for (String pair : pairs) {
			String[] keyValue = pair.split("=");
			String value = URLDecoder.decode(keyValue[1], "UTF-8");
			parsedInputs.put(keyValue[0], value);
		}
		return parsedInputs;
	}

	private void edit() throws IOException {
		if (method.equalsIgnoreCase("POST")) {
			readData();
			redirect();
			response = display.getRecordsPage("record modify!");
		} else {
			response = display.getAmendPage();
		}
	}

	private void delete() throws IOException {
		Student.removeRecordBy(String.valueOf(value));
		redirect();
		response = display.getRecordsPage("Record has been successfully removed");
	}

}
