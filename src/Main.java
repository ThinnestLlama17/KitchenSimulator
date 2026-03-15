import controller.SimulationController;


public class Main {

    public static void main(String[] args) {

        int numClients = 5;
        int numWaiters = 3;
        int numChefs = 2;
        int orderQueueCapacity = 10;
        int deliveryAreaCapacity = 10;

        SimulationController sim = new SimulationController(orderQueueCapacity, deliveryAreaCapacity);
        sim.startClients(numClients);
        sim.startChefs(numChefs);
        sim.startWaiters(numWaiters);


    }

}
