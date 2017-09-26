package com.genesyslab.ats.unified.starter;

import lombok.Getter;
import lombok.Setter;

public class CucumberContext {
	
	
	private static CucumberContext current = null;
	
	@Getter
	private String feature;
	
	@Getter @Setter
	private String scenario;
	
	private CucumberContext parent = null;
	
	
	public CucumberContext (String feature) {
		this.feature = feature;
		this.parent = current;
		current = this;
	}
	
	public void enclose () {
		current = this.parent;
	}
	
	public static void start (String feature) {
		new CucumberContext (feature);
	}
	
	public static void scenario (String name) {
		if (current != null)
			current.setScenario(name);
	}
	
	public static String scenario () {
		if (current != null)
			return current.getScenario();
		else return "undefined";
	}
	
	public static String feature () {
		if (current != null)
			return current.getFeature();
		else 
			return "undefined";
	}
	
	public static void end () {
		if (current != null) {
			current.enclose();
		}
	}
}
