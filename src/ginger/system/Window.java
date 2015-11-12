package ginger.system;

import ginger.system.Events;

public interface Window {
	public void setTitle(String title);
	public void setHeight(int height);
	public void setWidth(int width);
	public void open();
	public void close();
	public boolean isOpen();
	public void draw(Drawable o);
	public void clear(String colorName);
	public void showFrameContent();
	
	public Events pollEvents();
}
