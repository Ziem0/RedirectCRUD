package com.ziemo.redirect.view;

import com.ziemo.redirect.model.Student;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

public class WebDisplay {

	public String getRecordsPage(String message) {

		JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/records.twig");
		JtwigModel model = JtwigModel.newModel();

		if (message != null) {
			model.with("message", message);
		}

		if (!Student.rows.isEmpty()) {
			model.with("rows", Student.rows);
		}

		return template.render(model);
	}

	public String getAmendPage() {

		JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/amend.twig");
		JtwigModel model = JtwigModel.newModel();

		return template.render(model);
	}


}
