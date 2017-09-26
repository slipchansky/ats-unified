package com.genesyslab.ats.unified.scenario;

import com.genesys.ats.basics.AtsAction;
import com.genesyslab.ats.unified.actors.DataActor;
import com.genesyslab.ats.unified.starter.AtsLogConsole;

import org.apache.commons.lang3.ClassUtils;
import org.testng.Reporter;

public class PrintAction extends AtsAction {

	private String title = null;

	public PrintAction(String name) {
		super(name);
	}

	public PrintAction setTitle(String title) {
		this.title = title;
		return this;
	}
	
	@Override
	public void scenario() throws Exception {
		Object value = clarify(name);
		AtsLogConsole.log("\n====================================================================================");
		if (title==null) title = name;
		if (title != null) {
			AtsLogConsole.log(title);
			AtsLogConsole.log("------------------------------------------------------------------------------------");
		}
		if (value == null) {
			AtsLogConsole.log("null");
		} else if (DataActor.isPrimitive (value)) {
			AtsLogConsole.log(String.valueOf(value));
		} else {
			AtsLogConsole.log(OBJECT_MAPPER.writeValueAsString(value));
		}
		AtsLogConsole.log("====================================================================================");
	}
	


	@Override
	public String toString() {
		return "print " + name;
	}
	
}
