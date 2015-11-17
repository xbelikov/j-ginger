package ginger.jsfml;

import ginger.system.Event;

public class JSFMLEvent implements Event {
	private String type;
	private String[] args;

	@Override
	public void setType(String type) {
		this.type = type;
	}

	@Override
	public void setArgs(String[] args) {
		this.args = args;
	}

	@Override
	public String getType() {
		return this.type;
	}

	@Override
	public String[] getArgs() {
		return this.args;
	}
	
	
}