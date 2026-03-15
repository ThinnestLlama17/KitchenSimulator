package controller;

import model.DeliveryArea;
import model.Order;
import model.OrderQueue;

import java.util.concurrent.ThreadLocalRandom;

public class Chef implements Runnable {

    private int id;
    private OrderQueue orderQueue;
    private DeliveryArea deliveryArea;

    public Chef(int id, OrderQueue orderQueue, DeliveryArea deliveryArea) {
        this.id = id;
        this.orderQueue = orderQueue;
        this.deliveryArea = deliveryArea;
    }

    public void run () {
        try {
            while (true) {
                Order o = orderQueue.takeOrder();
                System.out.println("Chef " + id + " está preparando el pedido " + o.getId() + " del cliente " + o.getClientId());
                Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 10000));
                deliveryArea.addOrder(o);
                System.out.println("Chef " + id + " terminó el pedido " + o.getId() + " del cliente " + o.getClientId());
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
