import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.lf5.util.StreamUtils;
import org.codehaus.groovy.control.CompilationFailedException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Writable;
import groovy.text.SimpleTemplateEngine;
import groovy.util.Eval;

public class JsonTests {
	
	protected final static ObjectMapper OBJECT_MAPPER = new ObjectMapper ();

	//List<Map> books = with(JSON).get("store.book.findAll { book -> book.price >= 5 && book.price <= 15 }");
	
	
	
	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException, CompilationFailedException, ClassNotFoundException {
		
		
//		HashMap data = OBJECT_MAPPER.readValue(JsonTests.class.getClassLoader().getResourceAsStream("data/v2/response/categories.json"), HashMap.class);
//		String kbId = "groupon";
//		CategoryTreeAgent cta = new CategoryTreeAgent (data, "data", kbId);
//		List<Object> categories = cta.pickListOf("id", true);
//		
//		data = OBJECT_MAPPER.readValue(JsonTests.class.getClassLoader().getResourceAsStream("data/v2/response/browseV1.json"), HashMap.class);
//		List<Object> c1 = new ArrayAgent(data).pickListOf("documents.relatedCategories.id", true);
//		
//		
//		//JsonPath jp = new JsonPath (JsonTests.class.getClassLoader().getResourceAsStream("data/v2/response/browseV1.json"));
//		
//		//SimpleTemplateEngine e  = new SimpleTemplateEngine ();
//		
//		final Map a = new HashMap(){{
//			put ("x", "groupon");
//		}};
//		
//		final Map b = new HashMap(){{
//			put ("a", a);
//		}};
//		
//		
//		String path = new SimpleTemplateEngine().createTemplate("relatedCategories..[?( @.kbId=='groupon' )].id").make(b).toString();
		
		InputStream resourceAsStream = JsonTests.class.getClassLoader().getResourceAsStream("data/v2/response/browseV1.json");
		byte[] bytes = StreamUtils.getBytes(resourceAsStream);
		String json = new String (bytes);
//		HashMap o = OBJECT_MAPPER.readValue(resourceAsStream, HashMap.class);
//		
//		//ReadContext ctx = JsonPath.parse(o);
		Object x = JsonPath.read(json, "documents[?(@.id=='@groupon_202')].categories"); //[*].categories[?(@.kbId='groupon')].id
		List l = (List) x;
		
		int k = 0;
		k++;
		
		
		
		
//		List<Object> x = peakListOf(jp, "documents.id"); // list of document ids
//		List<Object> y = peakListOf(jp, "?.id", true);
		
		
		
		//get("$.documents[*].id");
		
	}






}

