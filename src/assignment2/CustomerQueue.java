package assignment2;

import assignment2.models.Customer;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * This class implements a queue of customers as a circular buffer.
 */
public class CustomerQueue {
    private LinkedBlockingQueue<Customer> queue;
    private Gui gui;
    private int queueSize;
    private int firstFreeSeat;

	/**
	 * Creates a new customer queue.
	 * @param queueLength	The maximum length of the queue.
	 * @param gui			A reference to the GUI interface.
	 */
    public CustomerQueue(int queueLength, Gui gui) {
        queue = new LinkedBlockingQueue<Customer>(queueLength);
        queueSize = queueLength;
        this.gui = gui;
        firstFreeSeat = 0;
	}

    public synchronized void addCustomerToQueue(Customer customer){
        while (isFull()){
            try {
                gui.println("Full customer queue");
                wait();
                gui.println("Available slot in customer queue");
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }

        try {
            gui.fillLoungeChair(firstFreeSeat, customer);
            firstFreeSeat++;
            checkFreeSeatPosition();
            queue.put(customer);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

        notify();
    }

    /**
     * Check that freeSeatPosition is a valid position.
     */
    private void checkFreeSeatPosition() {
        if (firstFreeSeat >= queueSize)
            firstFreeSeat = 0;
    }

    /**
     * @return Fetch next customer in queue. If the queue is empty, wait until it has new elements.
     */
    public synchronized Customer getNextCustomer(){
        while (isEmpty()){
            try {
                gui.println("Empty customer queue");
                wait();
                gui.println("Customer in queue");
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }

        int nextCustomerPosition = getNextCustomerPosition();
        gui.emptyLoungeChair(nextCustomerPosition);
        Customer customer = queue.poll();
        notify();
        return customer;
    }

    /**
     * Get position of next customer in queue
     */
    private int getNextCustomerPosition(){
        int nextCustomerPosition = firstFreeSeat - queue.size();
        if (nextCustomerPosition < 0)
            nextCustomerPosition += queueSize;

        return nextCustomerPosition;
    }

    /**
     * @return Returns true if the customer queue is full, false otherwise
     */
    public boolean isFull(){
        return queue.size() == queueSize;
    }

    /**
     * @return Returns true if the customer queue is empty, false otherwise
     */
    public boolean isEmpty(){
        return queue.isEmpty();
    }
}
