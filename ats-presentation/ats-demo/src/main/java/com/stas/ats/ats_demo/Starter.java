package com.stas.ats.ats_demo;



import org.testng.annotations.Test;

import com.genesyslab.ats.unified.starter.UnifiedStarter;

import cucumber.api.CucumberOptions;

@CucumberOptions(  
        features = "src/main/resources/features",
        glue = { 
        		 "com.genesyslab.ats.unified.adapter",
				 "com.genesyslab.ats.cfg.adapter" }, 
        plugin = { "pretty", 
        		    "html:target/cucumber", 
        		    "json:target/cucumber.json",
				    "junit:target/events_autotests_report.xml" 
         }, 
        tags = { "~@excluded", "~@required" }
)
public class Starter extends UnifiedStarter {
	
	
	@Test
	public void _03_testHello () {
		runFeatures("03hello.feature");
	}
	
	@Test
	public void _04_testSubstitutions () {
		runFeatures("04substitution.feature");
	}
	
	@Test
	public void _05_testScope () {
		runFeatures("05scope.feature");
	}
	


	@Test
	public void _06_testRandom () {
		runFeatures("06random.feature");
	}
	
	@Test
	public void _07_testDataFile () {
		runFeatures("07datafile.feature");
	}
	
	@Test
	public void _08_testShell () {
		runFeatures("08shell.feature");
	}
	
	@Test
	public void _09_testRest () {
		runFeatures("09callrest.feature");
	}
	
	@Test
	public void _10_testAllfromDistinctfrom () {
		runFeatures("10allfrom_distinctfrom.feature");
	}

	@Test
	public void _11_testSubstringbyPattern () {
		runFeatures("11substringbypattern.feature");
	}
	
	
	@Test
	public void _12_testDatamanipulations () {
		runFeatures("12datamanipulations.feature");
	}
	
	@Test
	public void _13_testUseCopy () {
		runFeatures("13usecopy.feature");
	}
	
	
	@Test
	public void _14_testAssertions () {
		runFeatures("14assertions.feature");
	}
	
	@Test
	public void _15_testFlowControl () {
		runFeatures("15flowcontrol.feature");
	}
	
	@Test
	public void _16_testForEach () {
		runFeatures("17foreach.feature");
	}

	@Test
	public void _17_testRepeat () {
		runFeatures("16repeat.feature");
	}
	
	@Test
	public void _18_testInterrupting () {
		runFeatures("18interrupting.feature");
	}
	
	@Test
	public void _19_testSnippets () {
		runFeatures("19snippets.feature");
	}
	
	@Test
	public void _20_testImport () {
		runFeatures("20import.feature");
	}

	@Test
	public void _21_testCustomAction () {
		runFeatures("21customaction.feature");
	}

}
