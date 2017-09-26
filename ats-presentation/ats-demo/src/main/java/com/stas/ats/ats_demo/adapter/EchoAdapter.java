package com.stas.ats.ats_demo.adapter;

import com.stas.ats.ats_demo.action.EchoAction;

import cucumber.api.java.en.And;

public class EchoAdapter {

	@And ("^echo (\\$[\\w|\\d|\\.|\\[|\\]|\\'|\\(|\\)|\\@|\\^|\\$]*) to (.*)$")
	public void echo (String value, String dstName) {
		new EchoAction(value, dstName).touch();
	}

}
