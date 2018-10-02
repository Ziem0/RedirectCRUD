package com.ziemo.redirect.model;

import lombok.Getter;

import java.util.*;

@Getter
public class Student {

	public static Map<String, List<String>> rows = new HashMap<>();

	private int id;
	private String name;
	private String lastName;
	private int age;


	public Student(String name, String lastName, int age) {
		this.id = idParser();
		this.name = name;
		this.lastName = lastName;
		this.age = age;
		addRecord(name, lastName, age);
	}

	private void addRecord(String name, String lastName, int age) {
		List<String> values = new LinkedList<>();
		values.add(name);
		values.add(lastName);
		values.add(String.valueOf(age));
		rows.put(String.valueOf(id), values);
	}

	public static void removeRecordBy(String id) {
		rows.remove(id);
	}

	private int idParser() {

		if (rows.isEmpty()) {
			return 1;
		}

		for (int i = 1; i <= rows.keySet().size() + 1; i++) {
			if (!rows.containsKey(String.valueOf(i))) {
				return i;
			}
		}
		return 1;
	}

	public static void edit(String id, List<String> newData) {
		rows.put(id, newData);
	}

}
