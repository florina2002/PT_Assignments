package GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserInterface extends JFrame {
    private JPanel main;
    private JTextField nrClientsTextField;
    private JTextField nrQsTextField;
    private JTextField minArrTextField;
    private JTextField maxArrTextField;
    private JTextField minSerTextField;
    private JTextField maxSerTextField;
    private JTextField simIntervalTextField;
    private JComboBox comboBox1;
    private JLabel testsComboBox;
    private JButton startButton;
    private JLabel timeLabel;
    private JTextArea logsArea;
    private JScrollPane logsPane;
    private JButton validateButton;
    private JButton clearButton;
    JScrollBar vertical = logsPane.getVerticalScrollBar();

    public UserInterface() {
        super("Simulation");
        setContentPane(main);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);


        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (comboBox1.getSelectedItem().equals("Test 1")) {
                    nrClientsTextField.setText("4");
                    nrQsTextField.setText("2");
                    minArrTextField.setText("2");
                    maxArrTextField.setText("30");
                    minSerTextField.setText("2");
                    maxSerTextField.setText("4");
                    simIntervalTextField.setText("60");
                } else if (comboBox1.getSelectedItem().equals("Test 2")) {
                    nrClientsTextField.setText("50");
                    nrQsTextField.setText("5");
                    minArrTextField.setText("2");
                    maxArrTextField.setText("40");
                    minSerTextField.setText("1");
                    maxSerTextField.setText("7");
                    simIntervalTextField.setText("60");
                } else if (comboBox1.getSelectedItem().equals("Test 3")) {
                    nrClientsTextField.setText("1000");
                    nrQsTextField.setText("20");
                    minArrTextField.setText("10");
                    maxArrTextField.setText("100");
                    minSerTextField.setText("3");
                    maxSerTextField.setText("9");
                    simIntervalTextField.setText("200");
                } else if (comboBox1.getSelectedItem().equals("Manual")) {
                    nrClientsTextField.setText("");
                    nrQsTextField.setText("");
                    minArrTextField.setText("");
                    maxArrTextField.setText("");
                    minSerTextField.setText("");
                    maxSerTextField.setText("");
                    simIntervalTextField.setText("");
                }
            }
        });
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logsArea.setText("");
            }
        });
    }

    public JTextArea getLogsArea() {
        return logsArea;
    }

    public void setLogsArea(JTextArea logsArea) {
        this.logsArea = logsArea;
    }

    public JScrollPane getLogsPane() {
        return logsPane;
    }

    public void setLogsPane(JScrollPane logsPane) {
        this.logsPane = logsPane;
    }

    public String getNrClients() {
        return nrClientsTextField.getText();
    }

    public String getNrQs() {
        return nrQsTextField.getText();
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

    public String getSimInterval() {
        return simIntervalTextField.getText();
    }

    public void updateTime(int time) {
        timeLabel.setText("Time: " + time);
    }

    public JButton getStartButton() {
        return startButton;
    }

    public void validateListener(ActionListener validate) {
        validateButton.addActionListener(validate);
    }

    public void startListener(ActionListener start) {
        startButton.addActionListener(start);
    }

    public void updateLog(String log) {
        logsArea.append(log);
        vertical.setValue(vertical.getMaximum());
    }
}
