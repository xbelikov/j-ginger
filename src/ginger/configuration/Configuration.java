package ginger.configuration;

import ginger.common.Keymap;
import ginger.common.AppState;
import ginger.common.Logger;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.nio.file.InvalidPathException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Configuration {
	static final String LOG_MSG_CHECK_PATH = "checking path...";
	static final String LOG_MSG_PATH_EXISTS = "OK";
	static final String LOG_MSG_PATH_NOT_FOUND = "path not found";
	
	Logger log;
	
	int windowWidth;
	int windowHeight;
	String windowType;
	
	String windowTitle;
	
	String pathToResources;
	
	String pathToScenes;
	String pathToSceneObjects;
	
	String pathToGui;
	
	/**
	 * Map<String appStateName, Keymap>
	 */
	Map<String, Keymap> keymaps;
	List<AppState> appStates;
	
	public Configuration() {
		this.log = new Logger();
		this.log.setOwner(this.getClass().getName());
		
		this.appStates = new ArrayList<AppState>();
		this.appStates.add(new AppState("init", true));
		this.appStates.add(new AppState("loading", false));
		this.appStates.add(new AppState("pause", false));
		this.appStates.add(new AppState("play", true));
		
		this.keymaps = new HashMap<String, Keymap>();
	}
	
	public boolean validatePath(String pathTo) throws Exception {
		boolean pathExists = false;
		
		this.log.add(LOG_MSG_CHECK_PATH);
		this.log.add(pathTo);
		
		Path path = Paths.get(pathTo);
		pathExists = Files.exists(path, new LinkOption[]{ LinkOption.NOFOLLOW_LINKS});
		
		if (pathExists) {
			this.log.add(LOG_MSG_PATH_EXISTS);
		} else {
			this.log.add(LOG_MSG_PATH_NOT_FOUND);
			
			Exception e = new InvalidPathException(pathTo, "invalid path");
			throw e;
		}
		
		return pathExists;
	}
	
	public boolean check() {
		try {
			this.validatePath(this.pathToResources);
			this.validatePath(this.pathToScenes);
			this.validatePath(this.pathToSceneObjects);
			this.validatePath(this.pathToGui);
		
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
}
