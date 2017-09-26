package com.genesyslab.ats.unified.scenario;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanMap;

import com.genesys.ats.basics.AtsClause;
import com.genesys.ats.basics.Explain;
import com.genesyslab.ats.unified.actors.DataActor;

import lombok.AllArgsConstructor;

/**
 * Use QuickModifyAction instead
 * @author SLypchan
 *
 */
@Deprecated
public class ModifyObjectClause extends AtsClause  {
	
	@AllArgsConstructor
	private static class Modification {
		String field;
		String value;
		boolean asJson = false;
		public Modification(String field, String value) {
			super();
			this.field = field;
			this.value = value;
		}
		@Override
		public String toString() {
			switch (field) {
			case "add": 
			case "remove": 
				return field +' '+value;
			default: 
				return field +"="+value;
			}
		}
	}
	
	List<Modification> modifications = new ArrayList<> ();

	public ModifyObjectClause (String name) {
		super (name);
		
	}
	
	public void update (String name, String value) {
		modifications.add(new Modification (name, value));
	}
	
	public void updateAsJson (String name, String value) {
		modifications.add(new Modification (name, value, true));
	}
	

	@Override
	public void  scenario () throws Exception {
		Object dest = clarify (name);
		
		Map bm;
		if (dest instanceof Map) {
			bm = (Map)dest;
		} else if (dest instanceof List){
			applyModificationsToList ( (List)dest );
			return ;
		} else {
			bm = new BeanMap (dest);
		}
		
		for (Modification m : modifications) {
			Object v = clarify (m.value);
			if (m.asJson && v instanceof String) {
				v = OBJECT_MAPPER.readValue((String)v, Object.class);
			}
			String field = (String)clarify (m.field);
			bm.put(field, v);
		}
	}



	private void applyModificationsToList(List dest) {
		
		for (Modification m : modifications) {
			Object v = clarify (m.value);
			switch (m.field) {
			case "add": 
				dest.add(v);
				break;
			case "remove":
				Integer index = toIndex (v);
				if (index != null)
					dest.remove(index.intValue());
				else
					dest.remove(v);
				break;
			}
		}
		
	}

	private Integer toIndex(Object v) {
		try {
			return Integer.parseInt(String.valueOf(v));
		} catch (Throwable e) {
			return null;
		}
	}
	
	@Override
	public String toString() {
		return "modify "+name+modifications; 
	}
	
	@Override
	public void draw(PrintStream out) {
		out.println(toString());;
	}
	
	@Override
	public void collectExplain (Explain explain) {
		explain.modifies (name);
		for (Modification m : modifications) {
			explain.consumes(m.value);
		}
	}
	
	
	
}
