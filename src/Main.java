//import controller.SimulationController;
//import util.Log;
//
//
//public class Main {
//
//    public static void main(String[] args) {
//
//        int numClients = 5;
//        int numWaiters = 3;
//        int numChefs = 2;
//        int orderQueueCapacity = 10;
//        int deliveryAreaCapacity = 10;
//
//        SimulationController sim = new SimulationController(orderQueueCapacity, deliveryAreaCapacity);
//        sim.startClients(numClients);
//        sim.startChefs(numChefs);
//        sim.startWaiters(numWaiters);
//
//        new Thread(() -> {
//            try {
//                Thread.sleep(10000);
//                Log.print("Poniento el limite de pedidos en 5");
//                sim.setOrderQueueLimit(5);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }, "Increase-OrderQueue").start();
//
//        new Thread(() -> {
//            try {
//                Thread.sleep(15000);
//                Log.print("Agregando un cliente extra");
//                sim.addClient();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }, "Add-Client").start();
//
//        new Thread(() -> {
//            try {
//                Thread.sleep(20000);
//                Log.print("Quitando un mesero");
//                sim.removeWaiter();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }, "Remove-Waiter").start();
//
//        new Thread(() -> {
//            try {
//                Thread.sleep(25000);
//                Log.print("Poniendo el limite de area de entrega en 3");
//                sim.setDeliveryAreaLimit(3);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }, "Decrease-DeliveryArea").start();
//    }
//
//}
