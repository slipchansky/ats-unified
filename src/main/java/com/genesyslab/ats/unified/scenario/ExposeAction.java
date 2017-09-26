package com.genesyslab.ats.unified.scenario;

import java.util.HashSet;
import java.util.Set;

import com.genesys.ats.basics.AtsAction;

public class ExposeAction extends AtsAction {
	
	private String fields;

	public ExposeAction (String fields) {
		this.fields = fields;
	}
	
	@Override
	protected void scenario() throws Exception {
		if (fields == null) return;
		String []a = fields.trim ().split(",");
		Set<String> exposed = new HashSet<> ();
		for (String f : a) {
			f = f.trim ();
			exposed.add(f);
		}
		
		Set<String> mustremove = new HashSet <> ();
		for (Object k : localMemory.keySet()) {
			String key = String.valueOf(k);
			if (key.startsWith ("protected.")) continue;
			if (exposed.contains(key)) continue;
			mustremove.add(key);
		}
		
		for (String key : mustremove) {
			localMemory.remove(key);
		}
		
	}

}
