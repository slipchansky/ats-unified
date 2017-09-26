package com.genesyslab.ats.unified.scenario;

import com.genesys.ats.basics.AtsAction;

public class PauseAction extends AtsAction {

	long pause;
	
	public PauseAction(Double pause) {
		super("noname");
		this.pause = pause.longValue();
	}
	
	@Override
	protected void scenario() throws Exception {
		Thread.sleep(pause);
	}

	@Override
	public String toString() {
		return "pause " + pause;
	}
	
	

}
