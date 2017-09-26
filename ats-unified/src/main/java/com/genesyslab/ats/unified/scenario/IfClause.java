package com.genesyslab.ats.unified.scenario;

import java.io.PrintStream;
import java.util.List;

import com.genesys.ats.basics.AtsAction;
import com.genesys.ats.basics.AtsClause;
import com.genesys.ats.basics.Explain;

public class IfClause extends AtsClause {
	private Conditional conditional;
	private int elseIndex = -1;

	public IfClause(Conditional conditional) {
		super("if-then-else");
		this.conditional = conditional;
	}

	@Override
	protected void scenario() throws Exception {
		boolean isTrue = conditional.isTrue();
		List<AtsAction> steps = super.activities;
		
		
		AtsClause.root().indent ();
		
		boolean moreunindent = false;
		if (!isTrue) {
			if (elseIndex >= 0) {
				AtsClause.root().render ("else");
				AtsClause.root().indent ();
				moreunindent = true;
			}
		} else {
				AtsClause.root().render ("then");
				AtsClause.root().indent ();
				moreunindent = true;
		}
		
		for (int i = 0; i < steps.size(); i++) {
			AtsAction s = steps.get(i);
			if (isTrue) {
				if (i < elseIndex || elseIndex < 0) {
					//AtsClause.root().draw(s);
					s.run();
				}
			} else {
				if (i >= elseIndex && elseIndex >= 0) {
					//AtsClause.root().draw(s);
					s.run();
				}
			}
		}
		AtsClause.root().unindent ();
		if (moreunindent) AtsClause.root().unindent ();
		AtsClause.root().render ("endif");
	}

	public void thenElse() {
		elseIndex = super.activities.size();
	}

	@Override
	public String toString() {
		return "if (" + conditional + ")";
	}
	
	@Override
	public void collectExplain(Explain explain) {
		conditional.collectExplain(explain);
		super.collectExplain(explain);
	}
	
	@Override
	public void draw(PrintStream out) {
		out.println("if " +this.conditional + " ? : "+conditional.isTrue());
	}


}
