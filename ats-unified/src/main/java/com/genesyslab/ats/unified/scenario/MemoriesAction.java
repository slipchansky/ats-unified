package com.genesyslab.ats.unified.scenario;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.genesys.ats.basics.AtsAction;
import com.genesys.ats.basics.Explain;
import com.genesyslab.ats.unified.actors.DataActor;

public class MemoriesAction extends AtsAction {
	protected enum Treat {
		STR, NUM, REF, JSON, INT
	}
	
	protected String value;
	protected boolean copy = false;
	Treat treatAs = Treat.STR;
	protected boolean toMemory = false;
	
	public MemoriesAction(String name) {
		super (name);
	}
	
	public static MemoriesAction remember (String name) {
		return new MemoriesAction (name).remember();
	}

	@Override
	protected void scenario() throws Exception {
		//Object data = DataActor.eval(valueName, asJson, copy);
		Object data = getData();
		setObject(data);
	}

	protected void setObject(Object data) {
		if (toMemory) {
			remember(name, data);
		} else {	
			super.setObject(data);
		}
	}

	protected Object getData() throws IOException, JsonParseException, JsonMappingException {
		Object data = null;
		if (value != null){
		switch (treatAs) {
			case STR:
				data = translate (value);
				if (((String)data).startsWith("$"))
					data = clarify ((String)data);
				if (data != null) 
					data = String.valueOf(data);
				data = DataActor.unroll (data);
				break;
			case JSON:
				data = DataActor.deserialize (value);
				try {
				Object s = ((List)((Map)data).get ("ssections")).get(2);
				int k = 0;
				k++;
				} catch (Throwable e) {
					
				}
				break;
			case NUM:
				if (value.indexOf('.') >= 0)
					data = Double.parseDouble(value);
				else
					data = Integer.parseInt(value);
				break;
			case REF:
				data = extractReferencedValue (value, copy);
				break;
			}
		}
		return data;
	}


	public AtsAction setValueName(String valueName) {
		this.value = valueName;
		return this;
	}


	@Override
	public String toString() {
		String action = "set";
		if (toMemory) action = "remember";
		if (copy) action = "copy";
		
		return toString(action);
	}

	protected String toString(String action) {
		String text = value;
		Object v = null;
		if (value != null) {
			try {
		      v = clarify (value);
			} catch (Throwable e) {
			  v = null;
			}
		  if (v != null) {
			  text = normalizeText (value);
			  v = normalizeObject (v);
		  }
		} else {
			text = "null";
		}
		
		
		return action +" " + name + " = " + text + "\t\t("+v+")";
	}


	public MemoriesAction copy() {
		copy = true;
		return this;
	}

	public MemoriesAction asNumber() {
		treatAs = Treat.NUM;
		return this;
	}
	
	public MemoriesAction asString() {
		treatAs = Treat.STR;
		return this;
	}

	public MemoriesAction asReference() {
		treatAs = Treat.REF;
		return this;
	}
	
	public MemoriesAction asJson() {
		this.treatAs = Treat.JSON;
		return this;
	}
	
	public MemoriesAction remember () {
		toMemory = true;
		return this;
	}

	public MemoriesAction asInteger() {
		this.treatAs = Treat.INT;
		return this;
	}

	@Override
	public void collectExplain (Explain explain) {
		explain.atRight (value);
		
		if (toMemory) {
			explain.remember(name);
		} else {
			explain.produces (name);
		}
	}
	
	
	
}
