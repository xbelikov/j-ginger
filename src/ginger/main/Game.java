package ginger.main;

import java.util.concurrent.Semaphore;

import ginger.common.Logger;
import ginger.main.Scene;
import ginger.main.Events;
import ginger.main.InputQueue;

public class Game {
	static final String LOG_MSG_START = "Start engine";
	//static final String LOG_MSG_STOP = "Stop engine";
	static final String LOG_MSG_ALL_STARTED = "All modules started";
	
	Logger log;
	//Configuration c;
	//EventsWorker e;
	//TasksWorker t;
	
	//TasksQueue tq;
	Scene scene;
	Events events;
	
	InputQueue inputQueue;
	Semaphore s;
	
	/**
	 * Default constructor
	 */
	public Game() {
		this.log = new Logger();
		this.log.setOwner(this.getClass().getName());
		
		this.s = new Semaphore(1);
		
		this.inputQueue = new InputQueue();
		
		this.events = new Events(s, this.inputQueue);
		this.scene = new Scene(s, this.inputQueue);
	}
	
	/**
	 * Start game modules
	 * @param args
	 */
	public void start(String[] args)
	{
		log.add(LOG_MSG_START);
		
		this.events.start();
		this.scene.start();
	}
	
	/**
	 * Stop game modules
	 */
	//public void stop()
	//{
	//	log.add(LOG_MSG_STOP);
	//}
}
