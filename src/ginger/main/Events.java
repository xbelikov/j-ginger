package ginger.main;

import java.util.Scanner;
import java.util.concurrent.Semaphore;

import ginger.common.Logger;
import ginger.main.InputQueue;

public class Events extends Thread {
	static final String LOG_MSG_START = "Start events listening";
	static final String LOG_MSG_STOP = "Stop events listening";
	static final String LOG_MSG_LISTEN = "listen...";
	
	private Logger log;
	private Scanner in;
	private InputQueue inputQueue;
	private boolean alive = true;
	private Semaphore s;
	
	public Events(Semaphore s, InputQueue inputQueue)
	{
		this.log = new Logger();
		this.log.setOwner(this.getClass().getName());
		
		this.in = new Scanner(System.in);
		this.inputQueue = inputQueue;
		this.s = s;
	}
	
	@Override
	public void run()
	{
		this.log.add(LOG_MSG_START);
		
		try {
			this.log.add(LOG_MSG_LISTEN);
			
			
			while (this.alive) {
				s.acquire();
				//this.checkInput();

				this.inputQueue.q = this.in.nextLine();
				if (this.inputQueue.q.contains("q")) {
					this.alive = false;
				}
				
				s.release();
			}
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
		
		this.log.add(LOG_MSG_STOP);
	}
}