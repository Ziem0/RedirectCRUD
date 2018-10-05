package com.ziemo.redirect.helper;

import com.sun.net.httpserver.HttpExchange;
import com.ziemo.redirect.model.Student;

import java.net.URI;
import java.util.*;

public class URIParser {

	public static Map<String, String> readRoute(HttpExchange exchange) {

		URI uri = exchange.getRequestURI();
		String route = uri.getPath();
		String query = uri.getQuery();

		return parseURI(route, query);
	}

	private static Map<String, String> parseURI(String route, String query) {

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
			handleAmendData(query, route, parsedUri, "edit");    // here we can apply query -> edit?KeyValues
		} else if (route.startsWith("/students/delete/")) {
			handleAmendData(query, route, parsedUri, "delete");
		} else {
			parsedUri.put("data", "redirect");
		}
		return parsedUri;
	}

	private static void handleAmendData(String query ,String route, Map<String, String> parsedUri, String action) {

		List<String> routeList = Arrays.asList(route.split("/"));
		int length = routeList.size();

		if (query != null && action.equalsIgnoreCase("delete")) {
			parsedUri.put("data", action);
			parsedUri.put("action", query.split("=")[1]);  //get data from query URI by Get method

		}else if (length != 4) {
			parsedUri.put("data", "redirect");
			parsedUri.put("action", "Incorrect URL!");

		} else {
			String value = routeList.get(3);

			if (Student.rows.keySet().contains(value)) {
				parsedUri.put("data", action);
				parsedUri.put("action", value);

			} else {
				parsedUri.put("data", "redirect");
				parsedUri.put("action", "Incorrect ID!");
			}
		}
	}

}
