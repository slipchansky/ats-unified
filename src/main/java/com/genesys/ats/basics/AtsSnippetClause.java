package com.genesys.ats.basics;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.genesys.ats.basics.Explain.VarExplain;
import com.genesyslab.ats.unified.scenario.SnippetAliasAction;
import com.genesyslab.ats.unified.scenario.RestAction;
import com.genesyslab.ats.unified.scenario.RestConfigureAction;
import com.genesyslab.ats.unified.starter.AtsLogConsole;

import lombok.Getter;

public class AtsSnippetClause extends AtsClause {
	
	private final static Map<String, AtsSnippetClause> SNIPPETS = new LinkedHashMap<String, AtsSnippetClause> () {{
		put ("nop", new AtsSnippetClause ("nop"));
	}};
	
	List<String> aliases = new ArrayList<> (); 
	
	@Getter
	private String comment;

	public AtsSnippetClause(String name) {
		super(name);
	}
	
	@Override
	public void enclose() {
		save();
		activeClause = owner; // (snippet does not process directly)
	}

	protected void save() {
		save(this.name);
		for (String alias : aliases) {
			save (alias);
		}
	}

	protected void save(String name) {
		SNIPPETS.put(this.featurePrefix + ":" + name, this);
		SNIPPETS.put(name, this);
	}
	
	@Override
	public void run() {
		if (currentIsOpenSpace()) {
			AtsClause.root().render("");
			AtsClause.root().render("start snippet '"+name+"'\t\t"+featureName+" :: "+scenarioName+")");
			AtsClause.root().indent ();
		}
		
		for (AtsAction a : activities) {
			a.owner = AtsClause.activeClause;
			
			
			if (! (a instanceof AtsProtectedClause) )
				a.context = this.context;
			a.process();
		}
		
		if (currentIsOpenSpace()) {
			AtsClause.root().unindent ();
			AtsClause.root().render("end snippet '"+name+"'\n");
		}	
		
		
		
	}
	
	public static void run (String snippetName) {
		AtsSnippetClause snippet = SNIPPETS.get (snippetName);
		if (snippet == null) {
			throw  new Error ("There is no snippet'"+snippetName+"'");
		}
		snippet.run();
	}

	public static void touch(String snippetName, AtsClause owner) {
		AtsSnippetClause snippet = SNIPPETS.get (snippetName);
		if (snippet == null) {
			throw  new Error ("There is no snippet'"+snippetName+"'");
		}
		snippet.context = owner;
		//snippet.owner = owner;
		snippet.save("last");
		snippet.run ();
	}
	
	@Override
	public void touch () {
		// stas. clarify this solution
		if (isOpen(owner)) {
			run ();
		} else {
			owner.add(this);
		}
	}

	public static List<Explain> explainAll() {
		Set<Entry<String, AtsSnippetClause>> entries = SNIPPETS.entrySet();
		
		
		List<Explain> explains = new ArrayList<Explain>();
		try {
		for (Entry<String, AtsSnippetClause> snippetEntry : entries) {
			if (snippetEntry.getKey().equals("last")) continue;
			Explain explain = new Explain (snippetEntry.getKey(), snippetEntry.getValue().getComment());
			snippetEntry.getValue().collectExplain(explain);
			explains.add(explain);
		}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		int k = 0;
		k++;
		
		AtsLogConsole.enableOutput (true);
		for (Explain e : explains) {
			AtsLogConsole.log("==================================================================");
			AtsLogConsole.log(e.getSnippetName());
			if (e.getSnippetComment()!=null){
				AtsLogConsole.log ("\n"+e.getSnippetComment());
			}
			AtsLogConsole.log("------------------------------------------------------------------");
			
			if (!e.getConsumes().isEmpty()) AtsLogConsole.log("Consumes: "+e.getConsumes());
			if (!e.getDefs().isEmpty()) AtsLogConsole.log("Defs: "+e.getDefs());
			if (!e.getModifies().isEmpty()) AtsLogConsole.log("Modifies: "+e.getModifies());
			if (!e.getRemembers().isEmpty())AtsLogConsole.log("Remembers: "+e.getRemembers());
			if (!e.getProduces().isEmpty())AtsLogConsole.log("Produces: "+e.getProduces());
			AtsLogConsole.log("\n");
		}
		
		return explains;
		
	}

	public AtsSnippetClause setComment(String comment) {
		this.comment = comment;
		return this;
	}
	
	void add(AtsAction activity) {
		if (activity instanceof SnippetAliasAction) {
			if (!this.aliases.contains(activity.name))
				this.aliases.add(activity.name);
		}
		else super.add(activity);
			
	}
	
	
	

}
