package com.genesys.ats.basics;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Map;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.testng.Reporter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.genesyslab.ats.unified.starter.AtsLogConsole;

public final class AtsOpenClause extends AtsClause {

	PrintStream out; 
	ByteArrayOutputStream bos = new ByteArrayOutputStream ();
	int indent = 3;
	
	
	public AtsOpenClause(String name) {
		super(name);
		owner = null;
		localMemory.clear();
		out = new PrintStream (bos);
		out.println("  "+featureName);
		out.println("    " + scenarioName);
	}
	

	public void enclose () {
	}
	
	@Override
	public void draw(PrintStream out) {
		out.println(featureName + " :: " + scenarioName);
	}


	public void draw(AtsAction activity) {
		makeIndent ();
		activity.draw(out);
	}
	
	private void makeIndent() {
		for (int i = 0; i< indent; i++) {
			out.print("  ");
		}
		
	}


	public void dump (String title) {
		String effectiveContext;
		Map contextMap = AtsClause.getContextMap();
		try {
			effectiveContext = OBJECT_MAPPER.writeValueAsString(contextMap);
		} catch (JsonProcessingException e) {
			effectiveContext = String.valueOf(contextMap);
		}
		out.flush();
		
		AtsLogConsole.log("***********************************************************************************************************");
		AtsLogConsole.log(title);
		AtsLogConsole.log("***********************************************************************************************************");
		AtsLogConsole.log("\ntrace: ");
		try {
			AtsLogConsole.log (Util.extractStackTrace(bos.toByteArray()));
		} catch (IOException e) {
			AtsLogConsole.log (new String (bos.toByteArray()));
		}
		AtsLogConsole.log("***********************************************************************************************************");
		
		
		
//		AtsLogConsole.log("Context:");
//		AtsLogConsole.log("\neffective:" + effectiveContext);
//		AtsLogConsole.log("\nendofcontext" + effectiveContext);
	}


	public void print(String string) {
		out.println (string);
	}


	public void indent() {
		indent++;
	}
	
	public void unindent() {
		indent--;
	}


	public void render(String string) {
		makeIndent ();
		out.println (string);
	}
	

}
