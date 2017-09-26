package com.genesyslab.ats.unified.scenario;

import com.genesys.ats.basics.AtsAction;
import com.genesys.ats.basics.AtsClause;

public class BreakAction extends AtsAction {

	@Override
	protected void scenario() throws Exception {
		IterationClause.breakIteration();
	}
	
	@Override
	public String toString() {
		return "break";
	}
	
}
