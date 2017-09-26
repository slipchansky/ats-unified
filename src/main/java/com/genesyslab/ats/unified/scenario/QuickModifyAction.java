package com.genesyslab.ats.unified.scenario;


import java.util.regex.Pattern;

import com.genesys.ats.basics.AtsAction;
import com.genesys.ats.basics.Explain;
import com.genesyslab.ats.unified.actors.EvalUtil;


public class QuickModifyAction extends MemoriesAction {
	enum Modification {
		SET, ADD, REMOVEALL, REMOVE
	}
	
	Modification activity = Modification.SET;

	public QuickModifyAction (String name, String value) {
		super (name);
		setValueName(value);
	}
	
	public QuickModifyAction(String name) {
		super (name);
	}

	@Override
	protected void scenario() throws Exception {
		Object v = getData ();
		
		if (activity == Modification.SET)
		if (isDirectName (name) ) {
			String nm = clarify ('$'+name);
			if (toMemory)
				remember (name, v);
			else
				put (nm, v);
			return;
			
		}
		
		String nm = translate (name);
		switch (activity) {
		case ADD:
			EvalUtil.add (nm, v);
			break;
		case SET:
			EvalUtil.modify (nm, v);
			break;
		case REMOVEALL:
			EvalUtil.removeall (nm);
			break;
			
		case REMOVE:
			EvalUtil.remove (nm, v);
			break;
		}
	}

	private final Pattern SIMPLE = Pattern.compile("^([a-z|A-Z|0-9|_|\\-]*)$");
	private boolean isDirectName(String name) {
		return SIMPLE.matcher(name).find();
	}

	public QuickModifyAction add() {
		this.activity = Modification.ADD;
		return this;
	}

	public QuickModifyAction removeAll() {
		this.activity = Modification.REMOVEALL;
		return this;
	}

	public QuickModifyAction remove() {
		this.activity = Modification.REMOVE;
		return this;
	}
	
	@Override
	public void collectExplain (Explain explain) {
		explain.modifies (name);
		explain.atRight(value);
	}
	
	
	

}
