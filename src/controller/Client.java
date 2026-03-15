package controller;

import model.Order;
import model.OrderQueue;
import util.Log;

import java.util.concurrent.ThreadLocalRandom;

public class Client implements Runnable, Stoppable{

    private final int id;
    private final OrderQueue orderQueue;
    private volatile boolean running = true;

    public Client(int id, OrderQueue orderQueue) {
        this.id = id;
        this.orderQueue = orderQueue;
    }

    @Override
    public void run () {
        try {
            while (running) {
                Order o = new Order(id);
                orderQueue.addOrder(o);
                Log.print("Cliente " + id + " hizo el pedido " + o.getId());
                Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 5000));
            }
            Log.print("Cliente " + id + " ha dejado de hacer pedidos.");
        } catch (InterruptedException e) {
            Log.print("Cliente " + id + " ha sido interrumpido.");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void stopThread() { running = false; }

    public int getId() { return id; }


}
