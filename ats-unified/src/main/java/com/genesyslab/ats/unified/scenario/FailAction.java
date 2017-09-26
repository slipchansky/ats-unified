package com.genesyslab.ats.unified.scenario;

import org.testng.Assert;

import com.genesys.ats.basics.AtsAction;

public class FailAction extends AtsAction {
	
	boolean abort;
	
	public FailAction(String name) {
		super(name);
	}

	@Override
	protected void scenario() throws Exception {
		new PrintAction(name).setTitle("Fail").scenario();
		if (abort) {
			System.exit(0);
		} else {
			fail(name);
		}
	}
	
	public FailAction abort () {
		abort = true;
		return this;
	}
	
	@Override
	public String toString() {
	    return "fail "+name;
	} 

	
	

}
