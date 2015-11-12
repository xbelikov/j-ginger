package ginger.system;

import ginger.system.Event;

public interface Events {
	public void push(Event e);
	public Event pop();
}