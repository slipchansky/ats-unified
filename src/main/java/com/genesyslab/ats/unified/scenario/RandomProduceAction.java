package com.genesyslab.ats.unified.scenario;

import java.security.SecureRandom;

import com.genesys.ats.basics.AtsAction;
import com.genesys.ats.basics.Explain;

public class RandomProduceAction extends AtsAction {
	private SecureRandom random;
	private String sfrom;
	private String sto;

	public RandomProduceAction (String name, String from, String to) {
		super (name);
		this.random = new SecureRandom ();
		this.sfrom = from;
		this.sto = to;
	}
	
	public RandomProduceAction (String from, String to) {
		this ("$", from, to);
	}
	
	
	@Override
	protected void scenario() throws Exception {
		int from = toInt (sfrom);
		int to = toInt (sto);
		int a = Math.min(from, to);
		int b = Math.max(from, to);
		int x = 0;
		if (a!=b) {
			int range = b - a;
			x = random.nextInt(range);
		}
		int v = a+x;
		setObject (v);
	}
	

	@Override
	public String toString() {
		return "random "+name+" ("+sfrom+" ... "+sto+")";
	}
	
	@Override
	public void collectExplain(Explain explain) {
		explain.produces(name);
		explain.consumes(sfrom);
		explain.consumes(sto);
	}
}
