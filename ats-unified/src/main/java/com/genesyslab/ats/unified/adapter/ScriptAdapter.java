// is empty, is not empty, contains, shell, includes, copy, remember, pause

package com.genesyslab.ats.unified.adapter;

import com.genesyslab.ats.unified.scenario.CollectionIsEmptyAction;
import com.genesys.ats.basics.AtsAction;
import com.genesys.ats.basics.AtsClause;
import com.genesyslab.ats.unified.scenario.BreakAction;
import com.genesyslab.ats.unified.scenario.CollectionContainsAction;
import com.genesyslab.ats.unified.scenario.ComparisonAction;
import com.genesyslab.ats.unified.scenario.DefAction;
import com.genesyslab.ats.unified.scenario.ExposeAction;
import com.genesyslab.ats.unified.scenario.ForEachClause;
import com.genesyslab.ats.unified.scenario.LoadResourceAction;
import com.genesyslab.ats.unified.scenario.MatchAction;
import com.genesyslab.ats.unified.scenario.PrintAction;
import com.genesyslab.ats.unified.scenario.QuickModifyAction;
import com.genesyslab.ats.unified.scenario.RandomProduceAction;
import com.genesyslab.ats.unified.scenario.RepeatClause;
import com.genesyslab.ats.unified.scenario.MemoriesAction;
import com.genesyslab.ats.unified.scenario.PauseAction;
import com.genesyslab.ats.unified.scenario.StringIncludesStringAction;
import com.genesyslab.ats.unified.scenario.UndefAction;
import com.genesyslab.ats.unified.scenario.VarFromSnippetAction;
import com.genesyslab.ats.unified.scenario.ImportAction;

import cucumber.api.java.en.And;

/**
 * Created by SLypchan on 02.06.2017.
 */
public class ScriptAdapter {

	
	@And("^import \"([^\"]*)\"$")
	public void importFeature(String str) throws Throwable {
		new ImportAction(str).touch();
	}
	
	@And("^print \"([^\"]*)\"$")
	public void print(String str) throws Throwable {
		new PrintAction(str).touch();

	}

	@And("^print (\\$[\\w|\\d|\\.|\\[|\\]|\\'|\\(|\\)|\\@|\\^|\\$]*)$")
	public void print1(String str) throws Throwable {
		PrintAction p = new PrintAction(str);
		p.touch();
	}

	@And("^print (\\$[\\w|\\d|\\.|\\[|\\]|\\'|\\(|\\)|\\@|\\^|\\$]*) title \"([^\"]*)\"$")
	public void print1withTitle(String str, String title) throws Throwable {
		new PrintAction(str).setTitle(title).touch();
	}

	@And("^print \"([^\"]*)\" title \"([^\"]*)\"$")
	public void print1withTitle1(String str, String title) throws Throwable {
		print1withTitle(str, title);
	}

	@And("^end$")
	public void end() throws Throwable {
		AtsClause.activeClause.enclose();
	}


	// @Then("^expected that$")
	// public void expectedThat() throws Throwable {
	// }
	//
	//
	//
	@And("^for each (\\w*) of (\\$[\\w|\\.|\\[|\\]|\\'|\\(|\\)|\\@|\\^|\\$]*) begin$")
	public void forEachNamedItemBegin(String itemName, String data) throws Throwable {
		new ForEachClause(data, itemName);
	}

	@And("^next$")
	public void next() throws Throwable {
		AtsClause.close();
	}
/*
	@And("^\"([^\"]*)\" != \"([^\"]*)\"$")
	public void notEqualsTo(String a, String b) throws Throwable {
		new ComparisonAction.NotEqualsScenario().setA(a).setB(b).touch();
	}

	@And("^\"([^\"]*)\" != (\\$[\\w|\\.|\\[|\\]|\\'|\\(|\\)|\\@|\\^|\\$]*)$")
	public void notEqualsTo2(String a, String b) throws Throwable {
		new ComparisonAction.NotEqualsScenario().setA(a).setB(b).touch();
	}

	@And("^(\\$[\\w|.]*) != (\\$[\\w|\\d|\\.|\\[|\\]|\\'|\\(|\\)|\\@|\\^|\\$]*)$")
	public void notEqualsTo3(String a, String b) throws Throwable {
		new ComparisonAction.NotEqualsScenario().setA(a).setB(b).touch();
	}

	@And("^\"([^\"]*)\" == \"([^\"]*)\"$")
	public void equalsTo(String a, String b) throws Throwable {
		new ComparisonAction.EqualsScenario().setA(a).setB(b).touch();
	}

	@And("^(\\$[\\w|\\d|\\.|\\[|\\]|\\'|\\(|\\)|\\@|\\^|\\$]*) == ([\\d|\\.]*)$")
	public void equalsToNum(String a, String b) throws Throwable {
		new ComparisonAction.EqualsScenario().setA(a).setB(b).touch();
	}

	@And("^([\\d|\\.]*) == (\\$[\\w|\\d|\\.|\\[|\\]|\\'|\\(|\\)|\\@|\\^|\\$]*)$")
	public void equalsToNum1(String a, String b) throws Throwable {
		new ComparisonAction.EqualsScenario().setA(a).setB(b).touch();
	}

	@And("^\"([^\"]*)\" == (\\$[\\w|\\d|\\.|\\[|\\]|\\'|\\(|\\)|\\@|\\^|\\$]*)$")
	public void equalsTo2(String a, String b) throws Throwable {
		new ComparisonAction.EqualsScenario().setA(a).setB(b).touch();
	}

	@And("^(\\$[\\w|\\d|\\.|\\[|\\]|\\'|\\(|\\)|\\@|\\^|\\$]*) == \"([^\"]*)\"$")
	public void equalsTo2a(String a, String b) throws Throwable {
		new ComparisonAction.EqualsScenario().setA(a).setB(b).touch();
	}
	
	@And("^(\\$[\\w|\\d|\\.|\\[|\\]|\\'|\\(|\\)|\\@|\\^|\\$]*) == null$")
	public void equalsToNull(String a) throws Throwable {
		new ComparisonAction.EqualsScenario().setA(a).setB((String)null).touch();
	}
	

	@And("^(\\$[\\w|\\d|\\.|\\[|\\]|\\'|\\(|\\)|\\@|\\^|\\$]*) == (\\$[\\w|\\d|\\.|\\[|\\]|\\'|\\(|\\)|\\@|\\^|\\$]*)$")
	public void equalsTo3(String a, String b) throws Throwable {
		new ComparisonAction.EqualsScenario().setA(a).setB(b).touch();
	}
	
	@And("^(\\$[\\w|\\d|\\.|\\[|\\]|\\'|\\(|\\)|\\@|\\^|\\$]*) ~= \\/(.*)\\/$")
	public void strSimilarToPattern(String a, String b) throws Throwable {
		new ComparisonAction.StringLikeAction().setA(a).setB(b).touch();
	}
	
	@And("^(\\$[\\w|\\d|\\.|\\[|\\]|\\'|\\(|\\)|\\@|\\^|\\$]*) !~ \\/(.*)\\/$")
	public void strNotSimilarToPattern(String a, String b) throws Throwable {
		new ComparisonAction.StringLikeAction().inverse().setA(a).setB(b).touch();
	}
	
	@And("^(\\$[\\w|\\d|\\.|\\[|\\]|\\'|\\(|\\)|\\@|\\^|\\$]*) ~= (\\$[\\w|\\d|\\.|\\[|\\]|\\'|\\(|\\)|\\@|\\^|\\$]*)$")
	public void strSimilarToPatternByRef(String a, String b) throws Throwable {
		new ComparisonAction.StringLikeAction().setPatternRef(b).setA(a).touch();
	}
	
	@And("^(\\$[\\w|\\d|\\.|\\[|\\]|\\'|\\(|\\)|\\@|\\^|\\$]*) !~ (\\$[\\w|\\d|\\.|\\[|\\]|\\'|\\(|\\)|\\@|\\^|\\$]*)$")
	public void strNotSimilarToPatternByRef(String a, String b) throws Throwable {
		new ComparisonAction.StringLikeAction().setPatternRef(b).inverse().setA(a).touch();
	}

	@And("^\"([^\"]*)\" equals to \"([^\"]*)\"$")
	public void equalsTo1(String a, String b) throws Throwable {
		new ComparisonAction.EqualsScenario().setA(a).setB(b).touch();
	}

	@And("^\"([^\"]*)\" (<|\\<\\=|\\>|\\>\\=|==) ([\\d|\\.]*)$")
	public void expressionThanNumber(String a, String operation, Double b) throws Throwable {
		ComparisonAction.numOperation(operation).setA(a).setB(b).touch();
	}

	@And("^([\\d|\\.]*) (<|\\<\\=|\\>|\\>\\=|==) \"([^\"]*)\"$")
	public void numberThenExpression(Double a, String operation, String b) throws Throwable {
		ComparisonAction.numOperation(operation).setA(a).setB(b).touch();
	}
	*/
	
	@And("^([^\\ ]*|\"[^\"]*\") (\\<|\\<\\=|\\>|\\>\\=|\\=\\=|\\!\\=|\\~\\=|\\!\\~) (.*)$")
	public void assertion (String a, String operation, String b) throws Throwable {
		ComparisonAction.scenario(operation).setA(a).setB(b).touch();
	}

	@And("^load resource \"([^\"]*)\" as \"([^\"]*)\"$")
	public void loadResource(String path, String name) throws Throwable {
		execute(new LoadResourceAction(path, name));
	}

	@And("^load resource \"([^\"]*)\" as ([\\w]*)$")
	public void loadResource1(String path, String name) throws Throwable {
		execute(new LoadResourceAction(path, name));
	}

	@And("^load resource (\\$[\\w|\\d|\\.|\\[|\\]|\\'|\\(|\\)|\\@|\\^|\\$]*) as ([\\w]*)$")
	public void loadResource2(String path, String name) throws Throwable {
		execute(new LoadResourceAction(path, name));
	}

	@And("^load file \"([^\"]*)\" as ([\\w]*)$")
	public void loadFile(String path, String name) throws Throwable {
		execute(new LoadResourceAction(path, name).file(true));
	}
	
	protected void collectionsIntersect(String a, String b, boolean all, boolean inverse) throws Exception {
		execute(new CollectionContainsAction().all(false).inverse(inverse).setCollection(a).setValue(b));
	}

	@And("^(\\$[\\w|\\d|\\.|\\[|\\]|\\'|\\(|\\)|\\@|\\^|\\$]*) contains (\\$[\\w|\\d|\\.|\\[|\\]|\\'|\\(|\\)|\\@|\\^|\\$]*)$")
	public void collectionContains(String a, String b) throws Throwable {
		collectionsIntersect(a, b, false, false);
	}

	@And("^(\\$[\\w|\\d|\\.|\\[|\\]|\\'|\\(|\\)|\\@|\\^|\\$]*) contains (\\w*)$")
	public void collectionContains1(String a, String b) throws Throwable {
		collectionsIntersect(a, b, false, false);
	}

	@And("^(\\$[\\w|\\d|\\.|\\[|\\]|\\'|\\(|\\)|\\@|\\^|\\$]*) contains \"([^\"]*)\"$")
	public void collectionContains2(String a, String b) throws Throwable {
		collectionsIntersect(a, b, false, false);
	}

	@And("^(\\$[\\w|\\d|\\.|\\[|\\]|\\'|\\(|\\)|\\@|\\^|\\$]*) does not contain (\\$[\\w|\\d|\\.|\\[|\\]|\\'|\\(|\\)|\\@|\\^|\\$]*)$")
	public void notCollectionContains(String a, String b) throws Throwable {
		collectionsIntersect(a, b, false, true);
	}

	@And("^(\\$[\\w|\\d|\\.|\\[|\\]|\\'|\\(|\\)|\\@|\\^|\\$]*) does not contain (\\w*)$")
	public void notCollectionContains1(String a, String b) throws Throwable {
		notCollectionContains(a, b);
	}

	@And("^(\\$[\\w|\\d|\\.|\\[|\\]|\\'|\\(|\\)|\\@|\\^|\\$]*) does not contain \"([^\"]*)\"$")
	public void notCollectionContains2(String a, String b) throws Throwable {
		notCollectionContains(a, b);
	}

	@And("^(\\$[\\w|\\d|\\.|\\[|\\]|\\'|\\(|\\)|\\@|\\^|\\$]*) contains any of (\\$[\\w|\\d|\\.|\\[|\\]|\\'|\\(|\\)|\\@|\\^|\\$]*)$")
	public void collectionContainsAny(String a, String b) throws Throwable {
		collectionContains(a, b);
	}

	@And("^(\\$[\\w|\\d|\\.|\\[|\\]|\\'|\\(|\\)|\\@|\\^|\\$]*) contains all of (\\$[\\w|\\d|\\.|\\[|\\]|\\'|\\(|\\)|\\@|\\^|\\$]*)$")
	public void collectionContainsAll(String a, String b) throws Throwable {
		collectionsIntersect(a, b, true, false);
	}

	@And("^(\\$[\\w|\\d|\\.|\\[|\\]|\\'|\\(|\\)|\\@|\\^|\\$]*) does not contain any of (\\$[\\w|\\d|\\.|\\[|\\]|\\'|\\(|\\)|\\@|\\^|\\$]*)$")
	public void collectionDoesNotContainAny(String a, String b) throws Throwable {
		collectionsIntersect(a, b, false, true);
	}

	@And("^(\\$[\\w|\\d|\\.|\\[|\\]|\\'|\\(|\\)|\\@|\\^|\\$]*) does not contain all of (\\$[\\w|\\d|\\.|\\[|\\]|\\'|\\(|\\)|\\@|\\^|\\$]*)$")
	public void collectionDoesNotContainAll(String a, String b) throws Throwable {
		collectionsIntersect(a, b, true, true);
	}

	@And("^(\\$[\\w|\\d|\\.|\\[|\\]|\\'|\\(|\\)|\\@|\\^|\\$]*) is empty$")
	public void collectionIsEmpty(String collection) throws Exception {
		execute(new CollectionIsEmptyAction(collection, true));
	}

	@And("^(\\$[\\w|\\d|\\.|\\[|\\]|\\'|\\(|\\)|\\@|\\^|\\$]*) is not empty$")
	public void collectionIsNotEmpty(String collection) throws Exception {
		execute(new CollectionIsEmptyAction(collection, false));
	}

	
	 @And("^pause (\\d*)$")
	 public void pause(final double pause) throws Throwable {
		 new PauseAction(pause).touch();
	 }
	 
	 @And("^pause$")
	 public void pause() throws Throwable {
	 	pause (1000);
	 }
	
	@And("^(\\$[\\w|\\d|\\.|\\[|\\]|\\'|\\(|\\)|\\@|\\^|\\$]*) includes \"([^\"]*)\"$")
	public void stringIncludesString(String a, String b) throws Throwable {
		execute(new StringIncludesStringAction(a, b));
	}

	@And("^(\\$[\\w|\\d|\\.|\\[|\\]|\\'|\\(|\\)|\\@|\\^|\\$]*) does not include \"([^\"]*)\"$")
	public void stringDoesNotInclude(String a, String b) throws Throwable {
		execute(new StringIncludesStringAction(a, b).invert());
	}
	
	@And("^undef (\\w*)$")
	public void undef (String name) {
		new UndefAction(name).touch();
	}


	@And("^def (\\w*) = ([\\d|\\.]*)$")
	public void defNum(String name, String value) throws Throwable {
		new DefAction(name).asNumber().setValueName(value).touch();
	}
	
	@And("^defremember (\\w*) = ([\\d|\\.]*)$")
	public void defRememberNum(String name, String value) throws Throwable {
		new DefAction(name).remember().asNumber().setValueName(value).touch();
	}
	

	@And("^def (\\w*) = (\\$[\\w|\\d|\\.|\\[|\\]|\\'|\\(|\\)|\\@|\\^|\\$|\\-|\\/|\\:]*)$")
	public void defRef(String name, String value) throws Throwable {
		new DefAction(name).asReference().setValueName(value).touch();
	}
	
	@And("^defremember (\\w*) = (\\$[\\w|\\d|\\.|\\[|\\]|\\'|\\(|\\)|\\@|\\^|\\$|\\-|\\/|\\:]*)$")
	public void defRememberRef(String name, String value) throws Throwable {
		new DefAction(name).remember().asReference().setValueName(value).touch();
	}
	

	@And("^def (\\w*):$")
	public void defJson(String name, final String value) throws Throwable {
		new DefAction(name).asJson().setValueName(value).touch();
	}
	
	@And("^defremember (\\w*):$")
	public void defRememberJson(String name, final String value) throws Throwable {
		new DefAction(name).remember().asJson().setValueName(value).touch();
	}
	

	@And("^def (\\w*) = (\\{.*\\})$")
	public void defMap(String name, final String value) throws Throwable {
		new DefAction(name).asJson().setValueName(value).touch();
	}

	@And("^defremember (\\w*) = (\\{.*\\})$")
	public void defRememberMap(String name, final String value) throws Throwable {
		new DefAction(name).remember().asJson().setValueName(value).touch();
	}

	@And("^def (\\w*) = (\\[.*\\])$")
	public void defList(String name, final String value) throws Throwable {
		new DefAction(name).asJson().setValueName(value).touch();
	}
	
	@And("^defremember (\\w*) = (\\[.*\\])$")
	public void defRememberList(String name, final String value) throws Throwable {
		new DefAction(name).remember().asJson().setValueName(value).touch();
	}
	

	@And("^def (\\w*) = \"([^\"]*)\"$")
	public void defStr(String name, String value) throws Throwable {
		new DefAction(name).asString().setValueName(value).touch();
	}
	
	@And("^defremember (\\w*) = \"([^\"]*)\"$")
	public void defRememberStr(String name, String value) throws Throwable {
		new DefAction(name).remember().asString().setValueName(value).touch();
	}
	
	
	 
	@And("^set (\\w*) = ([\\d|\\.]*)$")
	public void setNum(String name, String value) throws Throwable {
		new MemoriesAction(name).asNumber().setValueName(value).touch();
	}
	
	

	@And("^set (\\w*) = (\\$[\\w|\\d|\\.|\\[|\\]|\\'|\\(|\\)|\\@|\\^|\\$|\\-|\\/|\\:]*)$")
	public void setRef(String name, String value) throws Throwable {
		new MemoriesAction(name).asReference().setValueName(value).touch();
	}
	
	
	
//	@And("^set (\\w*) = \\$resource:json@([\\w|\\d|\\.|\\[|\\]|\\'|\\(|\\)|\\@|\\^|\\/|\\$|\\-]*)$")
//	public void setFileAsJson(String name, String value) throws Throwable {
//		new MemoriesScenario(name).asResourceJson().setValueName(value).touch();
//	}
	

	@And("^set (\\w*):$")
	public void setJson(String name, final String value) throws Throwable {
		new MemoriesAction(name).asJson().setValueName(value).touch();
	}

	@And("^set (\\w*) = (\\{.*\\})$")
	public void setMap(String name, final String value) throws Throwable {
		new MemoriesAction(name).asJson().setValueName(value).touch();
	}

	@And("^set (\\w*) = (\\[.*\\])$")
	public void setList(String name, final String value) throws Throwable {
		new MemoriesAction(name).asJson().setValueName(value).touch();
	}

	@And("^set (\\w*) = \"([^\"]*)\"$")
	public void setStr(String name, String value) throws Throwable {
		new MemoriesAction(name).asString().setValueName(value).touch();
	}
	
	@And("^set (\\w*) = true$")
	public void setTrue(String name) throws Throwable {
		new MemoriesAction(name).asString().setValueName("true").touch();
	}
	
	@And("^set (\\w*) = false$")
	public void setFalse(String name) throws Throwable {
		new MemoriesAction(name).asString().setValueName("false").touch();
	}
	
	
	@And("^set (\\w*) = \\$([\\w|\\d|\\.|\\[|\\]|\\'|\\(|\\)|\\^|\\$\\{|\\}|\"]*) from (.*)$")
	public void setFromSnippet(String name, String value, String snippet) throws Throwable {
		new VarFromSnippetAction(name, value, snippet).touch();
	}
	
	@And("^copy (\\w*) = \\$([\\w|\\d|\\.|\\[|\\]|\\'|\\(|\\)|\\^|\\$]*) from (.*)$")
	public void copyFromSnippet(String name, String value, String snippet) throws Throwable {
		new VarFromSnippetAction(name, value, snippet).copy().touch();
	}
	
	@And("^remember (\\w*) = \\$([\\w|\\d|\\.|\\[|\\]|\\'|\\(|\\)|\\^|\\$]*) from (.*)$")
	public void rememberFromSnippet(String name, String value, String snippet) throws Throwable {
		new VarFromSnippetAction(name, value, snippet).remember().touch();
	}
	
	
	@And("^copy (\\w*) = ([\\d|\\.]*)$")
	public void copyNum(String name, String value) throws Throwable {
		new MemoriesAction(name).copy().asNumber().setValueName(value).touch();
	}

	@And("^copy (\\w*) = (\\$[\\w|\\d|\\.|\\[|\\]|\\'|\\(|\\)|\\@|\\^|\\$]*)$")
	public void copyRef(String name, String value) throws Throwable {
		new MemoriesAction(name).copy().asReference().setValueName(value).touch();
	}

	@And("^copy (\\w*):$")
	public void copyJson(String name, final String value) throws Throwable {
		new MemoriesAction(name).copy().asJson().setValueName(value).touch();
	}

	@And("^copy (\\w*) = (\\{.*\\})$")
	public void copyMap(String name, final String value) throws Throwable {
		new MemoriesAction(name).copy().asJson().setValueName(value).touch();
	}
	
	@And("^set \\$([\\w|\\d|\\.|\\[|\\]|\\'|\\(|\\)|\\@|\\^|\\$|\\{|\\}]*) = \"([^\"]*)\"$")
	public void modify(String name, String value) throws Throwable {
		new QuickModifyAction(name, value).touch();
	}
	
	@And("^set \\$([\\w|\\d|\\.|\\[|\\]|\\'|\\(|\\)|\\@|\\^|\\$|\\{|\\}]*) = ([\\d|.]*)$")
	public void modifyNum(String name, String value) throws Throwable {
		new QuickModifyAction(name, value).asNumber().touch();
	}
	
	@And("^set \\$([\\w|\\d|\\.|\\[|\\]|\\'|\\(|\\)|\\@|\\^|\\$|\\{|\\}]*) = (\\[.*\\])$")
	public void modifyJsonArray(String name, String value) throws Throwable {
		new QuickModifyAction(name, value).asJson().touch();
	}
	
	@And("^set \\$([\\w|\\d|\\.|\\[|\\]|\\'|\\(|\\)|\\@|\\^|\\$|\\{|\\}]*) = (\\{.*\\})$")
	public void modifyJsonObject(String name, String value) throws Throwable {
		new QuickModifyAction(name, value).asJson().touch();
	}
	
	
	
	@And("^set \\$([\\w|\\d|\\.|\\[|\\]|\\'|\\(|\\)|\\@|\\^|\\$|\\{|\\}]*) = (\\$[\\w|\\d|\\.|\\[|\\]|\\'|\\(|\\)|\\@|\\^|\\$|\\{|\\}]*)$")
	public void modify1(String name, String value) throws Throwable {
		new QuickModifyAction(name, value).asReference().touch();
	}
	
	@And("^set \\$([\\w|\\d|\\.|\\[|\\]|\\'|\\(|\\)|\\@|\\^|\\$]*) = null$")
	public void modifySetNull(String name) throws Throwable {
		new QuickModifyAction(name, null).touch();
	}
	
	@And("^add \"([^\"]*)\" to \\$([\\w|\\d|\\.|\\[|\\]|\\'|\\(|\\)|\\@|\\^|\\$|\\{|\\}]*)$")
	public void addArrayElement(String value, String name) throws Throwable {
		new QuickModifyAction(name, value).add().touch();
	}
	
	@And("^add ([\\d|\\.]*) to \\$([\\w|\\d|\\.|\\[|\\]|\\'|\\(|\\)|\\@|\\^|\\$|\\{|\\}]*)$")
	public void addArrayNumElement(String value, String name) throws Throwable {
		new QuickModifyAction(name, value).add().asNumber().touch();
	}
	
	@And("^add (\\{.*\\}) to \\$([\\w|\\d|\\.|\\[|\\]|\\'|\\(|\\)|\\@|\\^|\\$|\\{|\\}]*)$")
	public void addArrayObjectElement(String value, String name) throws Throwable {
		new QuickModifyAction(name, value).add().asJson().touch();
	}
	
	@And("^add (\\[.*\\]) to \\$([\\w|\\d|\\.|\\[|\\]|\\'|\\(|\\)|\\@|\\^|\\$|\\{|\\}]*)$")
	public void addArrayArratElement(String value, String name) throws Throwable {
		new QuickModifyAction(name, value).add().asJson().touch();
	}
	
	@And("^add (\\$[\\w|\\d|\\.|\\[|\\]|\\'|\\(|\\)|\\@|\\^|\\$|\\{|\\}]*) to \\$([\\w|\\d|\\.|\\[|\\]|\\'|\\(|\\)|\\@|\\^|\\$|\\{|\\}]*)$")
	public void addArrayElement1(String value, String name) throws Throwable {
		new QuickModifyAction(name, value).add().asReference().touch();
	}
	
	
	@And("^remove \"([^\"]*)\" from \\$([\\w|\\d|\\.|\\[|\\]|\\'|\\(|\\)|\\@|\\^|\\$|\\{|\\}]*)$")
	public void removeStringFromObject (String value, String name) throws Throwable {
		new QuickModifyAction(name, value).remove().touch();
	}
	
	@And("^remove (\\d*) from \\$([\\w|\\d|\\.|\\[|\\]|\\'|\\(|\\)|\\@|\\^|\\$|\\{|\\}]*)$")
	public void removeItemByIndex (String value, String name) throws Throwable {
		new QuickModifyAction(name, value).remove().asNumber().touch();
	}
	

	@And("^copy (\\w*) = (\\[.*\\])$")
	public void copyList(String name, final String value) throws Throwable {
		new MemoriesAction(name).copy().asJson().setValueName(value).touch();
	}
	
	@And("^remove all from \\$([\\w|\\d|\\.|\\[|\\]|\\'|\\(|\\)|\\@|\\^|\\$]*)$")
	public void removeAllFrom (String name) throws Throwable {
		new QuickModifyAction(name).removeAll().touch();
	}
	

	@And("^copy (\\w*) = \"([^\"]*)\"$")
	public void copyStr(String name, String value) throws Throwable {
		new MemoriesAction(name).copy().asString().setValueName(value).touch();
	}
	
	@And("^remember (\\w*) = ([\\d|\\.]*)$")
	public void rememberNum(String name, String value) throws Throwable {
		new MemoriesAction(name).remember().asNumber().setValueName(value).touch();
	}

	@And("^remember (\\w*) = (\\$[\\w|\\d|\\.|\\[|\\]|\\'|\\(|\\)|\\@|\\^|\\$]*)$")
	public void rememberRef(String name, String value) throws Throwable {
		new MemoriesAction(name).remember().asReference().setValueName(value).touch();
	}
	
	@And("^remember (\\$[\\w|\\d|\\.|\\[|\\]|\\'|\\(|\\)|\\@|\\^|\\$]*)$")
	public void rememberRefToItsName(String name) throws Throwable {
		new MemoriesAction(name.substring(1)).remember().asReference().setValueName(name).touch();
	}
	

	@And("^remember (\\w*):$")
	public void rememberJson(String name, final String value) throws Throwable {
		new MemoriesAction(name).remember().asJson().setValueName(value).touch();
	}

	@And("^remember (\\w*) = (\\{.*\\})$")
	public void rememberMap(String name, final String value) throws Throwable {
		new MemoriesAction(name).remember().asJson().setValueName(value).touch();
	}

	@And("^remember (\\w*) = (\\[.*\\])$")
	public void rememberList(String name, final String value) throws Throwable {
		new MemoriesAction(name).remember().asJson().setValueName(value).touch();
	}

	@And("^remember (\\w*) = \"([^\"]*)\"$")
	public void rememberStr(String name, String value) throws Throwable {
		new MemoriesAction(name).remember().asString().setValueName(value).touch();
	}
	
	 @And("^remember (\\w*) = null$")
	 public void rememberNull (String name) throws Throwable {
		 new MemoriesAction(name).remember().setValueName(null).touch();
	 }
	 
	 @And("^set (\\w*) = null$")
	 public void setNull (String name) throws Throwable {
		 new MemoriesAction(name).setValueName(null).touch();
	 }
	 
	 @And("^set (\\w*) = random (\\d*) ... (\\d*)$")
	 public void setRandom (String name, String a, String b) throws Throwable {
		 new RandomProduceAction(name, a, b).touch();
	 }
	 
	 @And("^set (\\w*) = random (\\d*) ... (\\$[\\w|\\d|\\.|\\[|\\]|\\'|\\(|\\)|\\@|\\^|\\$]*)$")
	 public void setRandomBfromRef (String name, String a, String b) throws Throwable {
		 new RandomProduceAction(name, a, b).touch();
	 }
	 
	 @And("^set (\\w*) = random (\\$[\\w|\\d|\\.|\\[|\\]|\\'|\\(|\\)|\\@|\\^|\\$]*) ... (\\d*)$")
	 public void setRandomAfromRef (String name, String a, String b) throws Throwable {
		 new RandomProduceAction(name, a, b).touch();
	 }
	
	 @And("^set (\\w*) = random (\\$[\\w|\\d|\\.|\\[|\\]|\\'|\\(|\\)|\\@|\\^|\\$]*) ... (\\$[\\w|\\d|\\.|\\[|\\]|\\'|\\(|\\)|\\@|\\^|\\$]*)$")
	 public void setRandomBothfromRef (String name, String a, String b) throws Throwable {
		 new RandomProduceAction(name, a, b).touch();
	 }
	 
		@And("^repeat (\\w*) (\\$[\\w|\\.|\\[|\\]|\\'|\\(|\\)|\\@|\\^|\\$]*) times$")
		public void repeatTimesByRef(String itemName, String data) throws Throwable {
			new RepeatClause(data, itemName);
		}
		
		@And("^repeat (\\w*) (\\d*) times$")
		public void repeatTimesByNum(String itemName, String data) throws Throwable {
			new RepeatClause(data, itemName);
		}
	@And("^repeat (\\d*) times$")
	public void repeatAnonymousIteratorTimesByNum(String data) throws Throwable {
		new RepeatClause(data, "i");
	}
	
	@And("^repeat$")
	public void repeatAnonymousEndlessIterator() throws Throwable {
		new RepeatClause();
	}
	
	@And("^break$")
	public void breacCurrentIterator() throws Throwable {
		new BreakAction().touch ();
	}
	
	
	@And("^repeat (\\w*)$")
	public void repeatNamedEndlessIterator(String iteratorName) throws Throwable {
		new RepeatClause(iteratorName);
	}
		
	@And("^repeat (\\$[\\w|\\.|\\[|\\]|\\'|\\(|\\)|\\@|\\^|\\$]*) times$")
	public void repeatAnonymousIteratorTimesByRef(String data) throws Throwable {
		new RepeatClause(data, "i");
	}
	 
	@And("^expose (.*)$")
	public void repeatTimesByNum(String toExpose) throws Throwable {
		new ExposeAction(toExpose).touch();
	}
	
	@And("^comment:$")
	public void addComment (String somment) {
		// not implemented yet. added for commenting scripts
	}

	
	@And("^set (\\w*) = (\\$[\\w|\\.|\\[|\\]|\\'|\\(|\\)|\\@|\\^|\\$]*)\\[\\/(.*)\\/\\]$")
	public void setFromMatch(String name, String value, String expr) throws Throwable {
		new MatchAction(name, expr, value).touch();
	}
	
	@And("^set (\\w*) = (\\$[\\w|\\.|\\[|\\]|\\'|\\(|\\)|\\@|\\^|\\$]*)\\/(.*)\\/$")
	public void setFromMatch1(String name, String value, String expr) throws Throwable {
		new MatchAction(name, expr, value).touch();
	}
	

	private void execute(AtsAction activity) {
		// TODO. replace it lately. created just for minimizing efforts of
		// upgrade.
		activity.touch();

	}

}
