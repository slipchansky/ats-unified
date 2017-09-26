package com.genesyslab.ats.unified.scenario;

import com.genesys.ats.basics.AtsAction;

public class DirectShellAction  extends AtsAction {

	private String command;
	private String directory;

	public DirectShellAction (String command) {
		super ();
		this.command = command;
	}
	public DirectShellAction (String dir, String command) {
		this (command);
		this.directory = dir;
	} 
	
	@Override
	protected void scenario() throws Exception {
		String cmd = (String)clarify (command);
		ShellRunScenario shellRun = new ShellRunScenario ();
		if (this.directory != null) {
			String dir = (String)clarify (directory);
			shellRun.directory(dir);
		}
		shellRun.command(cmd);
		shellRun.enclose();
	}
}
