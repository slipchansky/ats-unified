package com.genesys.ats.basics;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.genesyslab.ats.unified.scenario.RestAction;
import com.genesyslab.ats.unified.scenario.TouchSnippetAction;

public class AtsClause extends AtsAction {
	public static AtsClause activeClause = new AtsOpenClause("root");

	protected List<AtsAction> activities = new ArrayList<AtsAction>();
	
	

	public static void close() {
		if (activeClause != null) {
			activeClause.enclose();
		}
	}

	public AtsClause(String name) {
		super(name);
		activeClause = this;
	}

	@Override
	public void touch() {
	}

	public void enclose() {
		activeClause = owner;
		process();
	}

	void add(AtsAction activity) {
		activities.add(activity);
	}

	protected void runChildren() {
		AtsClause.root().indent();
		try {
			for (AtsAction a : activities) {
					if (a instanceof AtsSnippetClause || a instanceof TouchSnippetAction) {
						a.owner = this;
					}
					a.run();
			}
		} finally {
			AtsClause.root().unindent();
		}
	}

	@Override
	public <T> T get(Object key) {
		if (key.equals("$"))
			key = "theveryroot";
		return (T) this.localMemory.get(key);
	}

	public static Map getContextMap() {
		Map context = new HashMap();
		context.putAll(AtsAction.globalMemory);
		AtsClause clause = activeClause.of(AtsProtectedClause.class);
		if (clause == null)
			clause = activeClause;
		context.putAll(clause.localMemory);
		return context;
	}

	@Override
	protected void scenario() throws Exception {
			runChildren();
			AtsClause.root().render("end");
			super.scenario();
	}
	

	public static void closeAll() {
		if (activeClause == null || (activeClause instanceof AtsOpenClause)) {
			return;
		}
		close();
		closeAll();

	}

	public <T> T of(Class<T> clazz) {
		if (this.getClass().isAssignableFrom(clazz))
			return (T) this;
		if (owner == null)
			return null;
		return owner.of(clazz);
	}

	public void draw(PrintStream out) {
		//out.println(this.getClass().getSimpleName());
		out.println ();
	}

	public static AtsOpenClause root() {
		return activeClause.of(AtsOpenClause.class);
	}

	public static void closeAndGo(String name) {
		AtsClause closed = activeClause;
		if (activeClause != null) {
			activeClause.enclose();
		}
		if (closed instanceof AtsSnippetClause) {
			if (name == null)
				((AtsSnippetClause) closed).save();
			else
				((AtsSnippetClause) closed).save(name);
			((AtsSnippetClause) closed).save("last");
			closed.process();
		}
	}

	public static void reset() {
		activeClause.clear();

	}

	private void clear() {
		globalMemory = new HashMap();
		localMemory = new HashMap();
	}

	@Override
	public void collectExplain(Explain explain) {
		for (AtsAction a : activities)
			a.collectExplain(explain);
	}

}
