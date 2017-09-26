package com.genesyslab.ats.unified.scenario;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.testng.Assert;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.genesys.ats.basics.AtsAction;
import com.genesys.ats.basics.Explain;
import com.genesyslab.ats.unified.actors.DataActor;

public abstract class ComparisonAction extends AtsAction  implements Conditional {
	
	public abstract boolean isTrue ();
	public static class EqualsScenario extends ComparisonAction {
		public EqualsScenario() {
			super(" == ");
		}

		@Override
		public boolean isTrue() {
			clarifyAll();
			if (a == null)
				return b == null;
			if (b == null)
				return a == null;
			if (as.equals(CompareAs.NUM)) {
				double da = ((Number) a).doubleValue();
				double db = ((Number) b).doubleValue();
				return (Math.abs(da - db) < 0.00001);
			}

			if (as.equals(CompareAs.OBJ) && a instanceof Collection && b instanceof Collection) {
				return compareCollections((Collection) a, (Collection) b);
			}

			return String.valueOf(a).equals(String.valueOf(b));
		}

		private boolean compareCollections(Collection a, Collection b) {
			if (a.size() != b.size())
				return false;

			Object[] x = a.toArray(new Object[0]);
			Arrays.sort(x);
			Object[] y = b.toArray(new Object[0]);
			Arrays.sort(y);

			boolean equals = Arrays.asList (x).equals(Arrays.asList(y));
			return equals;
		}
	}

	public static class NotEqualsScenario extends EqualsScenario {
		public NotEqualsScenario() {
			this.name = " != ";
		}

		@Override
		public boolean isTrue() {
			return !super.isTrue();
		}
	}

	public static class LessThanScenario extends ComparisonAction {
		public LessThanScenario() {
			super(" < ");
		}

		@Override
		public boolean isTrue() {
			clarifyAll();
			if (a == null)
				return b == null;
			if (b == null)
				return a == null;
			if (as.equals(CompareAs.NUM)) {
				return ((Number) a).doubleValue() < ((Number) b).doubleValue();
			} else if (as.equals(CompareAs.STR)) {
				return ((String) a).compareTo((String) b) < 0;
			} else
				throw new Error("Less than is not applicable to operands of type " + as);

		}
	}

	public static class LessOrEqualsThanScenario extends ComparisonAction {
		public LessOrEqualsThanScenario() {
			super(" <= ");
		}

		@Override
		public boolean isTrue() {
			clarifyAll();
			if (a == null)
				return b == null;
			if (b == null)
				return a == null;
			if (as.equals(CompareAs.NUM)) {
				double da = ((Number) a).doubleValue();
				double db = ((Number) b).doubleValue();
				return (da < db) || (Math.abs(da - db) < 0.00001);
			} else if (as.equals(CompareAs.STR)) {
				return (((String) a).compareTo((String) b) <= 0);
			} else
				throw new Error("Less than is not applicable to operands of type " + as);
		}
	}

	public static class GreatherOrEqualsThanScenario extends ComparisonAction {
		public GreatherOrEqualsThanScenario() {
			super(" >= ");
		}

		@Override
		public boolean isTrue() {
			clarifyAll();
			if (a == null)
				return b == null;
			if (b == null)
				return a == null;
			if (as.equals(CompareAs.NUM)) {
				double da = ((Number) a).doubleValue();
				double db = ((Number) b).doubleValue();
				return (da > db) || (Math.abs(da - db) < 0.00001);
			} else if (as.equals(CompareAs.STR)) {
				return (((String) a).compareTo((String) b) >= 0);
			} else
				throw new Error("Less than is not applicable to operands of type " + as);

		}
	}
	
	public static class StringLikeAction extends ComparisonAction {
		private Pattern pattern;
		protected boolean inverse = false;
		private boolean newPatternEachTime;
		private String lastCompiledPatternExpression;
		
		public StringLikeAction () {
		    super (" ~= ");
		}
		
		public StringLikeAction inverse () {
			inverse = true;
			return this;
		}

		@Override
		public boolean isTrue() {
			a = clarify(aName);
			clarifyPattern ();
			if (a==null) return false;
			String data;
			if ( a instanceof String )
				data = (String)a;
			else
				data = String.valueOf(a);
			if (!inverse)
				return pattern.matcher(data).find();
			else
				return !pattern.matcher(data).find();
		}
		
		private void clarifyPattern() {
			if (!newPatternEachTime) return;
			Object o = clarify (bName);
			if (o==null) {
				throw new Error ("Cant build pattern by unknown variable '"+bName+"'");
			}
			String p = String.valueOf(o);
			if (p.equals(lastCompiledPatternExpression) && pattern!= null) return;
			lastCompiledPatternExpression = p;
			pattern = compilePattern(p);
		}

		public ComparisonAction setB(String b) {
			this.bName = b;
			if (b.startsWith("$")) 
				return setPatternRef (b);
						
			pattern = compilePattern(b);
			return this;
		}

		protected Pattern compilePattern(String b) {
			b = b.trim();
			if (b.startsWith ("/") && b.endsWith ("/")) {
				b = b.substring(0, b.length()-1);
				b = b.substring(1);
			}
			return Pattern.compile(b);
		}
		
		public StringLikeAction setPatternRef (String b) {
			this.bName = b;
			this.newPatternEachTime = true;
			return this;
		}
		
		@Override
		public String toString() {
			return aName +"("+clarify(aName)+") "+(inverse?"!~":"~=")+" "+bName+"("+ clarify (bName) +")"; 
		} 
		
	}
	
	public static class StringNotLikeAction extends StringLikeAction {
		public StringNotLikeAction() {
			super();
			this.inverse = true;
		}
	}

	public static class GratherThanScenario extends ComparisonAction {
		public GratherThanScenario() {
			super(" > ");
		}

		@Override
		public boolean isTrue() {
			clarifyAll();
			if (a == null)
				return b == null;
			if (b == null)
				return a == null;
			if (as.equals(CompareAs.NUM)) {
				return ((Number) a).doubleValue() > ((Number) b).doubleValue();
			} else if (as.equals(CompareAs.STR)) {
				return (((String) a).compareTo((String) b) > 0);
			} else
				throw new Error("Less than is not applicable to operands of type " + as);
		}
	}

	enum CompareAs {
		STR, BOOL, NUM, OBJ
	}

	protected String aName, bName;
	protected Object a;
	protected Object b;
	protected CompareAs as = CompareAs.OBJ;

	private ComparisonAction() {
		this("noname");
	}
	
	
	@Override
	public <T> T clarify(String valueName) {
		if (valueName != null && valueName.equals("null")) return null;
		Object x = super.clarify(valueName.trim());
		if (x instanceof String) {
			
			String s = (String)x;
			if (s.equals("null")) return null;
			
			s = s.trim();
			if (s.startsWith("\"") && s.endsWith("\"")) {
				s = s.substring(1);
				s = s.substring(0, s.length()-1);
				return (T)s;
			}
			if (s.startsWith("[")&&s.endsWith("]") || s.startsWith("{")&&s.endsWith("}")) {
				try {
					x = DataActor.deserialize (s);
				} catch (IOException e) {
					x = s;
				}
			}
			
		}
		return (T)x;
	}

	public void clarifyAll() {
		a = clarify(aName);
		b = clarify(bName);

		if (isNum(a) && isNum(b)) {
			a = Double.parseDouble(String.valueOf(a));
			b = Double.parseDouble(String.valueOf(b));
			as = CompareAs.NUM;
		} else if (isBoolean(a) && isBoolean(b)) {
			a = Boolean.parseBoolean(String.valueOf(a));
			b = Boolean.parseBoolean(String.valueOf(b));
			as = CompareAs.BOOL;
		} else if (isString(a) && isString(b)) {
			a = String.valueOf(a);
			b = String.valueOf(b);
			as = CompareAs.STR;
		} else {
			as = CompareAs.OBJ;
		}

	}

	private boolean isString(Object o) {
		if (o == null)
			return false;
		return o instanceof String;
	}

	private boolean isBoolean(Object o) {
		if (o == null)
			return false;
		if (o instanceof Boolean)
			return true;
		if (o instanceof String) {
			if ("true".equals(o)||"false".equals("o")) return true;
		}
		return false;
	}

	private boolean isNum(Object o) {
		if (o == null)
			return false;
		if (o instanceof Number)
			return true;
		if (o instanceof String) {
			try {
				Double.parseDouble(String.valueOf(o));
				return true;
			} catch (Throwable e) {
				return false;
			}
		}
		return false;

	}

	protected ComparisonAction(String name) {
		super (name);
	}

	public ComparisonAction setA(String a) {
		this.aName = a;
		return this;
	}

	public ComparisonAction setB(String b) {
		this.bName = b;
		return this;
	}

	public ComparisonAction compareAs(CompareAs as) {
		this.as = as;
		return this;
	}


	@Override
	final protected void scenario() throws Exception {
		if (isTrue())
			return;
		fail("false on " + a + ' '+name+' ' + b + " compared as " + as);
	}

	private final static Map<String, Class<? extends ComparisonAction>> COMPARISONS = new HashMap<String, Class<? extends ComparisonAction>>() {
		{
			put("<", LessThanScenario.class);
			put("<=", LessOrEqualsThanScenario.class);
			put("==", EqualsScenario.class);
			put("!=", NotEqualsScenario.class);
			put(">=", GreatherOrEqualsThanScenario.class);
			put(">", GratherThanScenario.class);
			put("~=", StringLikeAction.class);
			put("!~", StringNotLikeAction.class);
		}
	};

	public static ComparisonAction scenario(String operation) {
		Class<? extends ComparisonAction> clazz = COMPARISONS.get(operation);
		if (clazz == null) {
			throw new Error("Wrong comparison oprtation " + operation);
		}
		try {
			ComparisonAction scenario = clazz.newInstance();
			return scenario;
		} catch (InstantiationException | IllegalAccessException e) {
			throw new Error("Couldn't prepare comparison " + operation);
		}
	}

	public static ComparisonAction numOperation(String operation) {
		return scenario(operation).compareAs(CompareAs.NUM);
	}

	public static ComparisonAction strOperation(String operation) {
		return scenario(operation).compareAs(CompareAs.STR);
	}

	@Override
	public String toString() {
		clarifyAll();
		return aName+"("+a+")" +  name +  bName+"("+b+")" ;
	}

	public ComparisonAction setB(Double b) {
		this.bName = String.valueOf(b);
		this.b = b;
		return this;
	}

	public ComparisonAction setA(Double a) {
		this.aName = String.valueOf(a);
		this.a = a;
		return this;
	}
	
	@Override
	public void collectExplain (Explain explain) {
		explain.consumes (aName);
		explain.consumes (bName);
	}
	
	

}
