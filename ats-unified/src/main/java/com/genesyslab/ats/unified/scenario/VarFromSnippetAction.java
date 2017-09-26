package com.genesyslab.ats.unified.scenario;

import java.util.Map;

import com.genesys.ats.basics.Explain;
import com.genesys.ats.basics.AtsSnippetClause;

public class VarFromSnippetAction extends MemoriesAction {

	private String snippet;
	public VarFromSnippetAction(String name, String value, String snippet) {
		super(name);
		setValueName(value);
		this.snippet = clear (snippet);
	}
	
	private static String clear(String name) {
		name = name.trim ();
		if (name.startsWith("\"")) name = name.substring(1);
		if (name.endsWith("\"")) name = name.substring(0, name.length()-1);
		return name;
	}

	@Override
	protected void scenario() throws Exception {
		AtsSnippetClause.run(snippet);
		
		Boolean isprotected = false;
		Object veryroot = this.context.get("theveryroot");
		if ( veryroot instanceof Map) {
			isprotected = null != ((Map)veryroot).get ("protected."+snippet);
		}
		
		Object data = null;
		if (isprotected ) {
			if (value == null || value.length()==0) {
				data = veryroot;
			} else {
				data = extractReferencedValue ("$."+value, copy);
			}
		} else {
			if (value == null || value.length()==0) {
				throw new Error ("You cannot ask for all context variables from snippet '"+snippet+"' with no protected section. Use begin - end construction inside your snippet '"+snippet+"'");
			}
			data = extractReferencedValue ("$"+value, copy);
		}
		setObject(data);
	}
	
	@Override
	public void collectExplain (Explain explain) {
		explain.produces (name);
	}
	

}
