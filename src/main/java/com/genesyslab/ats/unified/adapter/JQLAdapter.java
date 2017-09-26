package com.genesyslab.ats.unified.adapter;

import com.genesys.ats.basics.AtsClause;
import com.genesyslab.ats.unified.scenario.JQLAction;
import com.genesyslab.ats.unified.scenario.JQLClause;

import cucumber.api.java.en.And;

public class JQLAdapter {
	
	protected void select(String expression, String data, boolean distinct) {
		if (expression.startsWith ("\"") && expression.endsWith("\"")) {
			expression = expression.substring(1, expression.length()-1);
		}
		
		JQLClause jss = new JQLClause(expression, data);
		if (distinct) {
			jss.distinct ();
		}
		jss.touch();
	}
	
	@And("^select distinct (.*) from (\\$[\\w]*)$")
	public void selectArrayDistinct (String expression, String data) throws Exception {
		select(expression, data, true);
	}
	
	@And("^select all (.*) from (\\$[\\w|\\.]*)$")
	public void selectArray (String expression, String data) throws Exception {
		select(expression, data, false);
	}
	
	@And("^set ([\\w|\\.]*) = distinct (.*) from (\\$[\\w]*)$")
	public void selectArrayDistinctAndSet (String name,  String expression, String data) throws Exception {
		new JQLAction(name, expression, data).distinct().touch ();
	}
	
	@And("^set ([\\w|\\.]*) = all (.*) from (\\$[\\w]*)$")
	public void selectArrayAllAndSet (String name,  String expression, String data) throws Exception {
		new JQLAction(name, expression, data).touch ();
	}
	
	@And("^retrieve$")
	public void retrieve () throws Exception {
		AtsClause.close();
	}
	
}
