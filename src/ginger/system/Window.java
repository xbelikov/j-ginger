package ginger.system;

import ginger.system.Events;

/**
 * Window interface
 * @param <T> type used to draw
 */
public interface Window<T> {
	public void setTitle(String title);
	public void setHeight(int height);
	public void setWidth(int width);
	public boolean open();
	public void close();
	public boolean isOpen();
	public void draw(T o);
	public void clear(String colorName);
	public void showFrameContent();
	public void setIsActive(boolean b);
	
	public Events pollEvents();
}
