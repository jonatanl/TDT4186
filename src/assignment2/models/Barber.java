package assignment2.models;

import assignment2.Constants;
import assignment2.CustomerQueue;
import assignment2.Globals;
import assignment2.Gui;

/**
 * This class implements the barber's part of the
 * Barbershop thread synchronization example.
 */
public class Barber extends Thread{
    private CustomerQueue queue;
    private Gui gui;
    private int position;
    private boolean isRunning;

	/**
	 * Creates a new barber.
	 * @param queue		The customer queue.
	 * @param gui		The GUI.
	 * @param position	The position of this barber's chair
	 */
    public Barber(CustomerQueue queue, Gui gui, int position) {
        this.queue = queue;
        this.gui = gui;
        this.position = position;
    }

    /**
	 * Starts the barber running as a separate thread.
	 */
	public void startThread() {
        isRunning = true;
        start();
	}

	/**
	 * Stops the barber thread.
	 */
	public void stopThread() {
		isRunning = false;
	}

    @Override
    public void run() {
        while (isRunning){
            sleepBarber();
            Customer customer = queue.getNextCustomer();
            workBarber(customer);
        }
    }

    private void sleepBarber(){
        try {
            gui.barberIsSleeping(position);
            sleep(calculateSleepTime());
            gui.barberIsAwake(position);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    private void workBarber(Customer customer){
        gui.fillBarberChair(position, customer);

        try {
            sleep(calculateWorkTime());
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

        gui.emptyBarberChair(position);
    }

    private int calculateWorkTime(){
        return Globals.barberWork * (int) (Math.random() + 1);
    }

    private int calculateSleepTime(){
        return Globals.barberSleep * (int) (Math.random() + 1);
    }
}

