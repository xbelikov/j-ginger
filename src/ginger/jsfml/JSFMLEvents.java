package ginger.jsfml;

import ginger.system.Events;
import ginger.system.Event;
import ginger.common.Logger;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ArrayBlockingQueue;

public class JSFMLEvents implements Events {
	private BlockingQueue<Event> list;

	private Logger log;
	
	public JSFMLEvents() {
		this.list = new ArrayBlockingQueue<Event>(256);
		this.log = new Logger();
		this.log.setOwner(this.getClass().getName());
	}
	
	@Override
	public void push(Event e) {
		try {
			this.list.put(e);
			this.log.add("put event [" + e.getType() + ": " + ((e.getArgs().length > 0)? e.getArgs()[0] : "") + "]");
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public Event pop() {
		Event ev = null;
		
		try {
			ev = this.list.take();
			this.log.add("pop event [" + ev.getType() + ": " + ((ev.getArgs().length > 0)? ev.getArgs()[0] : "") + "]");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return ev;
	}

	@Override
	public void clear() {
		this.list.clear();
	}
}
