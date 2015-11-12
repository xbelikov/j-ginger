package ginger.common;

public class AppState {
	private String name;
	private boolean isRoot;
	
	public AppState(String name, boolean isRoot) {
		this.name = name;
		this.isRoot = isRoot;
	}
	
	public boolean getIsRoot() {
		return this.isRoot;
	}
	
	public char[] getName() {
		return this.name.toCharArray();
	}
}
