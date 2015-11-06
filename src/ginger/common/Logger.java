package ginger.common;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Logger {
	static final String TYPE_INFO = "INFO";
	static final String TYPE_ERROR = "ERROR";
	static final String TYPE_WARNING = "WARNING";
	
	private String owner;
	private DateFormat dateFormat;
	
	/**
	 * Default constructor
	 */
	public Logger() {
		this.dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	}
	
	/**
	 * Sets owner
	 * @param ownerName
	 */
	public void setOwner(String ownerName)
	{
		this.owner = ownerName;
	}
	
	/**
	 * Add string to log
	 * @param str
	 */
	public void add(String str)
	{
		this.print("[" + this.owner + "]: " + str);
	}
	
	/**
	 * Print string with current date/time
	 * @param str
	 */
	private void print(String str)
	{
		Date date = new Date();
		System.out.println(this.dateFormat.format(date) + " " + str);
	}
}
