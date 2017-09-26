package com.genesyslab.ats.unified.scenario;

import java.util.Set;

import com.genesys.ats.basics.AtsAction;
import com.genesys.ats.basics.Explain;

public class StringIncludesStringAction extends AtsAction implements Conditional {
	private String substr;
	private String str;
	String wholeString ;
	String subString;
	private boolean inverse = false;

	public StringIncludesStringAction (String str, String substr) {
		super ("noname");
		this.str = str;
		this.substr = substr;
	}

	@Override
	public boolean isTrue() {
		clarifyAll();
		boolean isTrue = wholeString.indexOf (subString) >= 0;
		if (inverse) return !isTrue;
		return isTrue;
	}

	protected void clarifyAll() {
		wholeString = ""+clarify (str);
		subString = ""+clarify (substr);
	}

	@Override
	protected void scenario() throws Exception {
		if (isTrue ()) return;
		
		String message = "";
		message = compile(str, wholeString);
		message += " does not include ";
		message += compile(substr, subString);		
		fail (message); 
	}

	protected String compile(String name, String value) {
		String message;
		if (!name.equals (value)) {
			message = name + "(\""+value+"\")";
		} else {
			message = '"'+name+'"';
		}
		return message;
	}
	
	@Override
	public String toString() {
		clarifyAll ();
		String a = compile (str, wholeString);
		String b = compile (substr, subString);
		return '('+a + " includes "+b+ ") ? : "+isTrue ();
	}

	public StringIncludesStringAction invert() {
		this.inverse = true;
		return this;
	}
	
	@Override
	public void collectExplain (Explain explain) {
		explain.consumes (substr);
		explain.consumes (str);
	}

}
