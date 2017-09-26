package com.genesyslab.ats.unified.actors;

import java.util.HashMap;
import java.util.Map;

import com.genesys.ats.basics.AtsClause;

public class SmartContextMap extends HashMap<Object, Object> {
	
	private Map parameters;

	public SmartContextMap (Map parameters) {
		this.parameters = parameters;
	}
	
	@Override
	public Object get(Object key) {
		if (parameters != null && parameters.containsKey(key)) {
			return parameters.get(key);
		} else if (super.containsKey(key)) {
			return super.get(key);
		}
		Object value = null;
		value = AtsClause.activeClause.get(key);
		if (value != null)
			return value;

		value = AtsClause.recall(key);
		if (value != null)
			return value;

		value = DataActor.getEnv(String.valueOf(key));
		if (value != null)
			return value;
		return '$' + String.valueOf(key);
	}
}