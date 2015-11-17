package ginger.system;

import ginger.main.Scene;

public interface Renderer {
	public void addScene(Scene scene);
	public void setScene(String name);
	public void addToRenderList(String name);
	public void update();
}
