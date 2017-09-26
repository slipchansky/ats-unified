package com.genesyslab.gks.ats;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.List;
import static org.junit.Assert.*;

import org.junit.Ignore;
import org.testng.annotations.Test;

import com.genesys.ats.basics.AtsClause;
import com.genesyslab.ats.unified.starter.UnifiedStarter;

import cucumber.api.CucumberOptions;

@CucumberOptions(  
        features = "src/test/resources/tests",
        glue = { 
        		 "com.genesyslab.gks.ats.adapter",
        		 "com.genesyslab.ats.unified.adapter",
				 "com.genesyslab.ats.cfg.adapter" }, 
        plugin = { "pretty", 
        		    "html:target/cucumber", 
        		    "json:target/cucumber.json",
				    "junit:target/events_autotests_report.xml" 
         }, 
        tags = { "~@excluded", "~@required" }
)
public class UnitTests extends UnifiedStarter {
	
	
	
	@Test
	public void nop() {
		
	}
    
    @Test
    public void testSet() {
        runFeatures("set.feature");
        
        Map context = AtsClause.getContextMap();
        Object object = context.get("object");
		assertEquals (LinkedHashMap.class, object.getClass());
		assertEquals (2, ((Map)object).size());
        Object map = context.get("map");
		assertEquals (LinkedHashMap.class, map.getClass());
		assertEquals (1, ((Map)map).size());
        Object array = context.get("array");
		assertEquals (ArrayList.class, array.getClass());
		assertEquals (3, ((List)array).size());
		assertEquals (Integer.class, context.get("num").getClass());
		assertEquals ("value", context.get("reference"));
		assertEquals ("str-value-10", context.get("string"));
		assertNull (context.get("isnull"));
    }
    
    @Test
    public void testSetOutlineExample() {
        runFeatures("set-outline-example.feature");
    }
    

    @Test
    public void testPrint() {
    	runFeatures("print.feature");
    }
    
    @Test
    public void testModify () {
    	runFeatures("modify.feature");
    	
    	Map context = AtsClause.getContextMap();
    	Map object = (Map)context.get("kbCfg");
    	assertEquals("modified_name", object.get("name"));
    	assertEquals("modified_description", object.get("description"));
    	
    	List type = (List)object.get("type");
    	assertEquals(2, type.size());
    	assertTrue(type.contains("ARTICLE"));
    	assertTrue(type.contains("NEWTYPE"));
    	
    	Map test = (Map)object.get("test");
    	assertEquals (1, test.size());
    	assertEquals ("new_value", test.get("field"));
    }
    
    @Test
    public void testSnippet () {
    	runFeatures("snippet.feature");
    	Map context = AtsClause.getContextMap();
    	List a = (List)context.get("a");
    	assertEquals (3, a.size());
    	assertEquals ("1", a.get(0));
    	assertEquals ("2", a.get(1));
    	assertEquals ("3", a.get(2));
    }	
    
    @Test
    public void testForEach () {
    	runFeatures("foreach.feature");
    	Map context = AtsClause.getContextMap();
    	List a = (List)context.get("a");
    	assertEquals (Arrays.asList("a", "1", "2", "b", "1", "2"), a);
    	
    }
    
    @Test
    public void testAssertions () {
    	runFeatures("assertions.feature");
    	Map context = AtsClause.getContextMap();
    	assertEquals ("OK", context.get("ok"));
    }
    
    @Test
    public void testIf () {
    	runFeatures("if.feature");
    	Map context = AtsClause.getContextMap();
    	assertEquals ("OK", context.get("ok"));
    }
    
    
    @Test
    public void testComparison () {
    	// TODO elaborate all cases, not all cases are implemented
    	runFeatures("comparison.feature");
    	Map context = AtsClause.getContextMap();
    	assertEquals ("OK", context.get("ok"));
    }
    
    @Test
    public void testSelect () {
    	// TODO elaborate all cases, not all cases are implemented
    	runFeatures("select.feature");
    	Map context = AtsClause.getContextMap();
    	List<String> result = (List)context.get("distinctCategories"); 
    	assertTrue (result.contains("gbank_category_1"));
    	assertTrue (result.contains("gbank_category_2"));
    	assertEquals (2, result.size());
    }
    
    @Test
    @Ignore
    public void testOne () {
    	runFeatures("onescript.feature");
    }
    
    @Test
    public void testImport() {
        runFeatures("import.feature");
        
        Map context = AtsClause.getContextMap();
        Object a = context.get("a");
		assertEquals (Arrays.asList("a", "b"), a);
    }
    
    
    @Test
    public void testImportImport() {
        runFeatures("import_import.feature");
        Map context = AtsClause.getContextMap();
        Object a = context.get("a");
		assertEquals (Arrays.asList("a", "b", "c", "d"), a);
    }

    @Test
    public void testProtected() {
        runFeatures("protected.feature");
    }
    
    @Test
    public void testProtectedSnippet() {
        runFeatures("protected_snippet.feature");
    }
    
    @Test
    public void testUnroll() {
        runFeatures("unroll.feature");
    }
    
    @Test
    public void testCallSnippetAsFunction () {
    	runFeatures("call-snippet-as-function.feature");
    }
    
    @Test
    public void testDefUndef() {
        runFeatures("def_undef.feature");
    }
    
    @Test
    public void testPerformSnippet() {
        runFeatures("perform-snippet.feature");
    }
    
    @Test
    public void testRandom () {
        runFeatures("random.feature");
    }

    @Test
    public void testRepeat () {
        runFeatures("repeat.feature");
    }
    
    @Test
    public void testShell () {
        runFeatures("shell.feature");
    }
    
    @Test
    public void testIterationBreak () {
        runFeatures("iteration-break.feature");
    }
    
    @Test
    public void testMatch () {
        runFeatures("match.feature");
    }
    
    @Test
    public void testSimilar () {
        runFeatures("similar.feature");
    }
    
    @Test
    public void testDoubleFail () {
    	runFeatures("double-fail.feature");
    }
    
    @Test
    public void testJsonProblem () {
    	runFeatures ("cms-json-problem.feature");
    }
    
    @Test
    public void testUpload () {
    	runFeatures ("upload.feature");
    }
    

}
