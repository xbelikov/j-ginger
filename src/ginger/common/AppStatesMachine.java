package ginger.common;

import ginger.common.AppState;

import java.util.HashMap;
import java.util.Map;

public class AppStatesMachine {
	private Map<String, AppState> states;
	private String currentState;
	
	public AppStatesMachine() {
		this.states = new HashMap<String, AppState>();
	}
	
	public AppStatesMachine(Map<String, AppState> states) {
		this.states = states;
		
		this.states.forEach((name, state)->{
			if (state.getIsRoot()) {
				this.currentState = name; 
			}
		});
	}
}
