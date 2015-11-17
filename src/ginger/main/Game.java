package ginger.main;


import ginger.configuration.StAXConfigurationLoader;
import ginger.jsfml.JSFMLWindow;
import ginger.configuration.ConfigurationLoader;
import ginger.configuration.Configuration;

import ginger.common.Logger;
import ginger.main.Scene;
import ginger.system.Window;
import ginger.system.Renderer;
import ginger.system.Events;
import ginger.main.InputQueue;

import java.util.concurrent.Semaphore;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Game {
	static final String LOG_MSG_START = "Start engine";
	//static final String LOG_MSG_STOP = "Stop engine";
	static final String LOG_MSG_ALL_STARTED = "All modules started";
	
	Logger log;
	Configuration c;
	
	Scene scene;
	Renderer renderer;
	volatile Events events;
	Window<org.jsfml.graphics.Drawable> window;
	
	//Window window;
	
	/**
	 * Default constructor
	 */
	public Game() {
		this.log = new Logger();
		this.log.setOwner(this.getClass().getName());
		
		this.c = new Configuration(); 
		ConfigurationLoader cl = new StAXConfigurationLoader();
		cl.load(this.c);
		
		//this.scene = new Scene();
		//this.window = new Window();
	}
	
	/**
	 * Start game modules
	 * @param args
	 */
	public void start(String[] args)
	{
		log.add(LOG_MSG_START);
		
		ExecutorService es = Executors.newCachedThreadPool();
		
		es.submit(new Runnable() {
			public void run() {
				log.add("start logic thread");
				
				while (true) {
					log.add("some logic...");
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		
		es.submit(new Runnable() {
			public void run() {
				log.add("start renderer thread");

				/**
				 * testing code
				 */
				window	= new JSFMLWindow();
				window.setTitle(c.windowTitle);
				window.setWidth(c.windowWidth);
				window.setHeight(c.windowHeight);
				
				if (window.open()) {
					renderer = new ginger.jsfml.JSFMLRenderer(window);
				}
				
				while (window.isOpen()) {
					renderer.update();
					events = window.pollEvents();
					events.clear();
				}
			}
		});
	}
	
	/**
	 * Stop game modules
	 */
	//public void stop()
	//{
	//	log.add(LOG_MSG_STOP);
	//}
}
