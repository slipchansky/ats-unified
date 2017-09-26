package com.genesyslab.ats.unified.scenario;

import java.util.LinkedHashMap;
import java.util.Map;

import com.genesys.ats.basics.AtsClause;
import com.genesys.ats.basics.AtsProtectedClause;
import com.genesys.ats.basics.AtsSnippetClause;

public class PerformSnippetClause extends AtsProtectedClause {

	private LinkedHashMap snippetOutput;

	public PerformSnippetClause(String name) {
		super(normalize(name));
	}

	protected static String normalize(String name) {
		name = name.trim();
		if (name.startsWith("\""))
			name = name.substring(1);
		if (name.endsWith("\""))
			name = name.substring(0, name.length() - 1);
		name = name.trim();
		return name;
	}

	@Override
	protected void scenario() throws Exception {
		super.scenario();
		this.localMemory.remove(name);
		if (snippetOutput != null) {
			put("$", snippetOutput);
		}

	}

	@Override
	protected void runChildren() {
		super.runChildren();
		AtsClause.root().indent();
		try {
			AtsSnippetClause.run(name);
			Object protectedContent = get("$");
			boolean isProtected = false;
			String key = null;
			if (protectedContent instanceof Map) {
				Map protectedContentMap = (Map) protectedContent;
				key = "protected." + name;
				if (protectedContentMap.get(key) != null) {
					isProtected = true;
				}
			}
			if (isProtected) {
				this.snippetOutput = OBJECT_MAPPER.convertValue((Map) protectedContent, LinkedHashMap.class);
				this.snippetOutput.remove(key);
			} else {
				this.snippetOutput = OBJECT_MAPPER.convertValue(this.localMemory, LinkedHashMap.class);
				this.snippetOutput.remove("theveryroot");
			}
		} finally {
			AtsClause.root().unindent();
		}
	}

}
