package com.genesyslab.ats.unified.starter;

import java.io.IOException;

import org.testng.Reporter;

import cucumber.runtime.formatter.ColorAware;
import gherkin.formatter.PrettyFormatter;

public class SilentFormatter extends PrettyFormatter implements ColorAware {
	private boolean noAnyOutput;

	public SilentFormatter() {
		super (new Appendable() {
			@Override
			public Appendable append(CharSequence csq) throws IOException {
				return this;
			}

			@Override
			public Appendable append(CharSequence csq, int start, int end) throws IOException {
				return this;
			}

			@Override
			public Appendable append(char c) throws IOException {
				return this;
			}

			public void reset() {
			}

			public void eof() {
			}
		}, true, true);
	}
	
	

	
	public static boolean enableOutput;
	
	public static void enableOutput (boolean enableOutput){
		SilentFormatter.enableOutput = enableOutput;
	}

	public static void log(String str) {
		if (enableOutput)
			org.testng.Reporter.log(str, true);
	}
}
