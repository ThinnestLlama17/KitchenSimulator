package controller;

import model.DeliveryArea;
import model.Order;

import java.util.concurrent.ThreadLocalRandom;

public class Waiter implements Runnable{

    private int id;
    private DeliveryArea deliveryArea;

    public Waiter(int id, DeliveryArea deliveryArea) {
        this.id = id;
        this.deliveryArea = deliveryArea;
    }

    public void run () {
        try {
            while (true) {
                System.out.println("Waiter " + id + " está esperando por pedidos listos...");
                Order o = deliveryArea.takeOrder();
                System.out.println("Waiter " + id + " entregó el pedido " + o.getId() + " del cliente " + o.getClientId());
                Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 15000));
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
