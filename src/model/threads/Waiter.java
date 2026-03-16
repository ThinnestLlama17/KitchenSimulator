package model.threads;

import controller.SimulationController;
import util.Stoppable;
import model.resources.DeliveryArea;
import model.resources.Order;
import util.Log;

import java.util.concurrent.ThreadLocalRandom;

public class Waiter implements Runnable, Stoppable {

    private final int id;
    private final DeliveryArea deliveryArea;
    private volatile boolean running = true;
    private final SimulationController controller;

    public Waiter(int id, DeliveryArea deliveryArea, SimulationController controller) {
        this.id = id;
        this.deliveryArea = deliveryArea;
        this.controller = controller;
    }

    public void run () {
        try {
            while (running) {
                Log.print("Waiter " + id + " está esperando por pedidos listos...");
                Order order = deliveryArea.takeOrder();
                controller.onOrderDelivered(order.getId());
                Log.print("Waiter " + id + " entregó el pedido " + order.getId() + " del cliente " + order.getClientId());
                Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 15000));
            }
            Log.print("Mesero " + id + " ha dejado de trabajar.");
        } catch (InterruptedException e) {
            Log.print("Mesero " + id + " ha sido interrumpido.");
        }
    }

    @Override
    public void stopThread() {
        running = false;
    }

    public int getId() { return id; }

}
