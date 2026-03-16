package model.threads;

import controller.SimulationController;
import util.Stoppable;
import model.resources.Order;
import model.resources.OrderQueue;
import util.Log;

import java.util.concurrent.ThreadLocalRandom;

public class Client implements Runnable, Stoppable {

    private final int id;
    private final OrderQueue orderQueue;
    private volatile boolean running = true;
    private final SimulationController controller;

    public Client(int id, OrderQueue orderQueue, SimulationController controller) {
        this.id = id;
        this.orderQueue = orderQueue;
        this.controller = controller;
    }

    @Override
    public void run () {
        try {
            while (running) {
                Order order = new Order(id);
                orderQueue.addOrder(order);
                controller.onOrderCreated(order.getId());
                Log.print("Cliente " + id + " hizo el pedido " + order.getId());
                Thread.sleep(ThreadLocalRandom.current().nextInt(controller.getMinArrival(), controller.getMaxArrival()));
            }
            Log.print("Cliente " + id + " ha dejado de hacer pedidos.");
        } catch (InterruptedException e) {
            Log.print("Cliente " + id + " ha sido interrumpido.");
        }
    }

    @Override
    public void stopThread() { running = false; }

    public int getId() { return id; }


}
