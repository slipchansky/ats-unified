package com.genesyslab.ats.unified.scenario;

import com.genesys.ats.basics.Explain;
import com.genesyslab.ats.unified.scenario.MemoriesAction;

public class DefAction extends MemoriesAction {
	public DefAction(String name) {
		super(name);
	}
	
	@Override
	protected void scenario() throws Exception {
		if (name.equals("kbname")) {
			@SuppressWarnings("unused")
			int k = 0;
		}
		this.copy = false;
		Object v = clarify ('$'+name);
		if (v==null)
			super.scenario();
	
	}
	
	@Override
	public String toString() {
		String action = "def";
		if (toMemory) action = "defremember";
		return toString ("def");
	}
	
	@Override
	public void collectExplain(Explain explain) {
		if (toMemory)
			explain.remember (name, value);
		else
			explain.def(name, value);
	}

}
