package com.genesyslab.ats.unified.scenario;

import java.util.HashSet;
import java.util.Set;

import org.testng.Reporter;

import com.genesys.ats.basics.AtsAction;
import com.genesys.ats.basics.AtsClause;
import com.genesyslab.ats.unified.starter.AtsLogConsole;
import com.genesyslab.ats.unified.starter.UnifiedStarter;

public class ImportAction extends AtsAction {
	
	private final static Set<String> ALREADY_IMPORTED = new HashSet<> (); 
	
	public ImportAction(String str) {
		super (str);
	}

	@Override
	protected void scenario() throws Exception {
		if (ALREADY_IMPORTED.contains(name)) return;
		ALREADY_IMPORTED.add(name);
		try {
			AtsLogConsole.log("  Import "+name);
			UnifiedStarter.INSTANCE.runFeaturesSilently(name);
			AtsClause.closeAll();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

}
