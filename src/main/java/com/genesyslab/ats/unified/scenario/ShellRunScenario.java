package com.genesyslab.ats.unified.scenario;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.genesys.ats.basics.AtsAction;
import com.genesys.ats.basics.AtsClause;

public class ShellRunScenario extends AtsClause  {
	private String directory;
	private String command;
	private String outputText;
	private int exitCode;

	public ShellRunScenario() {
		super("$");
	}



	public ShellRunScenario directory(String directory) {
		this.directory = directory;
		return this;
		
	}

	public ShellRunScenario command(String command) {
		this.command = command;
		return this;
	}

	@Override
	protected void scenario() throws Exception {
		Runtime runtime = Runtime.getRuntime();
		Process p;
		String actualCommand = translate(command);  
		
		if (directory != null) {
		   p = runtime.exec(actualCommand, null, new File (directory));
		} else {
		   p = runtime.exec(actualCommand);
		}
	    p.waitFor();
	    this.exitCode = p.exitValue();
	    
	    List<String> output = new ArrayList<String> ();
	    outputText = "";
		extractOutput(p.getInputStream(), output);
		extractOutput(p.getErrorStream(), output);
	    Map<String, Object> ctx = new HashMap<> ();
	    
	    ctx.put("exitCode", exitCode);
	    ctx.put("output", output);
	    setObject(ctx);
	}

	protected void extractOutput(InputStream inputStream, List<String> output) throws IOException {
		BufferedReader reader =
		         new BufferedReader(new InputStreamReader(inputStream));
	    
	    
	     		
	    String line;
	    
	    while ((line = reader.readLine())!= null) {
	    	output.add(line);
	    	outputText+=(line+"\n");
	    }
	}
	

	@Override
	public String toString() {
		return "shell@> "+ command;
	}

//	@Override
//	public void dump(PrintWriter out) throws Exception {
//		if (directory != null) {
//			out.println("shell@> cd "+directory);
//		}
//		out.println("shell@>"+command);
//		if (context == null) {
//			out.println ("\nnot executed yet");
//		} else {
//			out.println ("\nexitCode="+exitCode);
//			out.println ( "\nconsole output\n"+outputText );
//		}
//	}	

}
