package ginger.main;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.Font;
//import org.jsfml.graphics.Image;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Text;
import org.jsfml.window.ContextSettings;
import org.jsfml.window.VideoMode;
import org.jsfml.window.Window;
import org.jsfml.window.event.Event;

import java.io.IOException;
import java.nio.file.Paths;
//import java.util.HashMap;

import ginger.common.Logger;
//import ginger.main.Entity;

public class Scene extends Thread {
	static final String LOG_MSG_START = "Start scene rendering";
	static final String LOG_MSG_STOP = "Stop scene rendering";
	static final String LOG_MSG_RENDER_FRAME = "Rendering frame";
	
	Logger log;
	
	public Scene()
	{
		this.log = new Logger();
		this.log.setOwner(this.getClass().getName());
	}
	
	@Override
	public void run()
	{
		this.log.add(LOG_MSG_START);
		
		//Set OpenGL 3.0 to be the desired version
        ContextSettings settings = new ContextSettings(3, 0);

        //Create a render window
        RenderWindow window = new RenderWindow(
                new VideoMode(640, 480),
                "JSFML Standalone Example",
                Window.DEFAULT,
                settings);
        
        Font freeSans = new Font();
        
        try {
            freeSans.loadFromFile(Paths.get("resources/FreeSans.ttf"));
        } catch(IOException ex) {
            //Failed to load font
            ex.printStackTrace();
        }
        
        Text text1 = new Text("This is an example text.", freeSans, 24);
        text1.setPosition(100, 100);
		
		try {
			this.log.add(LOG_MSG_RENDER_FRAME);
			
			while (window.isOpen()) {
				 window.clear(Color.RED);
				
				//s.acquire();
				window.draw(text1);
				window.display();
				//s.release();
				
				for(Event event : window.pollEvents()) {
			        if(event.type == Event.Type.CLOSED) {
			            //The user pressed the close button
			            window.close();
			        }
			    }
			}
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
		
		this.log.add(LOG_MSG_STOP);
	}
}
