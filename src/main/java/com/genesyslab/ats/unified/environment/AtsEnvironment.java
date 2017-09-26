package com.genesyslab.ats.unified.environment;

import java.util.HashMap;
import java.util.Map;

import com.genesyslab.ats.unified.actors.DataActor;

import groovy.text.SimpleTemplateEngine;
import groovy.text.Template;

public class AtsEnvironment {

	public static String translateInContext(String text, final Map parameters) {
		String result = doTranslate(text, parameters);
		if ("null".equals(result))
			result = text;
		return result;
	}

	protected static String doTranslate(String text, final Map parameters) {
		if (text == null)
			return null;
		if (text.indexOf('$') < 0)
			return text;

		Map<Object, Object> contextAccessor = new HashMap<Object, Object>() {
			@Override
			public Object get(Object key) {
				if (parameters != null && parameters.containsKey(key)) {
					return parameters.get(key);
				} else if (super.containsKey(key)) {
					return super.get(key);
				}
				Object value = null;
				try {
					value = DataActor.eval ('$' + String.valueOf(key));
					return value;
				} catch (Throwable e) {

				}
				if (value != null)
					return value;
				return '$' + String.valueOf(key);
			}
		};
		try {
			SimpleTemplateEngine simpleTemplateEngine = new SimpleTemplateEngine();
			simpleTemplateEngine.setVerbose(false);
			Template template = simpleTemplateEngine.createTemplate(text);
			return template.make(contextAccessor).toString();
		} catch (Throwable e) {

			return text;
		}
	}
	

}
