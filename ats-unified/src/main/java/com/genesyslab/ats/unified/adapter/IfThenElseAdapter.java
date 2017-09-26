package com.genesyslab.ats.unified.adapter;

import com.genesys.ats.basics.AtsClause;
import com.genesys.ats.basics.Explain;
import com.genesyslab.ats.unified.actors.DataActor;
import com.genesyslab.ats.unified.scenario.ComparisonAction;
import com.genesyslab.ats.unified.scenario.Conditional;
import com.genesyslab.ats.unified.scenario.IfClause;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

public class IfThenElseAdapter {
	@And("^ifdef (\\$.*) begin$")
	public void ifdef (final String varName) {
		new IfClause( ifdefCondition(varName, true)).touch();
	}
	
	@And("^ifdef (\\$.*)$")
	public void ifdef1 (final String varName) {
		ifdef(varName);
	}
	
	
	@Given("^ifndef (\\$.*)$")
	public void ifndef (final String varName) {
		new IfClause( ifdefCondition(varName, false)).touch();
	}
	
	@And("^ifndef (\\$.*) begin$")
	public void ifndef1 (final String varName) {
		ifndef(varName);
	}
	

	protected Conditional ifdefCondition(final String varName, final boolean forward) {
		return new Conditional () {
			@Override
			public boolean isTrue() {
				try {
					Object v = DataActor.eval(varName);
					if (forward) return v!=null;
					else return v==null;
				} catch (Throwable e) {
					if (forward) return false;
					else return true;
				}
			}

			@Override
			public void collectExplain(Explain explain) {
				explain.atRight(varName);
			}
			
			@Override
			public String toString() {
				return "("+varName +(forward?" is defined":"is not defined")+")";
			} 
			
		};
	}

	@And("^if (.*) *(\\<|\\<\\=|\\>|\\>\\=|==|\\!\\=|\\~\\=|\\!\\~) *(.*)$")
	public void assertion (String a, String operation, String b) throws Throwable {
		new IfClause(ComparisonAction.scenario(operation).setA(a).setB(b)).touch();
	}
	
	
	@Then("^else$")
	public void thenElse () {
		((IfClause)AtsClause.activeClause).thenElse();
	}
	
	@Then("^endif$")
	public void endIf () throws Exception {
		AtsClause.close ();
	}
	
// TODO later	
//	protected void collectionsIntersect(String a, String b, boolean all, boolean inverse) throws Exception {
//		new IfThenElseScenario((CollectionContainsScenario)new CollectionContainsScenario().all(false).inverse (inverse).setCollection(a).setValue(b)).touch();
//	}
//	
//
//    @And("^if (\\$[\\w|\\.|\\[|\\]|\\'|\\(|\\)]*) contains (\\$[\\w|\\.|\\[|\\]|\\'|\\(|\\)]*)$")
//    public void collectionContains(String a, String b) throws Throwable {
//    	collectionsIntersect(a, b, false, false);
//    }
//
//    @And("^if (\\$[\\w|\\.|\\[|\\]|\\'|\\(|\\)]*) contains any of (\\$[\\w|\\.|\\[|\\]|\\'|\\(|\\)]*)$")
//    public void collectionContainsAny(String a, String b) throws Throwable {
//    	collectionContains(a, b);
//    }
//
//    @And("^if (\\$[\\w|\\.|\\[|\\]|\\'|\\(|\\)]*) contains all of (\\$[\\w|\\.|\\[|\\]|\\'|\\(|\\)]*)$")
//    public void collectionContainsAll(String a, String b) throws Throwable {
//    	collectionsIntersect(a, b, true, false);
//    }
//    
//    @And("^if (\\$[\\w|\\.|\\[|\\]|\\'|\\(|\\)||\\d]*) does not contain any of (\\$[\\w|\\.|\\[|\\]|\\'|\\(|\\)]*)$")
//    public void collectionDoesNotContainAny(String a, String b) throws Throwable {
//    	collectionsIntersect(a, b, false, true);
//    }
//    
//    @And("^if (\\$[\\w|\\.|\\[|\\]|\\'|\\(|\\)]*) does not contain all of (\\$[\\w|\\.|\\[|\\]|\\'|\\(|\\)]*)$")
//    public void collectionDoesNotContainAll(String a, String b) throws Throwable {
//    	collectionsIntersect(a, b, true, true);
//    }
//    
//    @And("^if (\\$[\\w|\\.|\\[|\\]|\\'|\\(|\\)]*) contains (\\w*)$")
//    public void collectionContains1(String a, String b) throws Throwable {
//    	collectionsIntersect(a, b, false, false);
//    }
//    @And("^if (\\$[\\w|\\.|\\[|\\]|\\'|\\(|\\)]*) contains \"([^\"]*)\"$")
//    public void collectionContains2(String a, String b) throws Throwable {
//    	collectionsIntersect(a, b, false, false);
//    }
//    
//    @And("^if (\\$[\\w|\\.|\\[|\\]|\\'|\\(|\\)]*) does not contain (\\$[\\w|\\.|\\[|\\]|\\'|\\(|\\)]*)$")
//    public void notCollectionContains(String a, String b) throws Throwable {
//    	collectionsIntersect(a, b, false, true);
//    }
//    
//    @And("^if (\\$[\\w|\\.|\\[|\\]|\\'|\\(|\\)|\\d]*) does not contain (\\d*)$")
//    public void notCollectionContains3(String a, String b) throws Throwable {
//    	collectionsIntersect(a, b, false, true);
//    }
//    
//    
//    @And("^if (\\$[\\w|\\.|\\[|\\]|\\'|\\(|\\)]*) does not contain (\\$[\\w|\\.|\\[|\\]|\\'|\\(|\\)|\\d]*)$")
//    public void notCollectionContains1(String a, String b) throws Throwable {
//    	notCollectionContains(a, b);
//    }
//    @And("^if (\\$[\\w|\\.|\\[|\\]|\\'|\\(|\\)]*) does not contain \"([^\"]*)\"$")
//    public void notCollectionContains2(String a, String b) throws Throwable {
//    	notCollectionContains(a, b);
//    }
	

}
