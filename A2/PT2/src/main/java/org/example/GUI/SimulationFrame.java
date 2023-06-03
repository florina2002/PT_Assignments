package org.example.GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimulationFrame extends JFrame {

    private JTextField minArrTextField;

    private JButton clearButton;
    private JTextField minSerTextField;
    private JPanel main;
    private JButton startButton;
    private JLabel timeLabel;
    private JTextArea logsArea;
    private JTextField nrQsTextField;
    private JTextField maxArrTextField;
    private JTextField simIntervalTextField;
    private JScrollPane logsPane;
    private JTextField maxSerTextField;

    private JTextField nrClientsTextField;


    JScrollBar vertical = logsPane.getVerticalScrollBar();

    public SimulationFrame() {
        super("Simulation");
        setContentPane(main);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setMinimumSize(new java.awt.Dimension(800, 700));


        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logsArea.setText("");
            }
        });
    }

    public void startListener(ActionListener start) {
        startButton.addActionListener(start);
    }

    public void updateLog(String log) {
        logsArea.append(log);
        vertical.setValue(vertical.getMaximum());
    }

    public String getMinArr() {
        return minArrTextField.getText();
    }

    public String getMaxArr() {

        return maxArrTextField.getText();
    }

    public String getMinSer() {

        return minSerTextField.getText();
    }

    public String getMaxSer() {

        return maxSerTextField.getText();
    }

    public String getNrClients() {

        return nrClientsTextField.getText();
    }

    public String getNrQs() {

        return nrQsTextField.getText();
    }

    public String getSimInterval() {

        return simIntervalTextField.getText();
    }

    public void currentTimeUpdate(int time) {

        timeLabel.setText("Time: " + time);
    }


}