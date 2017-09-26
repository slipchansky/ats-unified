package com.genesyslab.ats.unified.scenario;


import com.genesys.ats.basics.AtsClause;
import com.genesys.ats.basics.Explain;

// just for backward compatibility
@Deprecated
public class JQLClause extends AtsClause  {
	private String path;
	private String data;
	//ReadContext ctx;
	private boolean distinct = false;
	
	private JQLAction activity;

	@Deprecated
	public JQLClause (String path, String data) {
		super ("$");
		this.path = path;
		this.data = data;
	}

	public JQLClause distinct() {
		this.distinct = true;
		return this;
	}
	
	@Override
	protected void scenario() throws Exception {
		activity = new JQLAction("$", path, data);
		if (distinct)
			activity.distinct ();
		activity.run();
	}

	@Override
	public String toString() {
		return "select "+(distinct?"distinct":"") + " "+path + " from " + data;
	}
	
	@Override
	public void collectExplain (Explain explain) {
		explain.atRight (data);
		explain.produces (name);
	}
	

}
