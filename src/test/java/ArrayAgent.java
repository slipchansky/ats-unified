import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.restassured.path.json.JsonPath;

public class ArrayAgent {
	protected final static ObjectMapper OBJECT_MAPPER = new ObjectMapper ();
	protected JsonPath jp;
	private Object data;
	
	public ArrayAgent (Object data) {
		setData(data);
	}

	protected void setData(Object data) {
		this.data = data;
		this.jp = toJsonPath (data);
	}
	
	protected JsonPath toJsonPath(Object data) {
		if (data instanceof String) {
			return new JsonPath ((String)data);
		}
		try {
			String json = OBJECT_MAPPER.writeValueAsString(data);
			return new JsonPath (json);
		} catch (JsonProcessingException e) {
			throw new Error (e);
		}
	}

	public List<Object> pickListOf (String path, boolean distinct) {
		Collection<Object> peaked;
		if (distinct) peaked = new HashSet<Object>();
		else peaked = new ArrayList<Object>();
		
		List<Object> found = jp.getList(path);
		for (Object o : found) {
			if (o instanceof List) {
				peaked.addAll((List)o);
			}
			else
				peaked.add(o);
		}
		return new ArrayList<Object> (peaked);
	}
}
