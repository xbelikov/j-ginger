package ginger.system;

import ginger.system.Events;

public interface SystemFactory {
	static final String TYPE_DRAWABLE = "drawable";
	
	public void createWindow(String title, int w, int h);
	public void createRenderer();
	public Events getEvents();

	public boolean load(String type, String name); 
}
