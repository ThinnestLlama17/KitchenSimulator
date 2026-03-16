package model.resources;

import util.Log;

import java.util.concurrent.LinkedBlockingQueue;

public class OrderQueue {

    private final LinkedBlockingQueue<Order> queue;
    private int limit;

    public OrderQueue(int initialCapacity) {
        this.limit = initialCapacity;
        this.queue = new LinkedBlockingQueue<>();
    }

    public synchronized void addOrder(Order order) throws InterruptedException {
        while (queue.size() >= limit) {
            wait();
        }
        queue.add(order); // Se bloquea al llenarse, segura para concurrencia
        Log.print("Pedido " + order.getId() + " agregado a la cola");
        notifyAll();
    }

    public synchronized Order takeOrder() throws InterruptedException {
        while (queue.isEmpty()) {
            wait();
        }
        Order order = queue.poll(); // Se bloquea al vaciarse, segura para concurrencia
        Log.print("Pedido " + order.getId() + " retirado de la cola");
        notifyAll();
        return order;
    }

    public int getSize() {
        return queue.size();
    }

    public synchronized void setCapacity(int newLimit) {
            limit = newLimit;
            Log.print("Limite de cola puesto en " + limit);
            notifyAll();
    }

    public int getLimit() { return limit; }

}
