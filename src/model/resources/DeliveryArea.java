package model.resources;

import util.Log;

import java.util.concurrent.LinkedBlockingQueue;

public class DeliveryArea {

    private final LinkedBlockingQueue<Order> deliveryQueue;
    private int limit;

    public DeliveryArea(int initialCapacity) {
        this.limit = initialCapacity;
        this.deliveryQueue = new LinkedBlockingQueue<>();
    }

    public synchronized void addOrder(Order order) throws InterruptedException {
        while (deliveryQueue.size() >= limit) {
            wait();
        }
        deliveryQueue.add(order); // Se bloquea al llenarse, segura para concurrencia
        Log.print("Pedido " + order.getId() + " listo para entregar");
        notifyAll();
    }

    public synchronized Order takeOrder() throws InterruptedException {
        while (deliveryQueue.isEmpty()) {
            wait();
        }
        Order order = deliveryQueue.poll(); // Se bloquea al vaciarse, segura para concurrencia
        Log.print("Pedido " + order.getId() + " retirado del área de entrega");
        notifyAll();
        return order;
    }

    public int size() {
        return deliveryQueue.size();
    }

    public synchronized void setCapacity(int newLimit) {
        limit = newLimit;
        Log.print("Limite de cola aumentado a " + limit);
        notifyAll();
    }

    public int getLimit() { return limit; }

}
