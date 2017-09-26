package com.genesys.ats.basics;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import org.apache.commons.io.FilenameUtils;
import org.testng.Assert;
import org.testng.Reporter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.genesyslab.ats.unified.actors.DataActor;
import com.genesyslab.ats.unified.starter.CucumberContext;
import com.genesyslab.ats.unified.starter.AtsLogConsole;

public class AtsAction {
	protected static Map<Object, Object> globalMemory = new HashMap<>();
	protected static Map<Object, Object> localMemory = new HashMap<>();

	protected final static ObjectMapper OBJECT_MAPPER = new ObjectMapper() {
		{
			configure(SerializationFeature.INDENT_OUTPUT, true);
		}
	};
	protected final static ObjectMapper UNINDENT_OBJECT_MAPPER = new ObjectMapper();

	protected AtsClause owner;
	protected AtsClause context;
	protected String name;

	protected String featureName;
	protected String featurePrefix;
	protected String scenarioName;

	public AtsAction() {
		this("$");
	}

	public AtsAction(String name) {
		this.featureName = CucumberContext.feature();
		buildFeaturePrefix ();
		this.scenarioName = CucumberContext.scenario();
		this.name = name;
		owner = AtsClause.activeClause;
		this.context = owner;
	}

	private void buildFeaturePrefix() {
		featurePrefix = FilenameUtils.removeExtension(new File (featureName).getName());
		
	}

	public void process() {
		if (currentIsOpenSpace())
			this.run();
		else
			owner.add(this);
	}

	protected boolean currentIsOpenSpace() {
		AtsClause clause = AtsClause.activeClause;
		return isOpen(clause);
	}

	protected static boolean isOpen(AtsClause clause) {
		return clause == null || clause instanceof AtsOpenClause;
	}

	public void touch() {
		process();
	}

	public void put(Object key, Object value) {
		if (key == "$")
			key = "theveryroot";
		localMemory.put(key, value);
	}

	public <T> T get(Object key) {
		return (T) localMemory.get(key);
	}

	public static void remember(Object key, Object value) {
		globalMemory.put(key, value);
	}

	public static <T> T recall(Object key) {
		return (T) globalMemory.get(key);
	}

	public void run() {
		try {
			AtsClause.root().draw(this);
			scenario();
		} catch (Throwable e) {
			if (e instanceof AssertionError) {
				throw (AssertionError)e;
			}
			dump (e.getMessage());
			if (e instanceof RuntimeException) throw (RuntimeException)e; 
			if (e instanceof Error) throw (Error)e; 
			throw new RuntimeException (e); 
		}
	}

	protected void log(String str) {
		AtsLogConsole.log(str);
	}

	protected void log(String str, Throwable e) {
		AtsLogConsole.log(str);
		try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
			try (PrintWriter pw = new PrintWriter(bos)) {
				e.printStackTrace(pw);
				pw.flush();
			}
			bos.flush();
			AtsLogConsole.log(new String(bos.toByteArray()));
		} catch (IOException e1) {
			e.printStackTrace();
		}
	}

	static boolean failed = false;
	protected void fail(String message) {
		try {
			dump(message);
		} catch (Exception e) {
			log("Fail", e);
		}
		
		Assert.fail(message);
	}

	protected void dump(String string) {
		AtsClause.root().dump(string);
	}

	// TODO get rid of it. added just for minimizing time for upgrade
	protected void scenario() throws Exception {

	}

	protected void setObject(Object data) {
		put(name, data);
	}

	public <T> T clarify(String valueName) {
		return (T) extractReferencedValue(valueName.trim(), false);
	}

	protected Object extractReferencedValue(String valueName, boolean copy) {
		return DataActor.eval(valueName, copy);
	}

	protected String translate(String text) {
		return DataActor.translateInContext(text);
	}

	public AtsAction setLabel(String label) {
		this.name = label;
		return this;
	}

	public void draw(PrintStream out) {
		out.println(this.toString());
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + "(" + name + ")";
	}

	public void collectExplain(Explain explain) {
	}

	protected int toInt(String s) {
		Object x = clarify(s);
		int v = Integer.parseInt(String.valueOf(x));
		return v;
	}

	protected Object normalizeObject(Object v) {
		if (v == null)
			return v;
		if (DataActor.isPrimitive(v)) {
			if (v instanceof String) {
				String value = (String) v;
				if (!(value.startsWith("{") && value.endsWith("}") || value.startsWith("[") && value.endsWith("]"))) {
					return '"' + normalizeText((String) v) + '"';
				} else {
					try {
						v = UNINDENT_OBJECT_MAPPER.readValue((String) v, Object.class);
					} catch (Throwable e) {
						return toOneRow((String) v);
					}
				}
			} else
				return v;
		}
		try {
			v = UNINDENT_OBJECT_MAPPER.writeValueAsString(v);
		} catch (Throwable e) {
			v = String.valueOf(v);
		}
		return v;
	}

	protected String normalizeText(String value) {
		if (value == null)
			return value;
		if (value.indexOf('\n') < 0)
			return value;
		value = value.trim();
		if (value.startsWith("{") && value.endsWith("}"))
			return "{ json }";
		else if (value.startsWith("[") && value.endsWith("]"))
			return "[ json ]";

		return toOneRow(value);

	}

	private String toOneRow(String value) {
		String result = "", sep = "";
		try (StringReader reader = new StringReader(value)) {
			try (Scanner scanner = new Scanner(reader)) {
				String l;
				while (scanner.hasNext()) {
					l = scanner.nextLine();
					l = l.trim();
					result = result + sep + l;
					sep = " ";
				}
				return result.trim();
			} catch (Throwable e) {
				return result.trim();
			}
		}
	}

}
