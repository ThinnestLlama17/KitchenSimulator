package controller;

import model.resources.DeliveryArea;
import model.resources.OrderQueue;
import model.threads.Chef;
import model.threads.Client;
import model.threads.Waiter;
import util.Log;
import util.Stoppable;

import java.util.ArrayList;
import java.util.List;

import view.SimulationView;

public class SimulationController {

    SimulationView view;

    private final List<Stoppable> clients = new ArrayList<>();
    private final List<Thread> clientThreads = new ArrayList<>();

    private final List<Stoppable> chefs = new ArrayList<>();
    private final List<Thread> chefThreads = new ArrayList<>();

    private final List<Stoppable> waiters = new ArrayList<>();
    private final List<Thread> waiterThreads = new ArrayList<>();

    private final OrderQueue orderQueue;
    private final DeliveryArea deliveryArea;

    private volatile int minArrival = 1000;
    private volatile int maxArrival = 5000;

    public SimulationController(int orderQueueCapacity, int deliveryAreaCapacity) {
        orderQueue = new OrderQueue(orderQueueCapacity);
        deliveryArea = new DeliveryArea(deliveryAreaCapacity);
    }

    public void startClients(int num) { for(int i=0;i<num;i++) addClient(); }
    public void startChefs(int num) { for(int i=0;i<num;i++) addChef(); }
    public void startWaiters(int num) { for(int i=0;i<num;i++) addWaiter(); }


    public void addClient() {
        Client client = new Client(clients.size() + 1, orderQueue, this);
        startThread(client, clients, clientThreads, "Client-" + client.getId());
    }

    public void addChef() {
        Chef chef = new Chef(chefs.size() + 1, orderQueue, deliveryArea, this);
        startThread(chef, chefs, chefThreads, "Chef-" + chef.getId());
    }

    public void addWaiter() {
        Waiter waiter = new Waiter(waiters.size() + 1, deliveryArea, this);
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
            Log.print("Deteniendo " + type + " " + thread.getName());
            obj.stopThread();
            thread.interrupt();
        }
    }

    public void setOrderQueueLimit(int amount) { orderQueue.setCapacity(amount); }
    public void setDeliveryAreaLimit(int amount) { deliveryArea.setCapacity(amount); }

    public void setView(SimulationView view) { this.view = view; }

    public void onOrderCreated(int orderId) { if(view != null) { view.addOrderToQueue(orderId); } }

    public void onOrderCooking(int orderId) { if(view != null) { view.moveOrderToChef(orderId); } }

    public void onOrderReady(int orderId) { if(view != null) { view.moveOrderToDelivery(orderId); } }

    public void onOrderDelivered(int orderId) { if(view != null) { view.deliverOrder(orderId); } }

    public int getClientCount() {
        return clients.size();
    }

    public int getChefCount() {
        return chefs.size();
    }

    public int getWaiterCount() {
        return waiters.size();
    }

    public int getMinArrival() { return minArrival; }
    public int getMaxArrival() { return maxArrival; }

    public void setArrivalRate(int min, int max) {
        minArrival = min;
        maxArrival = max;
    }

}
