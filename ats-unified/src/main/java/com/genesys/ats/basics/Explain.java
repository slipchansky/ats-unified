package com.genesys.ats.basics;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class Explain {

	@Getter @Setter
	private String snippetName;
	
	@Data
	public static class VarExplain {
		String name;
		String def;
		String comment;
		public VarExplain(String name) {
			super();
			this.name = name;
		}
		public VarExplain(String name, String def) {
			super();
			this.name = name;
			this.def = def;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			return result;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (obj instanceof String) {
				return obj.equals(this.name);
			}
			if (getClass() != obj.getClass())
				return false;
			VarExplain other = (VarExplain) obj;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			return true;
		}
		
		@Override
		public String toString() {
			if (def==null) return name;
			return name +" = "+def;
		}
		
	}
	
	private static class VarExplainList extends ArrayList<VarExplain> {
		@Override
		public boolean contains(Object o) {
			if (o instanceof String) {
				o = new VarExplain ((String)o);
			}
			return super.contains(o);
		}
	}
	
	public boolean contains(Object o)  {
		return consumes.contains(o) || produces.contains(o) || modifies.contains(o) || defs.contains(o) || remembers.contains(o) || alreadyKnown.contains(o);
	}
	
	@Getter
	List<VarExplain> consumes = new VarExplainList ();
	
	@Getter
	List<VarExplain> produces = new VarExplainList ();
	
	@Getter
	List<VarExplain> defs = new VarExplainList ();
	
	@Getter
	List<VarExplain> modifies = new VarExplainList ();
	
	@Getter
	List<VarExplain> remembers = new VarExplainList ();
	
	@Getter
	List<VarExplain> alreadyKnown = new VarExplainList ();

	@Getter
	private String snippetComment;
	
	
	public void atRight (String v) {
		if (v==null) return;
		if (v.startsWith("$")) {
			if (!contains (v)) {
				if (!producedByMe (v)) {
				  consumes.add(new VarExplain (v));
				}
			}
		}
	}
	
	private boolean producedByMe(String v) {
		if (isMentioned (produces, v)) return true;
		if (isMentioned (remembers, v)) return true;
		if (isMentioned (defs, v)) return true;
		return false;
	}

	private boolean isMentioned(List<VarExplain> area, String v) {
		for (VarExplain x : area)
			if (v.startsWith(x.getName()+'.')) return true;
		return false;
	}

	public void produces (String v) {
		if (v == null) return;
		
		if (v.startsWith("$.") && !v.equals("$.")) {
			if (contains (v)) return;
			produces.add(new VarExplain (v));
		} else
		if (v.startsWith("$") && !v.equals("$")) {
			if (contains (v)) return;
			modifies.add(new VarExplain (v));
		} else if (!v.equals("$")) {
			if (contains ("$"+v)) return;
			produces.add(new VarExplain ("$"+v));
		} else {
			if (contains (v)) return;
			produces.add(new VarExplain (v));
		}
	}
	
	public void consumes (String v) {
		atRight (v);
	}
	
	public void def (String n, String v) {
		if (contains (n)) return;
		if (n.startsWith("$.")) {
			defs.add(new VarExplain (n, v));
			if (v.startsWith("$"))
				atRight (v);
			return;
		}
		defs.add(new VarExplain ("$"+n, v));
		if (v.startsWith("$"))
			atRight (v); 
	}

	public void remember(String name, String value) {
		remembers.add(new VarExplain ("$"+name, value));
	}
	
	public void remember(String name) {
		remembers.add(new VarExplain ("$"+name));
	}

	public void modifies(String name) {
		modifies.add(new VarExplain (name));
	}

	public Explain(String snippetName, String comment) {
		this.snippetName = snippetName;
		this.snippetComment = comment;
	}
	
	public Explain() {
		this.snippetName = ".";
	}
	
	public Explain (Explain e) {
		this ();
		this.alreadyKnown.addAll (e.consumes);
		this.alreadyKnown.addAll (e.produces);
		this.alreadyKnown.addAll (e.defs);
		this.alreadyKnown.addAll (e.modifies);
		this.alreadyKnown.addAll (e.remembers);
		this.alreadyKnown.addAll (e.alreadyKnown);
	}
	

}
