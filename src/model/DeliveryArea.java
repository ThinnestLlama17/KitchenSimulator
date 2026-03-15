package model;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class DeliveryArea {

    private BlockingQueue<Order> deliveryQueue;

    public DeliveryArea(int capacity) {
        this.deliveryQueue = new ArrayBlockingQueue<>(capacity);
    }

    public void addOrder(Order order) throws InterruptedException {
        deliveryQueue.put(order); // Se bloquea al llenarse, segura para concurrencia
    }

    public Order takeOrder() throws InterruptedException {
        return deliveryQueue.take(); // Se bloquea al vaciarse, segura para concurrencia
    }

    public int size() {
        return deliveryQueue.size();
    }

}
