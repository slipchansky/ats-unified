package com.genesyslab.ats.unified.scenario;

import com.genesys.ats.basics.AtsClause;

public class IterationClause extends AtsClause {
	
	private static IterationClause current;
	protected boolean breakMe = false;

	
	public IterationClause(String name) {
		super(name);
	}
	
	@Override
	public void run() {
		breakMe = false;
		IterationClause lastCurrent = current; 
		current = this;
		try {
			super.run();
		} finally {
			current = lastCurrent;
		}
		
	}
	
	
	public static void breakIteration () {
		if (current != null) current.breakIt ();
	}

	private void breakIt() {
		this.breakMe = true; 
	}
	
	
	

}
