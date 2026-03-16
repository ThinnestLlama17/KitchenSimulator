package model.threads;

import util.Stoppable;
import model.resources.DeliveryArea;
import model.resources.Order;
import util.Log;

import java.util.concurrent.ThreadLocalRandom;

public class Waiter implements Runnable, Stoppable {

    private final int id;
    private final DeliveryArea deliveryArea;
    private volatile boolean running = true;

    public Waiter(int id, DeliveryArea deliveryArea) {
        this.id = id;
        this.deliveryArea = deliveryArea;
    }

    public void run () {
        try {
            while (running) {
                Log.print("Waiter " + id + " está esperando por pedidos listos...");
                Order o = deliveryArea.takeOrder();
                Log.print("Waiter " + id + " entregó el pedido " + o.getId() + " del cliente " + o.getClientId());
                Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 15000));
            }
            Log.print("Mesero " + id + " ha dejado de trabajar.");
        } catch (InterruptedException e) {
            Log.print("Mesero " + id + " ha sido interrumpido.");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void stopThread() {
        running = false;
    }

    public int getId() { return id; }

}
