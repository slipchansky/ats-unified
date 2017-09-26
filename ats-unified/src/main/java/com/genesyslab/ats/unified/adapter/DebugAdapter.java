package com.genesyslab.ats.unified.adapter;

import java.util.Map;

import com.genesys.ats.basics.AtsAction;
import com.genesys.ats.basics.AtsClause;
import com.genesys.ats.basics.AtsSnippetClause;

import cucumber.api.java.en.And;

public class DebugAdapter {
	
	@And("^debug$")
	public void debug() throws Throwable {
		new AtsAction (){
			@SuppressWarnings("unused")
			protected void scenario() throws Exception {
				
				// for quick inspecting:
				Map<Object, Object> global = AtsClause.globalMemory;  
				Map<Object, Object> local = AtsClause.localMemory;
				AtsClause owner = this.owner;
				
				// for breakpoint:
				int toggleBreakPointHere= 0;
				toggleBreakPointHere++;
			};
		}.touch();
	}
	
	@And("^explain$")
	public void explain() throws Throwable {
		AtsSnippetClause.explainAll ();
	}
	
	

}
