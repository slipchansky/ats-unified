package com.genesyslab.ats.unified.adapter;

import org.testng.Assert;

import com.genesys.ats.basics.AtsOpenClause;
import com.genesys.ats.basics.AtsProtectedClause;
import com.genesyslab.ats.unified.environment.AtsEnvironment;
import com.genesyslab.ats.unified.scenario.FailAction;
import com.genesyslab.ats.unified.scenario.PrintAction;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

public class NopAdapter {

    @And("^start$")
    public void start() throws Throwable {
    	new AtsOpenClause ("root");
    }
	
    @And("^prepare$")
    public void prepare() throws Throwable {
    	start ();
    }
    
	@Given("^environment$")
	public void environment() throws Throwable {
		start ();
	}
	
	@Given("^new scope$")
	public void newscope() throws Throwable {
		start ();
	}
	
	
    @And("^continue$")
    public void continueScope() throws Throwable {
    }
	
    @And("^same scope$")
    public void continueScope1() throws Throwable {
    }
    
    @And("^same environment$")
    public void continueScope2() throws Throwable {
    }
    
    @And("^fail \"([^\"]*)\"$")
    public void fail (String failMessage) throws Exception {
    	new FailAction (failMessage).touch ();
    	
    } 
    
    @And("^abort \"([^\"]*)\"$")
    public void abort (String abortMessage) throws Exception {
    	new FailAction (abortMessage).abort().touch ();
    }
    
    @And("^abort$")
    public void abort () throws Exception {
    	new FailAction ("Abort").abort().touch ();
    }
    
    
    @And("^begin$")
    public void startProtected () {
    	new AtsProtectedClause().touch();
    }
    
    
    @And("^begin (\\w*)$")
    public void startProtected (String name) {
    	new AtsProtectedClause(name).touch();
    }
}
