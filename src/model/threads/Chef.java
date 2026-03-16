package model.threads;

import util.Stoppable;
import model.resources.DeliveryArea;
import model.resources.Order;
import model.resources.OrderQueue;
import util.Log;

import java.util.concurrent.ThreadLocalRandom;

public class Chef implements Runnable, Stoppable {

    private final int id;
    private final OrderQueue orderQueue;
    private final DeliveryArea deliveryArea;
    private volatile boolean running = true;

    public Chef(int id, OrderQueue orderQueue, DeliveryArea deliveryArea) {
        this.id = id;
        this.orderQueue = orderQueue;
        this.deliveryArea = deliveryArea;
    }

    public void run () {
        try {
            while (running) {
                Order o = orderQueue.takeOrder();
                Log.print("Chef " + id + " está preparando el pedido " + o.getId() + " del cliente " + o.getClientId());
                Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 10000));
                deliveryArea.addOrder(o);
                Log.print("Chef " + id + " terminó el pedido " + o.getId() + " del cliente " + o.getClientId());
            }
            Log.print("Chef " + id + " ha dejado de cocinar.");
        } catch (InterruptedException e) {
            Log.print("Chef " + id + " ha sido interrumpido.");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void stopThread() {
        running = false;
    }

    public int getId() { return id; }

}
