package org.example.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class SimulationFrame extends JFrame {
    private JLabel nrClientsLabel;
    private JLabel nrQueuesLabel;
    private JLabel simIntervalLabel;
    private JLabel minArrivalLabel;
    private JLabel maxArrivalLabel;
    private JLabel minServiceLabel;
    private JLabel maxServiceLabel;

    private JTextField nrClientsText;
    private JTextField nrQueuesText;
    private JTextField simIntervalText;
    private JTextField minArrivalText;
    private JTextField maxArrivalText;
    private JTextField minServiceText;
    private JTextField maxServiceText;

    private JLabel strategyLabel;

    private JTextArea queueTextArea;

    private JButton startButton;

    private JPanel panel;
    private JPanel buttonPanel;

    public SimulationFrame() {
        setTitle("Simulation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(800, 700));
        setLocationRelativeTo(null);

        panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        nrClientsLabel = new JLabel("Nr. of clients:");
        nrClientsText = new JTextField();
        panel.add(nrClientsLabel);
        panel.add(nrClientsText);

        nrQueuesLabel = new JLabel("Nr. of queues:");
        nrQueuesText = new JTextField();
        panel.add(nrQueuesLabel);
        panel.add(nrQueuesText);

        simIntervalLabel = new JLabel("Interval:");
        simIntervalText = new JTextField();
        panel.add(simIntervalLabel);
        panel.add(simIntervalText);

        minArrivalLabel = new JLabel("Min arrival:");
        minArrivalText = new JTextField();
        panel.add(minArrivalLabel);
        panel.add(minArrivalText);

        maxArrivalLabel = new JLabel("Max arrival:");
        maxArrivalText = new JTextField();
        panel.add(maxArrivalLabel);
        panel.add(maxArrivalText);

        minServiceLabel = new JLabel("Min service:");
        minServiceText = new JTextField();
        panel.add(minServiceLabel);
        panel.add(minServiceText);

        maxServiceLabel = new JLabel("Max service:");
        maxServiceText = new JTextField();
        panel.add(maxServiceLabel);
        panel.add(maxServiceText);

        // Create the combo box and label for choosing the strategy
        strategyLabel = new JLabel("Strategy:");
        String[] strategies = {"SHORTEST_TIME", "SHORTEST_QUEUE"};
        JComboBox<String> strategyComboBox = new JComboBox<>(strategies);
        panel.add(strategyLabel);
        panel.add(strategyComboBox);

        queueTextArea = new JTextArea();
        queueTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(queueTextArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane, BorderLayout.CENTER);

        startButton = new JButton("Start Simulation");
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttonPanel.add(startButton);

        add(panel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);

        // Get the selected strategy from the combo box
        String selectedStrategy = (String) strategyComboBox.getSelectedItem();

        pack();
        setVisible(true);
    }

    public void addStartButtonListener(ActionListener listenForStartButton) {
        startButton.addActionListener(listenForStartButton);
    }

    public int getNrClients() {
        return Integer.parseInt(nrClientsText.getText());
    }

    public int getNrQueues() {
        return Integer.parseInt(nrQueuesText.getText());
    }

    public int getSimInterval() {
        return Integer.parseInt(simIntervalText.getText());
    }

    public int getMinArrival() {
        return Integer.parseInt(minArrivalText.getText());
    }

    public int getMaxArrival(){
        return Integer.parseInt(maxArrivalText.getText());
    }

    public int getMinService(){
        return Integer.parseInt(minServiceText.getText());
    }

    public int getMaxService(){
        return Integer.parseInt(maxServiceText.getText());
    }

    public static void main(String[] args) {
        new SimulationFrame();
    }

}
