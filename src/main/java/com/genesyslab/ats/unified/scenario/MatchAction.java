package com.genesyslab.ats.unified.scenario;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class MatchAction extends MemoriesAction {

	private Pattern pattern;

	public MatchAction(String name) {
		super(name);
	}
	
	public MatchAction(String name, String expr, String data) {
		super(name);
		setValueName(data);
		this.pattern = Pattern.compile (expr);
	}
	
	@Override
	protected Object getData() throws IOException, JsonParseException, JsonMappingException {
		Object value = super.getData();
		if (value == null) return null;
		
		String data; 
		if (value instanceof String)
			data = (String) value;
		else
			data = String.valueOf(value);
		
		Matcher matcher = pattern.matcher(data);
		if (!matcher.find()) {
			return null;
		}
		List<String> result = new ArrayList<> ();
		for (int i = 1; i<1000; i++) {
			try {
			 String v = matcher.group(i);
			 	result.add(v);
			} catch (IndexOutOfBoundsException e) {
				break;
			}
		}
		if (result.size()==0) return null;
		if (result.size()==1) return result.get(0);
		return result;
	}
	
	
	
//	public static void main(String[] args) {
//		String s = "Java(TM) SE Runtime Environment (build 1.8.0_65-b17)";
//		Pattern pattern = Pattern.compile("^Java\\(TM\\) SE Runtime Environment \\(build (.*)\\)$");
//		Matcher matcher = pattern.matcher(s);
//		String x = "";
//		if (matcher.find()) {
//			x = matcher.group(0);
//		}
//		int k = 0;
//		k++;
//		
//	}

}
