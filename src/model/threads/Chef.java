package model.threads;

import controller.SimulationController;
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
    private final SimulationController controller;

    public Chef(int id, OrderQueue orderQueue, DeliveryArea deliveryArea, SimulationController controller) {
        this.id = id;
        this.orderQueue = orderQueue;
        this.deliveryArea = deliveryArea;
        this.controller = controller;
    }

    Order order = null;

    public void run () {
        try {
            while (running) {
                order = orderQueue.takeOrder();
                controller.onOrderCooking(order.getId());
                Log.print("Chef " + id + " está preparando el pedido " + order.getId() + " del cliente " + order.getClientId());
                Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 10000));
                deliveryArea.addOrder(order);
                controller.onOrderReady(order.getId());
                Log.print("Chef " + id + " terminó el pedido " + order.getId() + " del cliente " + order.getClientId());
            }
            Log.print("Chef " + id + " ha dejado de cocinar.");
        } catch (InterruptedException e) {
            Log.print("Chef " + id + " terminará pedido antes de salir");

            try {
                deliveryArea.addOrder(order);
                controller.onOrderReady(order.getId());
            } catch (InterruptedException _) {
            }
        }
    }

    @Override
    public void stopThread() {
        running = false;
    }

    public int getId() { return id; }

}
