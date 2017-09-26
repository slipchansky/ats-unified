package com.genesyslab.ats.unified.scenario;

import com.genesys.ats.basics.AtsAction;

public interface Parametrized<T> {
	
	public <T extends AtsAction> T param (String key, String value);

}
