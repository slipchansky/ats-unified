package com.genesys.ats.basics;

import java.io.PrintStream;
import java.util.LinkedHashMap;
import java.util.Map;

import com.genesys.ats.basics.Explain.VarExplain;

public class AtsProtectedClause extends AtsClause {
	
	Map<Object, Object> savedLocal;
	
	public AtsProtectedClause() {
		this ("$");
	}

	public AtsProtectedClause(String name) {
		super(name);
	}
	
	
	
	@Override
	protected void scenario() throws Exception {
		savedLocal = localMemory;
		LinkedHashMap protectedMemory = OBJECT_MAPPER.convertValue(globalMemory, LinkedHashMap.class);
		protectedMemory.putAll(OBJECT_MAPPER.convertValue(localMemory, LinkedHashMap.class));
		
		try {
			localMemory = protectedMemory;
			if (context instanceof AtsSnippetClause) {
				put ("protected."+context.name, true);
			}
			runChildren();
		} finally {
			localMemory = savedLocal;
			protectedMemory.remove("$");
			protectedMemory.remove("theveryroot");
			put(name, protectedMemory);
			savedLocal = null;
			AtsClause.root().render("end protected");
		}
	}
	
	@Override
	public void draw(PrintStream out) {
		out.println("start protected");
	}
	
	@Override
	public void collectExplain(Explain explain) {
		Explain inner = new Explain (explain);
		super.collectExplain(inner);
		
		if (explain.getSnippetName().equals("all documents")) {
			int k = 0;
			k++;
		}
		
		for (VarExplain vx : inner.getProduces ()) {
			if (vx.getName().startsWith("$.")) continue;
			String v = "$."+vx.getName().substring(1);
			if (!v.equals("$."))
				explain.produces (v);
		}
		
		for (VarExplain vx : inner.getConsumes()) {
			if (vx.getName().startsWith("$.")) continue;
			explain.consumes (vx.getName());
		}
		
		for (VarExplain vx : inner.getDefs()) {
			if (vx.getName().startsWith("$.")) continue;
			String n = "$."+vx.getName().substring(1);
			explain.def (n, vx.getDef());
		}
		
		for (VarExplain vx : inner.getRemembers()) {
			explain.remember (vx.getName().substring(1));
		}
	}
	
}
