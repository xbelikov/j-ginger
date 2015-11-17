package ginger.jsfml;

import ginger.jsfml.JSFMLEvents;
import ginger.jsfml.JSFMLEvent;
import ginger.common.Logger;

import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.Color;
import org.jsfml.system.Vector2i;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.KeyEvent;
import org.jsfml.window.ContextActivationException;
import org.jsfml.window.ContextSettings;

public class JSFMLWindow implements ginger.system.Window<Drawable> {
	private RenderWindow window;
	private ContextSettings ctx;
	
	private int width;
	private int height;
	private String title;
	
	private Logger log;
	
	public JSFMLWindow() {
		//Set OpenGL 3.0 to be the desired version
        this.ctx = new ContextSettings(3, 0);
        this.window = new RenderWindow();
        this.log = new Logger();
        this.log.setOwner(this.getClass().getName());
	}
	
	/**
	 * Set window title
	 */
	@Override
	public void setTitle(String title) {
		this.title = title;
		this.updateWindowSettings();
	}

	/**
	 * Set window height
	 */
	@Override
	public void setHeight(int height) {
		this.height = height;
		this.updateWindowSettings();
	}

	/**
	 * Set window width
	 */
	@Override
	public void setWidth(int width) {
		this.width = width;
		this.updateWindowSettings();
	}

	/**
	 * Open window
	 */
	@Override
	public boolean open() {
		this.window.create(
			new VideoMode(this.width, this.height), 
			this.title, RenderWindow.DEFAULT, this.ctx
		);
		
		if (this.window.isOpen()) {
			return true;
		}
		
		return false;
	}

	/**
	 * Close window
	 */
	@Override
	public void close() {
		if (this.window != null) {
			this.window.close();
		}
	}

	/**
	 * Check window is open
	 */
	@Override
	public boolean isOpen() {
		if (this.window != null) {
			if (this.window.isOpen()) {
				return true;
			}
		}
		
		return false;
	}

	/**
	 * Add drawable object to render queue
	 */
	@Override
	public void draw(Drawable o) {
		this.window.draw(o);
	}

	/**
	 * Clear buffer by color
	 */
	@Override
	public void clear(String colorName) {
		Color c = this.getColor(colorName);
		
		if (this.window != null) {
			this.window.clear(c);
		}
	}

	/**
	 * Display rendered content
	 */
	@Override
	public void showFrameContent() {
		this.window.display();
	}

	/**
	 * Poll window events 
	 * and convert they to ginger.Events<ginger.Event>
	 */
	@Override
	public ginger.system.Events pollEvents() {
		ginger.system.Events ev = new JSFMLEvents();
		
		for(Event event : this.window.pollEvents()) {
	        if(event.type == Event.Type.CLOSED) {
	            //The user pressed the close button
	            this.close();
	        } else if (event.type == Event.Type.KEY_PRESSED) {
	        	ev.push(this.getEvent(event));
	        }
	    }
		
		return ev;
	}
	
	/**
	 * Update sfmk window settings when it needs
	 */
	private void updateWindowSettings() {
		if (this.window != null) {
			this.window.setSize(new Vector2i(this.width, this.height));
			this.window.setTitle(this.title);
		}
	}
	
	private Color getColor(String colorName) {
		Color c = null;
		
		switch (colorName) {
			case "black":
				c = Color.BLACK;
				break;
				
			case "white":
				c = Color.WHITE;
				break;
				
			case "red":
				c = Color.RED;
				break;
				
			case "green":
				c = Color.GREEN;
				break;
				
			case "blue":
				c = Color.BLUE;
				break;
				
			default:
				c = Color.BLACK;
		}
		
		return c;
	}
	
	/**
	 * Create ginger Event from JSFML Event
	 * @param org.jsfml.window.event.Event e
	 * @return ginger.system.Event
	 */
	private ginger.system.Event getEvent(Event e) {
		ginger.system.Event gse = new ginger.jsfml.JSFMLEvent();
		KeyEvent ke;
		
		switch (e.type) {
			case KEY_PRESSED:
				ke = e.asKeyEvent();
				
				gse.setType(ginger.system.Event.TYPE_KEY_DOWN);
				gse.setArgs(new String[] {ke.key.name()});
				
				break;
				
			case KEY_RELEASED:
				ke = e.asKeyEvent();
				
				gse.setType(ginger.system.Event.TYPE_KEY_UP);
				gse.setArgs(new String[] {ke.key.name()});
				
				break;
			
			default:
				break;
		}
		
		return gse;
	}

	@Override
	public void setIsActive(boolean b) {
		try {
			this.window.setActive(b);
		} catch (ContextActivationException e) {
			e.printStackTrace();
		}
	}
}
