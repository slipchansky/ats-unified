package com.genesyslab.ats.unified.scenario;

import java.io.PrintStream;

import com.genesys.ats.basics.AtsClause;
import com.genesys.ats.basics.Explain;

public class RepeatClause extends IterationClause {
	
	
	protected String itemName = "i";
	private   String scount;
	

	public RepeatClause(String scount, String iteratorName) {
		super (scount);
		this.scount = scount;
		this.itemName = iteratorName;
	}
	
	public RepeatClause(String iteratorName) {
		this ("-1", iteratorName);
	}
	
	public RepeatClause() {
		this ("-1", "i");
	}



	@Override
	protected void scenario() throws Exception {
		int times = toInt (name);
		AtsClause.root().indent();
		iterate (times);
		AtsClause.root().unindent();
		AtsClause.root().render("end repeat");

	}

	protected void iterate(int times) throws Exception {
		for (int i=0; (i<times && times >= 0) || (times<0); i++) {
			if (breakMe) break;
			else {
			put (itemName, i+1);
				AtsClause.root().render("iteration "+(i+1));
				
				AtsClause.root().indent();
				runChildren();
				AtsClause.root().unindent();
			}
		}
	}


	@Override
	public String toString() {
		return "repeat " + itemName + " " + scount +" times";
	}
	
	@Override
	public void collectExplain (Explain explain) {
		explain.consumes (name);
		explain.produces(itemName);
		super.collectExplain(explain);
	}
	
	@Override
	public void draw(PrintStream out) {
		out.println(toString ());;
	}
	

}
