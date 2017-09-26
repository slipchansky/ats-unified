package com.genesyslab.ats.unified.scenario;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.genesys.ats.basics.AtsAction;
import com.genesys.ats.basics.AtsClause;
import com.genesys.ats.basics.Explain;
import com.genesyslab.ats.unified.environment.AtsEnvironment;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

public class ForEachClause extends IterationClause {
	private String dataPath;
	protected String itemName = "item";
	

	public ForEachClause(String dataPath, String itemName) {
		super (dataPath);
		this.dataPath = dataPath;
		this.itemName = itemName;
	}


	@Override
	protected void scenario() throws Exception {
		Object clarified = clarify(name);
		if (clarified instanceof Collection) {
			iterateList((List)clarified);
		} else if (clarified instanceof Map) {
			iterateList(((Map)clarified).entrySet());
		}
		AtsClause.root().render("end");

	}

	protected void iterateList(Collection data) throws Exception {
		for (Object context : data) {
			if (this.breakMe) break;
			else {
				put (itemName, context);
				runChildren();
			}
		}
	}


	@Override
	public String toString() {
		return "for each " + itemName + " in " + dataPath;
	}
	
	@Override
	public void collectExplain (Explain explain) {
		explain.consumes (name);
		explain.produces(itemName);
		super.collectExplain(explain);
	}
	

}
