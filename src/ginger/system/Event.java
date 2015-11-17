package ginger.system;

public interface Event {
	static final String TYPE_KEY_DOWN = "keydown";
	static final String TYPE_KEY_UP = "keyup";
	static final String TYPE_WIN_CLOSE = "winclose";
		
	/**
	 * Set event type
	 * @param type
	 */
	public void setType(String type);

	/**
	 * Set event args
	 * @param args
	 */
	public void setArgs(String[] args);
	
	/**
	 * Get event type
	 * @return String
	 */
	public String getType();
	
	/**
	 * Get event args
	 * @return String[]
	 */
	public String[] getArgs();
}
