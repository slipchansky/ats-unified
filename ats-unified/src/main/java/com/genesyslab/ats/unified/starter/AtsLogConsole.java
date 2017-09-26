package com.genesyslab.ats.unified.starter;


public class AtsLogConsole  {
	public static boolean enableOutput;
	public static void enableOutput (boolean enableOutput){
		AtsLogConsole.enableOutput = enableOutput;
	}

	public static void log(String str) {
		if (enableOutput)
			org.testng.Reporter.log(str, true);
	}

	public static boolean getOutput() {
		return enableOutput;
	}
}
