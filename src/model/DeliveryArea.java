package model;

import util.Log;

import java.util.concurrent.LinkedBlockingQueue;

public class DeliveryArea {

    private final LinkedBlockingQueue<Order> deliveryQueue;
    private int limit;

    public DeliveryArea(int initialCapacity) {
        this.limit = initialCapacity;
        this.deliveryQueue = new LinkedBlockingQueue<>();
    }

    public void addOrder(Order order) throws InterruptedException {
        while (deliveryQueue.size() >= limit) {
            Thread.sleep(50);
        }
        deliveryQueue.put(order); // Se bloquea al llenarse, segura para concurrencia
        Log.print("Pedido " + order.getId() + " listo para entregar");
    }

    public Order takeOrder() throws InterruptedException {
        Order order = deliveryQueue.take(); // Se bloquea al vaciarse, segura para concurrencia
        Log.print("Pedido " + order.getId() + " retirado del área de entrega");
        return order;
    }

    public int size() {
        return deliveryQueue.size();
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
