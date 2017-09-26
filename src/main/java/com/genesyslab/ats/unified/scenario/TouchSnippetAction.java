package com.genesyslab.ats.unified.scenario;

import java.io.PrintStream;

import com.genesys.ats.basics.AtsAction;
import com.genesys.ats.basics.AtsSnippetClause;

public class TouchSnippetAction extends AtsAction {
	
	
	public TouchSnippetAction(String name) {
		super(name);
	}

	@Override
	protected void scenario() throws Exception {
		String name = clarify (this.name);
		AtsSnippetClause.touch(name, owner);
	}
	
	@Override
	public void draw(PrintStream out) {
		out.println("call snippet '"+name+"'");
	}

}
