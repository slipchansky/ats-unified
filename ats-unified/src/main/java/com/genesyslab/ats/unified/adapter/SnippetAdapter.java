package com.genesyslab.ats.unified.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.genesys.ats.basics.AtsClause;
import com.genesys.ats.basics.AtsSnippetClause;
import com.genesyslab.ats.unified.environment.AtsEnvironment;
//import com.genesyslab.ats.unified.scenario.NewSnippetScenario;
import com.genesyslab.ats.unified.scenario.PerformSnippetClause;
import com.genesyslab.ats.unified.scenario.SnippetAliasAction;
import com.genesyslab.ats.unified.scenario.TouchSnippetAction;

import cucumber.api.java.en.And;
import cucumber.api.java.en.But;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

public class SnippetAdapter {
	
	@Given("^new snippet$")
	public void newSnippet () throws Throwable {
		new AtsSnippetClause("last");
//		AtsEnvironment outer = AtsEnvironment.getActual();
//		outer.using (new NewSnippetScenario ());
	}
	
	@Given("^as snippet$")
	public void newSnippet1 () throws Throwable {
		new AtsSnippetClause("last");
//		AtsEnvironment outer = AtsEnvironment.getActual();
//		outer.using (new NewSnippetScenario ());
	}
	
	
	
	@And("^new snippet \"([^\"]*)\"$")
	public void newNamedSnippet (String name) throws Throwable {
		new AtsSnippetClause(name).touch();
	}
	
	@And("^new snippet \"([^\"]*)\":$")
	public void newNamedSnippetWithComment (String name, String comment) throws Throwable {
		new AtsSnippetClause(name).setComment (comment).touch();
	}
	
	@And("^alias \"([^\"]*)\"$")
	public void snippetAlias (String name) throws Throwable {
		new SnippetAliasAction(name).touch();
	}
	
	@And("^new snippet \\@(.*$)")
	public void newNamedSnippet1 (String name) throws Throwable {
//		newNamedSnippet(name);
	}
	
	@Then("^save snippet as @(.*$)")
	public void saveSnippetAs (String snippetName) throws Throwable {
//		NewSnippetScenario latest = latest (NewSnippetScenario.class);
//		latest.setSnippetName (snippetName);
//		latest.run();
	}
	
	@Then("^save snippet$")
	public void saveSnippet () throws Throwable {
		if (! (AtsClause.activeClause instanceof AtsSnippetClause) ) {
			throw new Error ("You are not in snippet clause");
		}
		AtsSnippetClause snippet = (AtsSnippetClause)AtsClause.activeClause;
		snippet.enclose();
	}	
	
	
	@And("^(.*)\\.$")
	public void subst (String snippetName) throws Throwable {
		//Snippet.touch(snippetName);
		new TouchSnippetAction (snippetName).touch ();
	}
	
	@And("^(.*)\\;$")
	public void subst1 (String snippetName) throws Throwable {
		subst (snippetName);
	}
	
	@And("^@(.*$)")
	public void subst2 (String snippetName) throws Throwable {
		subst (snippetName);
	}

	@And("^perform (.*) using$")
	public void performSnippetUsing (String snippetName) throws Throwable {
		//Snippet.run(snippetName);
		new PerformSnippetClause (snippetName).touch ();
	}
	
	@And("^perform (.*) with$")
	public void performSnippetWith (String snippetName) throws Throwable {
		//Snippet.run(snippetName);
		new PerformSnippetClause (snippetName).touch ();
	}
	
	@And("^perform \"([^\"]*)\"$")
	public void performSnippet (String snippetName) throws Throwable {
		//Snippet.run(snippetName);
		new PerformSnippetClause (snippetName).touch ();
	}
	
	
	@And("^go$")
	public void go () throws Exception {
		AtsClause.closeAndGo(null);
	}
	
	@And("^go (\\w*)$")
	public void goNamed (String name) throws Exception {
		AtsClause.closeAndGo(name);
	}
}
