package controller;

import model.DeliveryArea;
import model.OrderQueue;
import util.Log;

import java.util.ArrayList;
import java.util.List;

public class SimulationController {

    private final List<Stoppable> clients = new ArrayList<>();
    private final List<Thread> clientThreads = new ArrayList<>();

    private final List<Stoppable> chefs = new ArrayList<>();
    private final List<Thread> chefThreads = new ArrayList<>();

    private final List<Stoppable> waiters = new ArrayList<>();
    private final List<Thread> waiterThreads = new ArrayList<>();

    private final OrderQueue orderQueue;
    private final DeliveryArea deliveryArea;

    public SimulationController(int orderQueueCapacity, int deliveryAreaCapacity) {
        orderQueue = new OrderQueue(orderQueueCapacity);
        deliveryArea = new DeliveryArea(deliveryAreaCapacity);
    }

    public void startClients(int num) { for(int i=0;i<num;i++) addClient(); }
    public void startChefs(int num) { for(int i=0;i<num;i++) addChef(); }
    public void startWaiters(int num) { for(int i=0;i<num;i++) addWaiter(); }


    public void addClient() {
        Client client = new Client(clients.size() + 1, orderQueue);
        startThread(client, clients, clientThreads, "Client-" + client.getId());
    }

    public void addChef() {
        Chef chef = new Chef(chefs.size() + 1, orderQueue, deliveryArea);
        startThread(chef, chefs, chefThreads, "Chef-" + chef.getId());
    }

    public void addWaiter() {
        Waiter waiter = new Waiter(waiters.size() + 1, deliveryArea);
        startThread(waiter, waiters, waiterThreads, "Waiter-" + waiter.getId());
    }

    private void startThread(Stoppable obj, List<Stoppable> list, List<Thread> threads, String name) {
        Thread thread = new Thread((Runnable) obj, name);
        list.add(obj);
        threads.add(thread);
        thread.start();
    }

    public void removeClient() { stopThread(clients, clientThreads, "Cliente"); }
    public void removeChef() { stopThread(chefs, chefThreads, "Chef"); }
    public void removeWaiter() { stopThread(waiters, waiterThreads, "Mesero"); }

    private void stopThread(List<Stoppable> list, List<Thread> threads, String type) {
        if (!list.isEmpty()) {
            Stoppable obj = list.remove(0);
            Thread thread = threads.remove(0);
            Log.print("🔹 Deteniendo " + type + " " + thread.getName());
            obj.stopThread();
            thread.interrupt();
        }
    }

    public void increaseOrderQueueLimit(int amount) { orderQueue.increaseCapacity(amount); }
    public void decreaseOrderQueueLimit(int amount) { orderQueue.decreaseCapacity(amount); }
    public void increaseDeliveryAreaLimit(int amount) { deliveryArea.increaseCapacity(amount); }
    public void decreaseDeliveryAreaLimit(int amount) { deliveryArea.decreaseCapacity(amount); }

}
