package com.genesyslab.ats.unified.scenario;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.testng.Assert;

import com.genesys.ats.basics.AtsAction;

public class CollectionIsEmptyAction extends AtsAction {
	
	private String collection;
	
	private boolean mustBeEmpty;

	public CollectionIsEmptyAction (String c, boolean mustBeEmpty) {
		super ("noname");
		this.collection = c;
		this.mustBeEmpty = mustBeEmpty;
	}

	@Override
	protected void scenario() throws Exception {
		Object c = clarify (collection);
		if (c == null) c = Collections.EMPTY_LIST;
		if (! (c instanceof Collection) && !(c instanceof Map)  ) {
			fail("Tested object is not neither a Collection nor Map");
		}
		boolean empty = false;
		
		if (c instanceof Collection) {
				empty = ((Collection) c).isEmpty();
		} else {
			empty = ((Map) c).isEmpty();
		}
		
		if (mustBeEmpty) {
			if (empty) return;
			fail("Tested container is not empty in spit of expectations, container="+c);
		} else {
			if (!empty) return;
			fail("Tested container is empty in spit of expectations, container="+c);
		}
	}

	@Override
	public String toString() {
		return collection + (mustBeEmpty?"must be empty":"must be not empty");
	}
	
	
}
