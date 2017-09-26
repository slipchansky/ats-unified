package com.genesyslab.ats.unified.scenario;

import java.io.PrintStream;
import java.util.Set;

import com.genesys.ats.basics.AtsAction;
import com.genesys.ats.basics.Explain;
import com.genesyslab.ats.unified.actors.DataActor;
import com.genesyslab.ats.unified.actors.SimpleRestActor;

public class RestConfigureAction extends AtsAction {

	protected String value;
	private final String type;
	
	protected SimpleRestActor restActor () {
		if (RestAction.actual==null) return null;
		return RestAction.actual.restActor;
	}
	
	protected String clarifyStr (String s) {
		Object clarified = clarify (s);
		if (clarified==null)
			return (String)clarified;
		return String.valueOf(clarified); 
	}
	
	public RestConfigureAction(String name, String value, String type) {
		super(name);
		this.value = value;
		this.type = type;
	}
	
	@Override
	public void collectExplain (Explain explain) {
		explain.consumes (value);
		explain.consumes (name);
	}
		
	
	@Override
	public String toString() {
		Object v = clarify (value);
		v = normalizeObject(v);
		return type + ' '+name+" = "+value+"("+v+")";
	}



	public static class RestParamScenario extends RestConfigureAction {
		public RestParamScenario(String name, String value) {
			super(name, value, "param");
		}
		@Override
		protected void scenario() throws Exception {
			SimpleRestActor actor = restActor();
			if (actor!=null) actor.param(clarifyStr (name), clarifyStr (value));
		}
		
	}
	
	public static class RestHeadScenario extends RestConfigureAction {
		public RestHeadScenario(String name, String value) {
			super(name, value, "head");
		}
		@Override
		protected void scenario() throws Exception {
			SimpleRestActor actor = restActor();
			if (actor!=null) actor.head(clarifyStr (name), clarifyStr (value));
		}
	}
	
	public static class RestBodyScenario extends RestConfigureAction {
		public RestBodyScenario(String value) {
			super("body", value, "body");
		}
		@Override
		protected void scenario() throws Exception {
			SimpleRestActor actor = restActor();
			if (actor!=null) actor.body(clarify (value));
		}
	}
	
	public static class RestAttachmentScenario extends RestConfigureAction {
		public RestAttachmentScenario(String value) {
			super("attachment", value, "attachment");
		}
		@Override
		protected void scenario() throws Exception {
			SimpleRestActor actor = restActor();
			if (actor!=null) actor.attachment(clarify (value));
		}
	}
	
	
	public static class RestURLScenario extends RestConfigureAction {
		public RestURLScenario(String value) {
			super("url", value, "url");
		}
		@Override
		protected void scenario() throws Exception {
			SimpleRestActor actor = restActor();
			if (actor!=null) actor.url(clarifyStr (value));
		}
		
		@Override
		public void draw(PrintStream out) {
			// TODO Auto-generated method stub
			super.draw(out);
		}
	}
	
	
	public static class RestMethodScenario extends RestConfigureAction {
		public RestMethodScenario(String value) {
			super("method", value, "method");
		}
		@Override
		protected void scenario() throws Exception {
			SimpleRestActor actor = restActor();
			if (actor!=null) actor.method((String)clarifyStr (value));
		}
	} 

	public static class RestSSLScenario extends RestConfigureAction {
		public RestSSLScenario(String path, String value) {
			super(path, value, "ssl");
		}
		@Override
		protected void scenario() throws Exception {
			SimpleRestActor actor = restActor();
			if (actor!=null) actor.usingSSL(clarify(name), clarify(value));
		}
		
		@Override
		public String toString() {
			Object v = clarify (value);
			if (!DataActor.isPrimitive(v)) v = "OBJECT";
			return "SSL: " + name+"/"+value+"("+v+")";
		}

		
	}
	
	public static class RestBasicScenario extends RestConfigureAction {
		public RestBasicScenario(String path, String value) {
			super(path, value, "basic auth");
		}
		@Override
		protected void scenario() throws Exception {
			SimpleRestActor actor = restActor();
			if (actor!=null) actor.usingBasicAuth(clarify(name), clarify(value));
		}
		
		@Override
		public String toString() {
			Object v = clarify (value);
			if (!DataActor.isPrimitive(v)) v = "OBJECT";
			return "BASIC AUTH auth: " + name+":"+value+"("+v+")";
		}
		
	}
	
	public static class RestFormAuthScenario extends RestConfigureAction {
		public RestFormAuthScenario(String path, String value) {
			super(path, value, "form auth");
		}
		@Override
		protected void scenario() throws Exception {
			SimpleRestActor actor = restActor();
			if (actor!=null) actor.formAuth(clarify(name), clarify(value));
		}
		@Override
		public String toString() {
			Object v = clarify (value);
			if (!DataActor.isPrimitive(v)) v = "OBJECT";
			return "FORM AUTH auth: " + name+":"+value+"("+v+")";
		}
	}
	
	public static class RestHomeScenario extends RestConfigureAction {
		public RestHomeScenario(String value) {
			super("home", value, "home");
		}
		@Override
		protected void scenario() throws Exception {
			SimpleRestActor actor = restActor();
			if (actor!=null) actor.home(clarify(value));
		}
	}
	
	
}
