package ginger.jsfml;

import ginger.common.Logger;
import ginger.main.Scene;
import ginger.system.Renderer;
import ginger.system.Window;

import org.jsfml.graphics.Drawable;
import org.jsfml.system.Clock;
import org.jsfml.system.Time;
//import ginger.jsfml.JSFMLWindow;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class JSFMLRenderer implements Renderer {
	/**
	 * TODO: Maybe create advanced drawable resource manager?
	 */
	private Map<String, Drawable> drws;
	private ArrayList<String> drwsRenderList;
	
	private Window<Drawable> window;
	//private List<Scene> scenes;
	//private String currentSceneName;
	private float clockValue; 
	private Clock clock;
	
	private Logger log;
	
	public JSFMLRenderer(Window<Drawable> target) {
		this.clock = new Clock();
		
		this.clockValue = 0.0f; 
		this.window = target;
		//this.scenes = new ArrayList<Scene>();
		this.drws = new HashMap<String, Drawable>();
		this.drwsRenderList = new ArrayList<String>(); 
		this.log = new Logger();
		this.log.setOwner(this.getClass().getName());
	}

	@Override
	public void addScene(Scene scene) {
		//this.scenes.add(scene);
	}
	
	@Override
	public void setScene(String name) {
		//this.currentSceneName = name;
	}

	@Override
	public void update() {
		Time t = this.clock.restart();
		this.clockValue = t.asSeconds();
		
		//this.log.add("update");
		this.window.clear("black");
		
		this.drwsRenderList.forEach((name) -> {
			if (this.drws.containsKey(name)) {
				Drawable d = this.drws.get(name);
				this.window.draw(d);
			}
		});
		
		this.window.showFrameContent();
		
		//this.window.pollEvents();
		this.drwsRenderList.clear();
	}

	@Override
	public void addToRenderList(String name) {
		this.drwsRenderList.add(name);
	}
}
