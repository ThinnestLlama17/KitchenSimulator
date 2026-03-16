package view;

import controller.SimulationController;

import javax.swing.*;
import java.awt.*;

public class SimulationView extends JFrame {

    SimulationController controller;

    private JPanel orderQueuePanel;
    private JPanel chefsPanel;
    private JPanel deliveryPanel;
    private JLabel clientsLabel;
    private JLabel chefsLabel;
    private JLabel waitersLabel;

    public SimulationView(SimulationController controller) {
        this.controller = controller;
        initUI();
    }

    public void addOrderToQueue(int orderId) {
        SwingUtilities.invokeLater(() -> {
            JLabel orderLabel = new JLabel("Pedido " + orderId);
            orderLabel.setName("order-" + orderId);
            orderQueuePanel.add(orderLabel);
            orderQueuePanel.revalidate();
            orderQueuePanel.repaint();
        });

    }

    public void moveOrderToChef(int orderId) {
        SwingUtilities.invokeLater(() -> {
            Component[] components = orderQueuePanel.getComponents();
            for (Component c : components) {
                if (c.getName() != null && c.getName().equals("order-" + orderId)) {
                    orderQueuePanel.remove(c);
                    chefsPanel.add(c);
                    break;
                }
            }
            orderQueuePanel.revalidate();
            chefsPanel.revalidate();
            repaint();
        });
    }

    public void moveOrderToDelivery(int orderId) {
        SwingUtilities.invokeLater(() -> {
            Component[] components = chefsPanel.getComponents();
            for (Component c : components) {
                if (c.getName() != null && c.getName().equals("order-" + orderId)) {
                    chefsPanel.remove(c);
                    deliveryPanel.add(c);
                    break;
                }
            }
            chefsPanel.revalidate();
            deliveryPanel.revalidate();
            repaint();
        });
    }

    public void deliverOrder(int orderId) {
        SwingUtilities.invokeLater(() -> {
            Component[] components = deliveryPanel.getComponents();
            for (Component c : components) {
                if (c.getName() != null && c.getName().equals("order-" + orderId)) {
                    deliveryPanel.remove(c);
                    break;
                }
            }
            deliveryPanel.revalidate();
            deliveryPanel.repaint();
        });
    }

    private void initUI() {
        setTitle("Simulación de Restaurante");
        setSize(1000, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());

        topPanel.add(createControlPanel(), BorderLayout.NORTH);
        topPanel.add(createStatusPanel(), BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);
        add(createSimulationPanel(), BorderLayout.CENTER);
        add(createSettingsPanel(), BorderLayout.SOUTH);

        setVisible(true);

    }

    private JPanel createControlPanel() {
        JPanel panel = new JPanel();

        JButton addChef = new JButton("Agregar Chef");
        JButton removeChef = new JButton("Eliminar Chef");

        JButton addWaiter = new JButton("Agregar Mesero");
        JButton removeWaiter = new JButton("Eliminar Mesero");

        JButton addClient = new JButton("Agregar Cliente");
        JButton removeClient = new JButton("Eliminar Cliente");

        addChef.addActionListener(e -> {controller.addChef(); updateChefs(controller.getChefCount());});
        removeChef.addActionListener(e -> {controller.removeChef(); updateChefs(controller.getChefCount());});

        addWaiter.addActionListener(e -> {controller.addWaiter(); updateWaiters(controller.getWaiterCount());});
        removeWaiter.addActionListener(e -> {controller.removeWaiter(); updateWaiters(controller.getWaiterCount());});

        addClient.addActionListener(e -> {controller.addClient(); updateClients(controller.getClientCount());});
        removeClient.addActionListener(e -> {controller.removeClient(); updateClients(controller.getClientCount());});

        panel.add(addChef);
        panel.add(removeChef);
        panel.add(addWaiter);
        panel.add(removeWaiter);
        panel.add(addClient);
        panel.add(removeClient);

        return panel;
    }

    private JPanel createSimulationPanel() {
        JPanel main = new JPanel();
        main.setLayout(new GridLayout(1, 4));

        orderQueuePanel = createSection("Cola de Pedidos");
        chefsPanel = createSection("Chefs");
        deliveryPanel = createSection("Área de Entrega");

        main.add(new JScrollPane(orderQueuePanel));
        main.add(new JScrollPane(chefsPanel));
        main.add(new JScrollPane(deliveryPanel));

        return main;
    }

    private JPanel createSection(String title) {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), title));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        return panel;
    }

    private JPanel createStatusPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        clientsLabel = new JLabel("Clientes: 0");
        chefsLabel = new JLabel("Chefs: 0");
        waitersLabel = new JLabel("Meseros: 0");

        panel.add(clientsLabel);
        panel.add(chefsLabel);
        panel.add(waitersLabel);

        return panel;
    }

    private JPanel createSettingsPanel() {
        JPanel panel = new JPanel();
        JTextField queueLimitField = new JTextField("10",5);
        JButton updateQueue = new JButton("Actualizar Cola");
        JTextField deliveryLimitField = new JTextField("10",5);
        JButton updateDelivery = new JButton("Actualizar Entrega");
        updateQueue.addActionListener(e -> {
            int newLimit = Integer.parseInt(queueLimitField.getText());
            controller.setOrderQueueLimit(newLimit);
        });
        updateDelivery.addActionListener(e -> {
            int newLimit = Integer.parseInt(deliveryLimitField.getText());
            controller.setDeliveryAreaLimit(newLimit);
        });
        JTextField minArrivalField = new JTextField("1000",5);
        JTextField maxArrivalField = new JTextField("5000",5);

        JButton updateArrival = new JButton("Actualizar Llegada");
        updateArrival.addActionListener(e -> {

            int min = Integer.parseInt(minArrivalField.getText());
            int max = Integer.parseInt(maxArrivalField.getText());

            controller.setArrivalRate(min, max);

        });
        panel.add(new JLabel("Límite Cola Pedidos"));
        panel.add(queueLimitField);
        panel.add(updateQueue);
        panel.add(new JLabel("Límite Área Entrega"));
        panel.add(deliveryLimitField);
        panel.add(updateDelivery);
        panel.add(new JLabel("Llegada Clientes (ms)"));
        panel.add(new JLabel("Min:"));
        panel.add(minArrivalField);
        panel.add(new JLabel("Max:"));
        panel.add(maxArrivalField);
        panel.add(updateArrival);
        return panel;
    }

    public void updateClients(int n){
        SwingUtilities.invokeLater(() -> clientsLabel.setText("Clientes: " + n));
    }

    public void updateChefs(int n){
        SwingUtilities.invokeLater(() -> chefsLabel.setText("Chefs: " + n));
    }

    public void updateWaiters(int n){
        SwingUtilities.invokeLater(() -> waitersLabel.setText("Meseros: " + n));
    }

}
