package com.genesyslab.ats.unified.scenario;

import com.genesys.ats.basics.Explain;

public interface Conditional {
	
	boolean isTrue ();
	void collectExplain(Explain explain);

}
