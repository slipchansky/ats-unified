package com.genesyslab.ats.unified.adapter;

import com.genesys.ats.basics.AtsClause;
import com.genesyslab.ats.unified.scenario.DirectShellAction;
import com.genesyslab.ats.unified.scenario.ShellRunScenario;

//TODO later
//import com.genesyslab.ats.unified.scenario.ShellRunScenario;

import cucumber.api.java.en.And;

public class ShellRunAdapter  {
	
	@And("^prepare SHELL$")
	public void prepareShell () {
		 new ShellRunScenario ().touch();;
	}
	
	@And("^shell \"([^\"]*)\"$")
	public void runShell (String cmd) {
		 new DirectShellAction (cmd).touch();;
	}
	
	
	@And("^shell (\\$[\\w|\\d|\\.|\\[|\\]|\\'|\\(|\\)|\\@|\\^|\\$|\\-|\\/|\\:]*)$")
	public void runShell1 (String cmd) {
		 new DirectShellAction (cmd).touch();;
	}
	

	@And("^shell (\\$[\\w|\\d|\\.|\\[|\\]|\\'|\\(|\\)|\\@|\\^|\\$|\\-|\\/|\\:]*) at \"([^\"]*)\"$")
	public void runShell (String cmd, String dir) {
		 new DirectShellAction (dir, cmd).touch();;
	}

	@And("^shell (\\$[\\w|\\d|\\.|\\[|\\]|\\'|\\(|\\)|\\@|\\^|\\$|\\-|\\/|\\:]*) at (\\$[\\w|\\d|\\.|\\[|\\]|\\'|\\(|\\)|\\@|\\^|\\$|\\-|\\/|\\:]*)$")
	public void runShell1 (String cmd, String dir) {
		 new DirectShellAction (dir, cmd).touch();;
	}
	

	@And("^shell \"([^\"]*)\" at (\\$[\\w|\\d|\\.|\\[|\\]|\\'|\\(|\\)|\\@|\\^|\\$|\\-|\\/|\\:]*)$")
	public void runShell2 (String cmd, String dir) {
		 new DirectShellAction (dir, cmd).touch();
	}
	
	@And("^shell \"([^\"]*)\" at \"([^\"]*)\"$")
	public void runShell3 (String cmd, String dir) {
		 new DirectShellAction (dir, cmd).touch();
	}
	
	
	
	
	@And("^current directory \"([^\"]*)\"$")
	public void setDirectory (String directory){
		((ShellRunScenario)AtsClause.activeClause).directory (directory);
	}
	
	@And("^command (.*)$")
	public void setCommand (String command){
		((ShellRunScenario)AtsClause.activeClause).command (command);
	}
	
	@And("^run$")
	public void run () throws Exception{
		AtsClause.close();
	}

}
