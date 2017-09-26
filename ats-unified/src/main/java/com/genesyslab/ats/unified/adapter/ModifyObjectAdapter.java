package com.genesyslab.ats.unified.adapter;

import com.genesys.ats.basics.AtsClause;
import com.genesyslab.ats.unified.scenario.ModifyObjectClause;

import cucumber.api.java.en.And;

public class ModifyObjectAdapter {
	
	
	@And ("^modify (\\$[\\w|\\d|\\.|\\[|\\]|\\'|\\(|\\)|\\@|\\^|\\$]*) begin$")
	public void startObjectModifications (String objectId) throws Exception {
		new ModifyObjectClause (objectId).touch ();
	}
	
	@And ("^update (\\w*) = (\\$[\\w|\\d|\\.|\\[|\\]|\\'|\\(|\\)|\\@|\\^|\\$]*)$")
	public void modificationUpdate (String name, String value) throws Exception {
		((ModifyObjectClause)AtsClause.activeClause).update(name, value);
	}
	
	@And ("^update (\\$[\\w|\\d|\\.|\\[|\\]|\\'|\\(|\\)|\\@|\\^|\\$]*) = (\\$[\\w|\\d|\\.|\\[|\\]|\\'|\\(|\\)|\\@|\\^|\\$]*)$")
	public void modificationUpdateA (String name, String value) throws Exception {
		((ModifyObjectClause)AtsClause.activeClause).update(name, value);
	}
	
	
	@And ("^update ([\\w|\\.|\\'|\\[|\\]\\d]*) = \"([^\"]*)\"$")
	public void modificationUpdate1 (String name, String value) throws Exception {
		((ModifyObjectClause)AtsClause.activeClause).update(name, value);
	}
	
	@And ("^update ([\\w|\\.|\\'|\\[|\\]\\d]*) = (\\d*)$")
	public void modificationUpdate2 (String name, String value) throws Exception {
		((ModifyObjectClause)AtsClause.activeClause).update(name, value);
	}
	
	@And ("^update ([\\w|\\.|\\'|\\[|\\]\\d]*):$")
	public void modificationUpdate3 (String name, String value) throws Exception {
		((ModifyObjectClause)AtsClause.activeClause).updateAsJson(name, value);
	}
	
	@And ("^add \"([^\"]*)\"$")
	public void modificationAdd (String value) throws Exception {
		((ModifyObjectClause)AtsClause.activeClause).update("add", value);
	}
	
	@And ("^add:$")
	public void modificationAddJson (String value) throws Exception {
		((ModifyObjectClause)AtsClause.activeClause).updateAsJson("add", value);
	}
	
	
	@And ("^add (\\$[\\w|\\d|\\.|\\[|\\]|\\'|\\(|\\)|\\@|\\^|\\$]*)$")
	public void modificationAdd1 (String value) throws Exception {
		((ModifyObjectClause)AtsClause.activeClause).update("add", value);
	}
	
	@And ("^add (\\d*)$")
	public void modificationAdd2 (String value) throws Exception {
		((ModifyObjectClause)AtsClause.activeClause).update("add", value);
	}
	
	@And ("^remove (\\$[\\w|\\d|\\.|\\[|\\]|\\'|\\(|\\)|\\@|\\^|\\$]*)$")
	public void modificationRemove (String value) throws Exception {
		((ModifyObjectClause)AtsClause.activeClause).update("remove", value);
	}
	
	@And ("^remove \"([^\"]*)\"$")
	public void modificationRemove1 (String value) throws Exception {
		((ModifyObjectClause)AtsClause.activeClause).update("remove", value);
	}
	
	@And ("^remove (\\d*)$")
	public void modificationRemove2 (String value) throws Exception {
		((ModifyObjectClause)AtsClause.activeClause).update("remove", value);
	}
}
