package com.genesyslab.ats.unified.scenario;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.genesys.ats.basics.AtsAction;
import com.genesys.ats.basics.Explain;
import com.jayway.jsonpath.JsonPath;

public class JQLAction extends AtsAction {
	
	private String path;
	private String data;
	private boolean distinct = false;
	
	public JQLAction(String name, String path, String data) {
		super(name);
		this.path = path;
		this.data = data;
	}

	public JQLAction distinct() {
		this.distinct = true;
		return this;
	}
	
	
	@Override
	protected void scenario() throws Exception {
		Object dataValue = clarify(data);
		String realPath = (String)clarify(path);
		
		Object v = JsonPath.read (dataValue, realPath); //
		if (! (v instanceof Collection)) {
			fail("Object of type was retrieved by path '"+path+"'. JQLScenario does not support retrieving of data of types that are differ from Collection yet.");
		}
		v = normalize ((Collection)v);
		
		if (distinct && v instanceof Collection) {
			v = makeDistinct ((Collection)v);
		}
		setObject(v);
	}

	private Object normalize(Collection collection) {
		List normalized = new ArrayList();
		for (Object o : collection) {
			if (o instanceof Collection) {
				normalized.addAll((Collection)o);
			} else {
				normalized.add(o);
			}
		}
		return normalized;
	}

	private Collection makeDistinct(Collection v) {
		Set result = new HashSet ();
		result.addAll(v);
		return new ArrayList(result);
	}


	@Override
	public String toString() {
		return "set " +name+ " = "+(distinct?"distinct":"") + " "+path + " from " + data;
	}
	
	@Override
	public void collectExplain (Explain explain) {
		explain.atRight (data);
		explain.produces (name);
	}
	
}
