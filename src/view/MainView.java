package view;

import controller.SimulationController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MainView extends JFrame {

    private final SimulationController controller;

    public MainView() {
        controller = new SimulationController(10, 10);
        initUI();
    }

    private void initUI() {
        setTitle("Simula    ción de Restaurante");
        setSize(500, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6,2,10,10));

        JTextField clientsField = new JTextField("5");
        JTextField chefsField = new JTextField("2");
        JTextField waitersField = new JTextField("3");
        JTextField orderQueueLimit = new JTextField("10");
        JTextField deliveryAreaLimit = new JTextField("10");

        panel.add(new JLabel("Número de Clientes:"));
        panel.add(clientsField);
        panel.add(new JLabel("Número de Chefs:"));
        panel.add(chefsField);
        panel.add(new JLabel("Número de Meseros:"));
        panel.add(waitersField);
        panel.add(new JLabel("Límite Cola Pedidos:"));
        panel.add(orderQueueLimit);
        panel.add(new JLabel("Límite Área Entrega:"));
        panel.add(deliveryAreaLimit);

        JButton startButton = new JButton("Iniciar Simulación");
        startButton.addActionListener((ActionEvent e) -> {
            int numClients = Integer.parseInt(clientsField.getText());
            int numChefs = Integer.parseInt(chefsField.getText());
            int numWaiters = Integer.parseInt(waitersField.getText());
            int orderLimit = Integer.parseInt(orderQueueLimit.getText());
            int deliveryLimit = Integer.parseInt(deliveryAreaLimit.getText());

            SimulationView view = new SimulationView(controller);
            controller.setView(view);

            controller.setOrderQueueLimit(orderLimit);
            controller.setDeliveryAreaLimit(deliveryLimit);

            controller.startClients(numClients);
            controller.startChefs(numChefs);
            controller.startWaiters(numWaiters);

            view.updateClients(controller.getClientCount());
            view.updateChefs(controller.getChefCount());
            view.updateWaiters(controller.getWaiterCount());

            JOptionPane.showMessageDialog(this, "Simulación iniciada");
            view.setVisible(true);
            dispose();
        });

        JButton exitButton = new JButton("Salir");
        exitButton.addActionListener((ActionEvent e) -> System.exit(0));

        panel.add(startButton);
        panel.add(exitButton);

        add(panel);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainView::new);
    }
}
