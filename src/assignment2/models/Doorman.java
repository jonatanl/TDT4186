package assignment2.models;

import assignment2.Constants;
import assignment2.CustomerQueue;
import assignment2.Globals;
import assignment2.Gui;

/**
 * This class implements the doorman's part of the
 * Barbershop thread synchronization example.
 */
public class Doorman extends Thread{
    private CustomerQueue queue;
    private Gui gui;
    private boolean isRunning;

    /**
	 * Creates a new doorman.
	 * @param queue		The customer queue.
	 * @param gui		A reference to the GUI interface.
	 */
    public Doorman(CustomerQueue queue, Gui gui) {
        this.queue = queue;
        this.gui = gui;
        isRunning = false;
    }

    /**
	 * Starts the doorman running as a separate thread.
	 */
	public void startThread() {
        isRunning = true;
        start();
	}

	/**
	 * Stops the doorman thread.
	 */
	public void stopThread() {
		isRunning = false;
	}

    @Override
    public void run() {
        while (isRunning){
            try {
                sleep(calculateSleepTime());
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }

            queue.addCustomerToQueue(new Customer());
            gui.println("New customer in queue");
        }
    }

    private int calculateSleepTime(){
        return Globals.doormanSleep * (int) (Math.random() + 1);
    }
}
