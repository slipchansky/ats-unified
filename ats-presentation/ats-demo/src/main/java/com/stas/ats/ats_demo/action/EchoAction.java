package com.stas.ats.ats_demo.action;

import com.genesys.ats.basics.AtsAction;

public class EchoAction extends AtsAction {
	private String value;
	public EchoAction (String value, String destName) { 
		super (destName); 
		this.value = value;  
	}
	
	@Override
	protected void scenario() throws Exception {
		Object v = clarify (value);
		
		// store value locally under the name of echo
		setObject(v);
		
		// for remember object
		//remember(name, v);
		
		// setObject is the same the put:
		//put(name, v);
	}
}
