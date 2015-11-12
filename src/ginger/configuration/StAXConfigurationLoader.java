package ginger.configuration;

import ginger.configuration.ConfigurationLoader;
import ginger.common.Keymap;
import ginger.common.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
//import java.util.ArrayList;
import java.util.Iterator;
//import java.util.List;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class StAXConfigurationLoader implements ConfigurationLoader {
	static final String DEFAULT_CONFIG = "resources/config.xml";
	
	static final String EL_CONFIGURATION = "configuration";
	static final String ATTR_CONFIGURATION_VERSION = "version";
	
	static final String EL_APP = "app";
	static final String ATTR_APP_TITLE = "title";
	
	static final String EL_WINDOW = "window";
	static final String ATTR_WINDOW_HEIGHT = "height";
	static final String ATTR_WINDOW_WIDTH = "width";
	static final String ATTR_WINDOW_TYPE = "type";
	
	static final String EL_PATH = "path";
	static final String ATTR_PATH_CHILDS_VALUE = "value";
	static final String EL_PATH_RESOURCES = "resources";
	static final String EL_PATH_SCENES = "scenes";
	static final String EL_PATH_SCENEOBJECTS = "sceneobjects";
	static final String EL_PATH_GUI = "gui";
	
	static final String EL_KEYMAPS = "keymaps";
	static final String EL_KEYMAP = "keymap";
	static final String EL_KEY = "key";
	static final String ATTR_KEYMAP_APP_STATE = "app-state";
	static final String ATTR_KEY_VALUE = "value";
	static final String ATTR_KEY_ACTION = "action";
	
	Logger log;
	XMLEventReader eventReader;
	
	public StAXConfigurationLoader() {
		this.log = new Logger();
		this.log.setOwner(this.getClass().getName());
	}
	
	@Override
	public void load(Configuration c) {
		try {
		    // First, create a new XMLInputFactory
		    XMLInputFactory inputFactory = XMLInputFactory.newInstance();
		    // Setup a new eventReader
		    InputStream in = new FileInputStream(DEFAULT_CONFIG);
			this.eventReader = inputFactory.createXMLEventReader(in);
			
			while (this.eventReader.hasNext()) {
		    	XMLEvent event = this.eventReader.nextEvent();

		        if (event.isStartElement()) {
		        	StartElement startElement = event.asStartElement();
		         	String elName = startElement.getName().getLocalPart();
		         	
	        		if (elName.equals(EL_APP)) {
	        			this.log.add(EL_APP + " element found");
	        			this.setApp(c, startElement);
	        			
	        			continue;
	        		}
	        		
	        		if (elName.equals(EL_WINDOW)) {
	        			this.log.add(EL_WINDOW + " element found");
	        			this.setWindow(c, startElement);
	        			
	        			continue;
	        		}
	        		
	        		if (elName.equals(EL_PATH)) {
	        			this.log.add(EL_PATH + " element found");
	        			this.setPath(c);
	        			
	        			continue;
	        		}
	        		
	        		if (elName.equals(EL_KEYMAPS)) {
	        			this.log.add(EL_KEYMAPS + " element found");
	        			this.setKeymaps(c);
	        			
	        			continue;
	        		}
		        }
		    }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void save(Configuration c) {
		
	}
	
	@SuppressWarnings("unchecked")
	private void setApp(Configuration c, StartElement el) {
		Iterator<Attribute> attributes = el.getAttributes();
		
		while (attributes.hasNext()) {
			Attribute attr = attributes.next();
			String attrName = attr.getName().toString();
			
			switch (attrName) {
				case ATTR_APP_TITLE:
					c.windowTitle = attr.getValue();
        			this.log.add(ATTR_APP_TITLE + ": " + c.windowTitle);
					break;
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private void setWindow(Configuration c, StartElement el) {
		Iterator<Attribute> attributes = el.getAttributes();
		
		while (attributes.hasNext()) {
			Attribute attr = attributes.next();
			String attrName = attr.getName().toString();
			
			switch (attrName) {
				case ATTR_WINDOW_WIDTH:
					this.log.add(ATTR_WINDOW_WIDTH + ": " + attr.getValue());
					c.windowWidth = Integer.parseInt(attr.getValue());
					break;
					
				case ATTR_WINDOW_HEIGHT:
					this.log.add(ATTR_WINDOW_HEIGHT + ": " + attr.getValue());
					c.windowHeight = Integer.parseInt(attr.getValue());
					break;
					
				case ATTR_WINDOW_TYPE:
					this.log.add(ATTR_WINDOW_TYPE + ": " + attr.getValue());
					c.windowType = attr.getValue();
					break;
			}
		}
	}
	
	private void setPath(Configuration c) throws XMLStreamException {
		while (this.eventReader.hasNext()) {
			XMLEvent event = this.eventReader.nextEvent();
			
			if (event.isEndElement()) {
				 EndElement eel = event.asEndElement();
				 if (eel.getName().getLocalPart().equals(EL_PATH)) {
					 break;
				 }
			}
			
			if (event.isStartElement()) {
				StartElement el = event.asStartElement();
				this.setPathTo(c, el);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private void setPathTo(Configuration c, StartElement el) {
		Iterator<Attribute> attributes = el.getAttributes();
		String elName = el.getName().getLocalPart();
		
		while (attributes.hasNext()) {
			Attribute attr = attributes.next();
			String attrName = attr.getName().toString();
			
			if (attrName.equals(ATTR_PATH_CHILDS_VALUE)) {
				switch (elName){
					case EL_PATH_RESOURCES:
						this.log.add("resources in " + attr.getValue());
						c.pathToResources = attr.getValue();
						break;
					
					case EL_PATH_SCENES:
						this.log.add("scenes in " + attr.getValue());
						c.pathToScenes = attr.getValue();
						break;
						
					case EL_PATH_SCENEOBJECTS:
						this.log.add("scene objects in " + attr.getValue());
						c.pathToSceneObjects = attr.getValue();
						break;
						
					case EL_PATH_GUI:
						this.log.add("gui in " + attr.getValue());
						c.pathToGui = attr.getValue();
						break;
				}
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private void setKeymaps(Configuration c) throws XMLStreamException {
		while (this.eventReader.hasNext()) {
			XMLEvent event = this.eventReader.nextEvent();
			
			if (event.isEndElement()) {
				 EndElement eel = event.asEndElement();
				 if (eel.getName().getLocalPart().equals(EL_KEYMAPS)) {
					 break;
				 }
			}
			
			if (event.isStartElement()) {
				StartElement el = event.asStartElement();
				String elName = el.getName().getLocalPart();
				Iterator<Attribute> attributes = el.getAttributes();
				
				String appStateName = "";
				
				if (elName.equals(EL_KEYMAP)) {
					this.log.add(EL_KEYMAP + " element found");
					
					while (attributes.hasNext()) {
						Attribute attr = attributes.next();
						String attrName = attr.getName().toString();
						
						switch (attrName){
							case ATTR_KEYMAP_APP_STATE:
								appStateName = attr.getValue();
								this.log.add("for app state: " + appStateName);
								c.keymaps.put(appStateName, new Keymap(appStateName));
								break;
						}
					}
				}
				
				this.setKeymap(c, appStateName);
			}
		}
	}
	
	private void setKeymap(Configuration c, String appStateName) throws XMLStreamException {
		while (this.eventReader.hasNext()) {
			XMLEvent event = this.eventReader.nextEvent();
			
			if (event.isEndElement()) {
				 EndElement eel = event.asEndElement();
				 if (eel.getName().getLocalPart().equals(EL_KEYMAP)) {
					 break;
				 }
			}
			
			if (event.isStartElement()) {
				StartElement el = event.asStartElement();
				this.setKey(c, el, appStateName);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private void setKey(Configuration c, StartElement el, String appStateName) {
		Iterator<Attribute> attributes = el.getAttributes();
		String key = "";
		String action = "";
		
		while (attributes.hasNext()) {
			Attribute attr = attributes.next();
			String attrName = attr.getName().toString();
			
			switch (attrName){
				case ATTR_KEY_VALUE:
					this.log.add(ATTR_KEY_VALUE + ": " + attr.getValue());
					key = attr.getValue();
					break;
					
				case ATTR_KEY_ACTION:
					this.log.add(ATTR_KEY_ACTION + ": " + attr.getValue());
					action = attr.getValue();
					break;
			}
		}
		
		c.keymaps.get(appStateName).setKey(key, action);
	}
}
