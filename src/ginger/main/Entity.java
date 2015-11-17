package ginger.main;

import ginger.system.Updatable;
import ginger.system.Drawable;
import ginger.system.SystemFactory;

public class Entity implements Updatable, Drawable {
	private String drwName;
	
	@Override
	public void update(SystemFactory sf) {
		
	}

	@Override
	public String getDrwName() {
		return this.drwName;
	}

	@Override
	public void setDrwName(String name) {
		this.drwName = name;
	}
}
