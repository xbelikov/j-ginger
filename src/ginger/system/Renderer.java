package ginger.system;

import ginger.main.Scene;
import ginger.system.Window;

public interface Renderer {
	public void setTarget(Window window);
	public void setScene(Scene scene);
	public void update();
}
