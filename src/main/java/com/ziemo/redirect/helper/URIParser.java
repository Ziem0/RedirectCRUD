package com.ziemo.redirect.helper;

import com.sun.net.httpserver.HttpExchange;
import com.ziemo.redirect.model.Student;

import java.net.URI;
import java.util.*;

public class URIParser {

	public static Map<String, String> readRoute(HttpExchange exchange) {
		URI uri = exchange.getRequestURI();
		String route = uri.getPath();

		Map <String, String>parsedUri = parseURI(route);

		return parsedUri;
	}

	private static Map<String, String> parseURI(String route) {
		Map<String, String> parsedUri = new HashMap<>();
		parsedUri.put("data", null);
		parsedUri.put("action", null);

		if (route.equalsIgnoreCase("/students")) {
			parsedUri.put("data", "index");

		}else if (!route.startsWith("/students")) {
			parsedUri.put("data", "redirect");

		} else if (route.equalsIgnoreCase("/students/add")) {
			parsedUri.put("data", "add");

		} else if (route.startsWith("/students/edit/")) {
			List<String> routeList = Arrays.asList(route.split("/"));
			int length = routeList.size();

			if (length != 4) {
				parsedUri.put("data", "redirect");
				parsedUri.put("action", "Incorrect URL!");
			} else {
				String value = routeList.get(3);
				if (Student.rows.keySet().contains(value)) {
					parsedUri.put("data", "edit");
					parsedUri.put("action", value);
				} else {
					parsedUri.put("data", "redirect");
					parsedUri.put("action", "Incorrect ID!");
				}
			}

		} else if (route.startsWith("/students/delete/")) {
			List<String> routeList = Arrays.asList(route.split("/"));
			int length = routeList.size();

			if (length != 4) {
				parsedUri.put("data", "redirect");
				parsedUri.put("action", "Incorrect URL!");
			} else {
				String value = routeList.get(3);
				if (Student.rows.keySet().contains(value)) {
					parsedUri.put("data", "delete");
					parsedUri.put("action", value);
				} else {
					parsedUri.put("data", "redirect");
					parsedUri.put("action", "Incorrect ID!");
				}
			}
		}
		return parsedUri;
	}

}
