package ginger.common;

/**
 * Keyboard mapping to actions
 */

import java.util.HashMap;
import java.util.Map;

public class Keymap {
	private String appStateName;
	private Map<String, String> data;
	
	public Keymap(String appStateName) {
		this.appStateName = appStateName;
		this.data = new HashMap<String, String>();
	}
	
	public void setKey(String key, String action) {
		this.data.put(key, action);
	}
	
	public String getAction(String key) {
		return this.data.get(key);
	}
}
