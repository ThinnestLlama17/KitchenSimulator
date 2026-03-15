package controller;

import model.Order;
import model.OrderQueue;

import java.util.concurrent.ThreadLocalRandom;

public class Client implements Runnable{

    private int id;
    private OrderQueue orderQueue;

    public Client(int id, OrderQueue orderQueue) {
        this.id = id;
        this.orderQueue = orderQueue;
    }

    public void run () {
        try {
            while (true) {
                Order o = new Order(id);
                orderQueue.addOrder(o);
                System.out.println("Cliente " + id + " hizo el pedido " + o.getId());
                Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 5000));
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
