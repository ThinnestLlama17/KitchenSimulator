package model;

import util.Log;

import java.util.concurrent.LinkedBlockingQueue;

public class OrderQueue {

    private final LinkedBlockingQueue<Order> queue;
    private int limit;

    public OrderQueue(int initialCapacity) {
        this.limit = initialCapacity;
        this.queue = new LinkedBlockingQueue<>();
    }

    public void addOrder(Order order) throws InterruptedException {
        while (queue.size() >= limit) {
            Thread.sleep(50);
        }
        queue.put(order); // Se bloquea al llenarse, segura para concurrencia
        Log.print("Pedido " + order.getId() + " agregado a la cola");
    }

    public Order takeOrder() throws InterruptedException {
        Order order = queue.take(); // Se bloquea al vaciarse, segura para concurrencia
        Log.print("Pedido " + order.getId() + " retirado de la cola");
        return order;
    }

    public int getSize() {
        return queue.size();
    }

    public synchronized void increaseCapacity(int newLimit) {
        if (newLimit > limit) {
            limit = newLimit;
            Log.print("Limite de cola aumentado a " + limit);
        }
    }

    public synchronized void decreaseCapacity(int newLimit) {
        if (newLimit < limit) {
            limit = newLimit;
            Log.print("Limite de cola ajustado a " + limit);
        }
    }

    public int getLimit() { return limit; }

}
