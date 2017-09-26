package com.genesyslab.ats.unified.scenario;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.testng.Assert;

import com.genesys.ats.basics.AtsAction;
import com.genesys.ats.basics.Explain;
import com.genesyslab.ats.unified.actors.DataActor;

public class CollectionContainsAction extends AtsAction implements Conditional {
	

	public CollectionContainsAction() {
		super("noname");
	}

	private boolean all = true;
	private boolean inverselogic;
	
	private String collection;
	private String value;
	public CollectionContainsAction all (boolean on) {
		this.all = on;
		return this;
	}

	@Override
	final protected void scenario() throws Exception {
		String message = evaluate();
		if (message != null) 
			fail(message);
	}

	protected String evaluate() {
		Object c = clarify(collection);
		Object v = clarify(value);
		
		if (c instanceof Map) {
			
			if (inverselogic) {
				if ( ((Map)c).containsKey(v) ) return "Map "+c+" does contain value '"+v+"'";
				else
					return null;
			} else {
				
				if ( ((Map)c).containsKey(v) ) return null;
				else
					return "Map "+c+" does not contain value '"+v+"'";
			}
				
				
		} else if (c instanceof Collection) {
			if (v instanceof Map) {
				return testCollctionContainsMap ();
			}
			
			if (v instanceof Collection) {
				return testCollctionContainsCollection ((Collection)c, (Collection)v);
			}

			
			if (inverselogic) {
				if ( collectionContains(c, v) ) return "Collection "+c+" contains value '"+v+"'";
				else
					return null;
			} else {
				if ( collectionContains(c, v) ) return null;
				else
					return "Collection "+c+" does not contain value '"+v+"'";
			}
		} else 
			return "Object "+String.valueOf (collection) + " is not neither Collection nor Map";
	}

	protected boolean collectionContains(Object c, Object v) {
		Collection collection = (Collection)c;
		// simple case
		if (collection.contains(v)) return true;
		
		// collection items and value may be of different types
		if (v==null) {
			for (Object i : collection) {
				if (i==null) return true;
			}
		} else {
			v = String.valueOf(v);
			for (Object i : collection) {
				if (v.equals(String.valueOf(i))) return true;
			}
			
		}
		return false;
	}

	private String testCollctionContainsCollection(Collection c, Collection v) {
		Collection b = new ArrayList (v);
		b.removeAll(c);
		if (all) {
			if (inverselogic){
			  if (b.size()==0)
				  return "Tested collection contains all items of sample collection:\nsample="+c+"\ntested="+v;
			} else {
				  if (b.size()!=0)
					  return "Collection contains items that are not present in sample collection:\nsample="+c+"\ntested="+v;
			}
		} else {
			if (inverselogic) {
				if (b.size() != v.size()) {
					return "Some elements of tested collection are present in sample collection:\nsample="+c+"\ntested="+v;
				}
			} else {
				if (b.size() == v.size()) {
					return "Any item of tested collection is not represented in sample collection:\nsample="+c+"\ntested="+v;
				}
			}
		}
		return null;
	}

	private String testCollctionContainsMap() {
		return "testCollctionContainsMap not implemented yet";
		
	}

	public CollectionContainsAction inverse(boolean inverse) {
		this.inverselogic = inverse;
		return this;
	}

	@Override
	public boolean isTrue() {
		return evaluate()==null;
	}

	@Override
	public String toString() {
		return collection + (inverselogic?" does not contain":" contain") + (all?" all ": " any ");
	}

	public CollectionContainsAction setCollection(String collection) {
		this.collection = collection;
		return this;
	}

	public CollectionContainsAction setValue(String b) {
		this.value = b;
		return this;
	}
	
	@Override
	public void collectExplain (Explain explain) {
		explain.consumes (collection);
		explain.consumes (value);
	}
	
	

}
