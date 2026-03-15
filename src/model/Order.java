package model;

public class Order {

    private static int globalCounter = 0;
    private final int id;
    private final int clientId;

    public Order(int clientId) {
        this.id = ++globalCounter;
        this.clientId = clientId;
    }

    public int getId() {return id;}
    public int getClientId() {return clientId;}

}
