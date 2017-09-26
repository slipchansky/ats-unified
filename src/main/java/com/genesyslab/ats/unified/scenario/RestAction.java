package com.genesyslab.ats.unified.scenario;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.genesys.ats.basics.AtsClause;
import com.genesys.ats.basics.Explain;
import com.genesyslab.ats.unified.actors.SimpleRestActor;
import com.genesyslab.ats.unified.actors.SimpleRestActor.Method;
import com.genesyslab.gks.ats.config.GksAtsConfig;

import jodd.util.StringUtil;
import lombok.AllArgsConstructor;

public class RestAction extends AtsClause {

	SimpleRestActor restActor;
	public static RestAction actual;
	

	public RestAction() {
		this("$");
	}

	public RestAction(String name) {
		super(name);
	}

	@Override
	protected void scenario() throws Exception {
		RestAction lastActual = actual;
		actual = this;
		try {
			String home = clarify("$restHome");
			if (StringUtil.isEmpty(home))
				home = clarify("$base.url");
			restActor = new SimpleRestActor(home);
			runChildren();

			Map<String, Object> response = restActor.invoke();
			setObject(response);
			
			if (Integer.valueOf(-1).equals (response.get("status"))) {
				AtsClause.root().render("send (error) "+toJson (response));
			}
			else
				AtsClause.root().render("send (success) "+toJson (response));
			
		} finally {
			actual = lastActual;
		}

	}

	private String toJson(Map<String, Object> response) {
		try {
			return UNINDENT_OBJECT_MAPPER.writeValueAsString(response);
		} catch (Throwable e) {
			return String.valueOf (response);
		}
	}

	@Override
	public void draw(PrintStream out) {
		out.println("using REST");
	}

	@Override
	public void collectExplain(Explain explain) {
		explain.produces(name);
		super.collectExplain(explain);
	}

}
