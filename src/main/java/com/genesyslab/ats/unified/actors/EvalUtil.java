package com.genesyslab.ats.unified.actors;

import java.io.IOException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.HashMap;
import java.util.Map;

import com.genesys.ats.basics.AtsClause;

import groovy.lang.Binding;
import groovy.lang.GroovyCodeSource;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import groovy.security.GroovyCodeSourcePermission;
import groovy.text.SimpleTemplateEngine;
import groovy.text.Template;

public class EvalUtil {
	private final static Map<String, Script> SCRIPTS = new HashMap<>();
	private final static Map<String, Template> TEMPLATES = new HashMap<>();
	
	
	
	public static Object eval (String src, Map context) {
		final String source = "groovyresult=" + src;
		Script script = getScript(source, context);
		script.run();
		return script.getBinding().getVariable("groovyresult");
	}
	
	
	private static synchronized Script getScript(final String src, Map context) {
		Binding binding = new Binding(context);
		Script script = SCRIPTS.get (src);
		if (script != null) {
			script.setBinding(new Binding(context));
			return script;
		}
		return newScript (src, context);
	}
	
	private static synchronized Script newScript(final String src, Map context) {
		
		Script script = SCRIPTS.get (src);
		if (script != null) {
			return script;
		}
		
		Binding binding = new Binding(context);
		GroovyShell gs = new GroovyShell(binding);
		SecurityManager sm = System.getSecurityManager();
		if (sm != null) {
		    sm.checkPermission(new GroovyCodeSourcePermission(GroovyShell.DEFAULT_CODE_BASE));
		}
		GroovyCodeSource gcs = AccessController.doPrivileged(new PrivilegedAction<GroovyCodeSource>() {
		    public GroovyCodeSource run() {
		        return new GroovyCodeSource(src, "script.txt", GroovyShell.DEFAULT_CODE_BASE);
		    }
		});
		
		script = gs.parse(gcs);
		script.setBinding(binding);
		SCRIPTS.put(src, script);
		return script;
	}


	public static void modify(String name, Object value) {
		Map<Object, Object> context = AtsClause.getContextMap ();//getContextMap(null);
		context.put ("neValueForAssigning", value);
		String source = name+"=neValueForAssigning";
		Script script = getScript(source, context);
		script.run();
//		Object eval = eval ("x", context);
//		int k = 0;
//		k++;
	}


	public static void add(String name, Object value) {
		Map<Object, Object> context = AtsClause.getContextMap ();
		context.put ("neValueForAssigning", value);
		String source = name+".add(neValueForAssigning)";
		Script script = getScript(source, context);
		script.run();
	}


	public static void removeall(String name) {
		Map<Object, Object> context = AtsClause.getContextMap ();
		String source = name+".clear()";
		Script script = getScript(source, context);
		script.run();
	}


	public static void remove(String name, Object v) {
		Map<Object, Object> context = AtsClause.getContextMap ();
		context.put ("neValueForRemoving", v);
		String source = name+".remove(neValueForRemoving)";
		Script script = getScript(source, context);
		script.run();
	}

	public static String translateTemplate(String text, Map<Object, Object> contextAccessor) {
		try {
			Template template = getTemplate(text);
			return template.make(contextAccessor).toString();
		} catch (Throwable e) {
			return text;
		}
	}


	protected static Template getTemplate(String text) throws ClassNotFoundException, IOException {
		Template t = TEMPLATES.get (text);
		if (t != null) return t;
		//if (text.length() > 256) return createTemplateInstance (text); 
		return createTemplate(text);
	}

	protected static synchronized Template createTemplate(String text) throws ClassNotFoundException, IOException {
		Template t = TEMPLATES.get (text);
		if (t != null) return t;
		Template template = createTemplateInstance(text);
		TEMPLATES.put(text, template);
		return template;
	}


	protected static Template createTemplateInstance(String text) throws ClassNotFoundException, IOException {
		SimpleTemplateEngine simpleTemplateEngine = new SimpleTemplateEngine();
		simpleTemplateEngine.setVerbose(false);
		Template template = simpleTemplateEngine.createTemplate(text);
		return template;
	}
	
	
	
}
