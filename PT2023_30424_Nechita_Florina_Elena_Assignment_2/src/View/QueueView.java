package View;

import Controller.SimulationManager;
import Main.Exceptions.ExceptionEmptyField;
import Main.Exceptions.ExceptionMinHigherThanMax;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class QueueView extends JFrame {
    private JScrollPane scrollPane;
    public JTextArea logText;
    private JLabel logTextLabel;
    private JButton start;
    private JSpinner simulationTime;
    private JLabel simulationTimeLabel;
    private JSpinner nrClients;
    private JLabel nrClientsLabel;
    private JSpinner nrQueues;
    private JLabel nrQueuesLabel;
    private JSpinner minArrival;
    private JLabel minArrivalLabel;
    private JSpinner maxArrival;
    private JLabel maxArrivalLabel;
    private JSpinner minService;
    private JLabel minServiceLabel;
    private JSpinner maxService;
    private JLabel maxServiceLabel;
    private JComboBox strategy;

    public QueueView() {
        this.setSize(1000, 750);
        this.setBackground(new Color(200, 200, 255));
        this.getContentPane().setLayout(null);

        strategy = new JComboBox();
        strategy.addItem("Time Strategy");
        strategy.addItem("Shortest Queue Strategy");
        strategy.setBounds(100, 650, 200, 50);
        this.add(strategy);

        logText = new JTextArea("");
        logText.setBounds(500, 50, 450, 550);
        logText.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        logText.setBackground(new Color(200, 200, 255));
        scrollPane = new JScrollPane(logText);
        scrollPane.setBounds(500, 50, 450, 550);
        this.add(scrollPane);

        logTextLabel = new JLabel("Simulation Logs");
        logTextLabel.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        logTextLabel.setBounds(500, 0, 500, 50);
        this.add(logTextLabel);

        simulationTime = new JSpinner();
        simulationTime.setBounds(275, 50, 50, 50);
        this.add(simulationTime);
        simulationTimeLabel = new JLabel("SIMULATION TIME");
        simulationTimeLabel.setBounds(50, 50, 200, 50);
        simulationTimeLabel.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        this.add(simulationTimeLabel);

        nrClients = new JSpinner();
        nrClients.setBounds(275, 125, 50, 50);
        this.add(nrClients);
        nrClientsLabel = new JLabel("NUMBER OF CLIENTS");
        nrClientsLabel.setBounds(50, 125, 200, 50);
        nrClientsLabel.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        this.add(nrClientsLabel);

        nrQueues = new JSpinner();
        nrQueues.setBounds(275, 200, 50, 50);
        this.add(nrQueues);
        nrQueuesLabel = new JLabel("NUMBER OF QUEUES");
        nrQueuesLabel.setBounds(50, 200, 200, 50);
        nrQueuesLabel.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        this.add(nrQueuesLabel);

        minArrival = new JSpinner();
        minArrival.setBounds(275, 275, 50, 50);
        this.add(minArrival);
        minArrivalLabel = new JLabel("MIN ARRIVAL TIME");
        minArrivalLabel.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        minArrivalLabel.setBounds(50, 275, 200, 50);
        this.add(minArrivalLabel);

        maxArrival = new JSpinner();
        maxArrival.setBounds(275, 350, 50, 50);
        this.add(maxArrival);
        maxArrivalLabel = new JLabel("MAX ARRIVAL TIME");
        maxArrivalLabel.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        maxArrivalLabel.setBounds(50, 350, 200, 50);
        this.add(maxArrivalLabel);


        minService = new JSpinner();
        minService.setBounds(275, 425, 50, 50);
        this.add(minService);
        minServiceLabel = new JLabel("MIN SERVICE TIME");
        minServiceLabel.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        minServiceLabel.setBounds(50, 425, 200, 50);
        this.add(minServiceLabel);


        maxService = new JSpinner();
        maxService.setBounds(275, 500, 50, 50);
        this.add(maxService);
        maxServiceLabel = new JLabel("MAX SERVICE TIME");
        maxServiceLabel.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        maxServiceLabel.setBounds(50, 500, 200, 50);
        this.add(maxServiceLabel);


        start = new JButton("START");
        start.setBounds(350, 250, 125, 50);
        start.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                this.action(e, QueueView.this);
            }

            public void action(ActionEvent e, QueueView queueView) {
                SimulationManager sim = new SimulationManager(queueView);
                sim = new SimulationManager(queueView);
                ArrayList<Integer> inputs = new ArrayList<>();
                inputs.add((int) nrClients.getValue());
                inputs.add((int) nrQueues.getValue());
                inputs.add((int) simulationTime.getValue());
                inputs.add((int) minArrival.getValue());
                inputs.add((int) maxArrival.getValue());
                inputs.add((int) minService.getValue());
                inputs.add((int) maxService.getValue());
                sim.setValues(inputs, strategy.getSelectedItem().toString());
                try {
                    if (inputs.get(0) == 0 || inputs.get(1) == 0 || inputs.get(2) == 0 || inputs.get(4) == 0 || inputs.get(5) == 0 || inputs.get(6) == 0) {
                        throw new ExceptionEmptyField("Empty Field!");
                    }
                    if (inputs.get(3) > inputs.get(4) || inputs.get(5) > inputs.get(6)) {
                        throw new ExceptionMinHigherThanMax("Min is higher!");
                    }
                    if (sim.getNbClients() != 0) {
                        Thread thread = new Thread(sim);
                        thread.start();
                    }
                } catch (ExceptionEmptyField ex) {
                    JOptionPane.showMessageDialog(new JFrame(), "EMPTY FIELD!", "ERROR!", JOptionPane.WARNING_MESSAGE);
                } catch (ExceptionMinHigherThanMax ex) {
                    JOptionPane.showMessageDialog(new JFrame(), "MIN FIELD HAS TO BE LOWER THAN MAX FIELD!", "ERROR!", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        this.add(start);


        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.setVisible(true);
    }
}
