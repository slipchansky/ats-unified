package com.genesyslab.ats.unified.scenario;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Set;

import org.apache.log4j.lf5.util.StreamUtils;
import org.junit.Assert;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.genesys.ats.basics.AtsAction;
import com.genesys.ats.basics.AtsClause;
import com.genesys.ats.basics.Explain;
import com.genesyslab.ats.unified.actors.DataActor;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import jodd.io.StreamUtil;

public class LoadResourceAction extends AtsAction {
	private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper (); 
	
	private String path;
	private DocumentContext responseContext;
	private boolean resourceIsFile;

	public LoadResourceAction (String path, String name) {
		super (name);
		this.path = path;
	}
	

	@Override
	protected void scenario() throws Exception {
		try (InputStream in = getResourceStream()) {
			if (in == null) {
				fail("Resource not found '"+path+"'");
			}
			byte[] bytes = StreamUtils.getBytes(in);
			String src = new String (bytes);
			Object v = DataActor.deserialize(src);
			//Object v = OBJECT_MAPPER.readValue(in, Object.class);
			//v = DataActor.unroll(v);
			setObject (v);
		}
		
	}

	protected InputStream getResourceStream() throws Exception {
		String path = clarify(this.path);
		
		if (resourceIsFile)
			return new FileInputStream (path);
		return LoadResourceAction.class.getClassLoader().getResourceAsStream(path);
	}


	@Override
	public String toString() {
		return "load "+name +" from resource " + path;
	}

	public LoadResourceAction file(boolean b) {
		this.resourceIsFile = true;
		return this;
	}
	
	@Override
	public void collectExplain (Explain explain) {
		explain.atRight (path);
		explain.produces (name);
	}
	
	
	
}
