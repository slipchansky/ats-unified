package com.genesyslab.ats.unified.scenario;

import com.genesys.ats.basics.Explain;

public class UndefAction extends MemoriesAction {
	public UndefAction(String name) {
		super(name);
	}
	
	@Override
	protected void scenario() throws Exception {
		setObject(null);
		remember (null);
	}
	
	@Override
	public void collectExplain (Explain explain) {
		explain.modifies (name);
	}
	
}
