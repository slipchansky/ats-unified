package com.genesyslab.ats.unified.actors;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.lang3.ClassUtils;
import org.apache.log4j.lf5.util.StreamUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.genesys.ats.basics.AtsClause;
import com.genesyslab.gks.ats.config.GksAtsConfig;

import groovy.lang.MissingPropertyException;
import groovy.text.SimpleTemplateEngine;
import groovy.text.Template;
import jersey.repackaged.com.google.common.collect.ImmutableMap;
import lombok.AllArgsConstructor;

public class DataActor {
	private static final String JSON_PREFIX = "$json@";
	private static final String RESOURCE_JSON_PREFIX = "$resource:json@";
	protected final static ObjectMapper OBJECT_MAPPER = new ObjectMapper() {
		{
			configure(SerializationFeature.INDENT_OUTPUT, true);
		}
	};

	public static Object eval(String value) {
		return eval(value, false);
	}

	public static boolean isPrimitive(Object o) {
		if (o==null) return true;
		boolean isPrimitiveOrWrapped = o.getClass().isPrimitive() || ClassUtils.wrapperToPrimitive(o.getClass()) != null
				|| o instanceof String;
		return isPrimitiveOrWrapped;

	}

	
	private final static Pattern SIMPLE_V1 = Pattern.compile("^\\$[\\w|\\d|_|\\.|\\-|\\[|\\]|\\(|\\)|\\'|\\@|\\^|\\$|\"]*$");
	private final static Pattern SIMPLE_V2 = Pattern
			.compile("^^\\$\\{[\\w|\\d|_|\\-|\\.|\\[|\\]|\\(|\\)|\\'|\\@|\\^|\"]*\\}$");
	
	private static boolean isDirectExpression(String val) {
		
		boolean found = SIMPLE_V1.matcher(val).matches();
		if (!found)
			found = SIMPLE_V2.matcher(val).matches();

		return found;
	}

	public static Object eval(String path, boolean copy) {
		if (path == null)
			return null;
		
		if (path.indexOf('$') < 0) return path;
		
		if (path.equals("$"))
			path = "$theveryroot";
		else if (path.startsWith("$.")) {
			path = "$theveryroot" + path.substring(1);
		}

		if (path.indexOf('$', 1) > 0 && path.indexOf('$') == 0) {
			path = '$' + translateInContext(path.substring(1));
		}

		if (path.startsWith(RESOURCE_JSON_PREFIX)) {
			Object obj = loadResourceAsJson(path.substring(RESOURCE_JSON_PREFIX.length()));
			return obj;
		}
		
		if (path.startsWith(JSON_PREFIX)) {
			Object obj = loadResourceAsJson(path.substring(JSON_PREFIX.length()));
			return obj;
		}
		
		

		if (!isDirectExpression(path))
			return translateInContext(path);

		if (path.startsWith("$"))
			path = path.substring(1);
		Map<Object, Object> context = AtsClause.getContextMap();// getContextMap(null);
		Object  found = null;

		try {
			found = EvalUtil.eval(path, context);
		}  catch (MissingPropertyException e) {
			found = getEnv(path);
		} catch (Throwable e1) {
			throw (e1);
		}
		if (found == null)
			return null;

		if (isPrimitive(found))
			return found;

		if (copy) {
			return OBJECT_MAPPER.convertValue(found, Object.class);
		}

		return found;
	}

	public static Object loadResourceAsJson(String resourceName) {
		InputStream in = DataActor.class.getClassLoader().getResourceAsStream(resourceName);
		try {
			byte[] bytes = StreamUtils.getBytes(in);
			String src = new String (bytes);
			Object deserialized = deserialize (src);
			return deserialized;
			//return OBJECT_MAPPER.readValue(in, Object.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String doTranslate(String text, final Map parameters) {
		if (text == null)
			return null;

		if (text.indexOf('$') < 0)
			return text;

		Map<Object, Object> contextAccessor = getContextMap(parameters);
		return EvalUtil.translateTemplate(text, contextAccessor);
	}


	public static String getEnv(String name) {
		if (name.length() == 0)
			return null;
		String v = System.getProperty(name);
		if (v == null)
			v = System.getenv(name);
		if (v == null) {
			if (GksAtsConfig.INSTANCE.envProperties!= null)
				v = GksAtsConfig.INSTANCE.envProperties.getProperty(name);
		}
		return v;
	}

	protected static Map<Object, Object> getContextMap(final Map parameters) {
		return new SmartContextMap (parameters);
	}

	public static String translateInContext(String text) {
		return doTranslate(text, null);
	}

	public static String evalOrGetOriginal(String formAuthPassword) {
		String result = translateInContext(formAuthPassword);
		return result;
	}

	private static int counter;

	protected static synchronized String generateScriptName() {
		return "Script" + (++counter) + ".groovy";
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object unroll (Object o) {
		if (o instanceof String) {
			o = eval ((String)o, false);
			if (isPrimitive(o)) return o;
		}
		
		if (o instanceof Map) {
			Map m = (Map)o;
			Set<Entry> entries = m.entrySet();
			Map replacements = new HashMap ();
			for (Entry e : entries) {
				Object x = unroll (e.getValue());
				Object key = e.getKey();
				if (key instanceof String) {
					String k = (String)eval ((String)key, false);
					if (!key.equals(k))  {
						replacements.put ((String)key, k);
					}
				}
				 
				if (x != e.getValue()) {
					m.put(key, x);
				}
			}
			entries = replacements.entrySet();
			for (Entry e : entries) {
				Object x = m.get(e.getKey());
				m.remove(e.getKey());
				m.put (e.getValue(), x);
			}
			
		} else if (o instanceof List) {
			List l = (List)o;
			for (int i = 0; i<l.size(); i++) {
				Object e = l.get(i);
				Object x = unroll (e);
				if (x != e)
					l.set(i, x);
			}
		}
		return o;
	}

	public static Object deserialize(Object data) throws JsonParseException, JsonMappingException, IOException {
		if (!(data instanceof String)) return data;
		String value = pretranslate ((String)data);
		data = OBJECT_MAPPER.readValue(value, Object.class);
		if (value.indexOf('$') > 0)
			data = unroll (data);
		
		return data;
	}

	private static String pretranslate(String value) {
		boolean revert = false;
		if (value.indexOf("\"$")>0){
			value = value.replaceAll("\"\\$", "\"\\^");
			revert = true;
		}
		
		if (value.indexOf('$') >= 0) {
			Map<Object, Object> context = new SmartContextMap (null) {
				@Override
				public Object get(Object key) {
					Object value = super.get(key);
					if (value != null)
					if (!isPrimitive (value)) {
						try {
							value = OBJECT_MAPPER.writeValueAsString(value);
						} catch (JsonProcessingException e) {
						}
					} else {
						if (value instanceof String)
							value =  '"'+(String)value+'"';
					}
					
					return value;
				}
			};
			value = EvalUtil.translateTemplate(value, context);
		}
		
		if (revert) {
			//value = value.replaceAll("\"\\^", "\"\\$");
			value = value.replaceAll("\\^", "\\$");
		}
		return value;
	}
	
	
	private static Map<Object, Object> getContextMap() {
		return getContextMap (ImmutableMap.of());
	}

}
