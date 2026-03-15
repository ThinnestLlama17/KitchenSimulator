package model;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class OrderQueue {

    private BlockingQueue<Order> queue;

    public OrderQueue(int capacity) {
        this.queue = new ArrayBlockingQueue<>(capacity);
    }

    public void addOrder(Order order) throws InterruptedException {
        queue.put(order); // Se bloquea al llenarse, segura para concurrencia
    }

    public Order takeOrder() throws InterruptedException {
        return queue.take(); // Se bloquea al vaciarse, segura para concurrencia
    }

    public int getSize() {
        return queue.size();
    }

}
