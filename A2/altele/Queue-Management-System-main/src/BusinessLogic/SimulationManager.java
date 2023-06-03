package BusinessLogic;

import GUI.UserInterface;
import Model.Client;
import Model.Queue;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.*;

public class SimulationManager implements Runnable {
    private UserInterface ui;
    private int minArrival, maxArrival, minService, maxService, nrClientsInt, nrQsInt, simIntervalInt;
    private Scheduler scheduler;
    private LinkedList<Client> generatedClients = new LinkedList<>();
    private static float averageWaitTime = 0f;
    private static float avgServiceTime = 0f;
    private int peakTime = 0;
    private int peakNrClients = 0;


    public SimulationManager(UserInterface ui) {
        this.ui = ui;
        String minArr = ui.getMinArr();
        String maxArr = ui.getMaxArr();
        String minSer = ui.getMinSer();
        String maxSer = ui.getMaxSer();
        String nrClients = ui.getNrClients();
        String nrQs = ui.getNrQs();
        String simInterval = ui.getSimInterval();
        try {
            minArrival = Integer.parseInt(minArr);
            maxArrival = Integer.parseInt(maxArr);
            minService = Integer.parseInt(minSer);
            maxService = Integer.parseInt(maxSer);
            nrClientsInt = Integer.parseInt(nrClients);
            nrQsInt = Integer.parseInt(nrQs);
            simIntervalInt = Integer.parseInt(simInterval);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid data",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        scheduler = new Scheduler(nrQsInt);
        generateRandomClients(nrClientsInt, minArrival, maxArrival, minService, maxService);
    }

    public static void addAverageWaitTime(int averageWaitingTime) {
        averageWaitTime += averageWaitingTime;
    }

    public static void addAverageServiceTime(int averageServiceTime) {
        avgServiceTime += averageServiceTime;
    }

    public void peakHour(ArrayList<Queue> queueList, int time) {
        int peakNrClients = 0;
        for (Queue q : queueList) {
            peakNrClients+=q.getQueueLength();
        }
        if(peakNrClients>this.peakNrClients) {
            this.peakTime = time;
            this.peakNrClients = peakNrClients;
        }
    }

    public void print(ArrayList<Queue> queueList, int time) {
        ui.updateLog("==========================================================================================\n");
        ui.updateLog("Time: " + time +"\n");
        ui.updateLog("Waiting: ");
        for (Client c : generatedClients) {
            ui.updateLog("(" +c.getId() + "," + c.getArrivalTime() + "," + c.getServiceTime() + ")  ");
        }
        ui.updateLog("\n");
        for (Queue q : queueList) {
            Client[] c = q.getClients();
            if (c.length == 0)
                ui.updateLog("Queue " + q.getQueueId() + ": empty"+"\n");
            else {
                ui.updateLog("Queue " + q.getQueueId() + ": ");
                for (Client client : c) {
                    ui.updateLog("(" + client.getId() + "," + client.getArrivalTime()
                            + "," + client.getServiceTime() + ")  ");
                }
                ui.updateLog("\n");
            }
        }
    }

    public void generateRandomClients(int nrClients, int minArrival, int maxArrival, int minService, int maxService) {

        for (int i = 0; i < nrClients; i++) {
            int getRandomArrival = (int) (Math.random() * (maxArrival - minArrival + 1) + minArrival);
            int getRandomService = (int) (Math.random() * (maxService - minService + 1) + minService);
            Client client = new Client(i + 1, getRandomArrival, getRandomService);
            generatedClients.add(client);
        }
        Collections.sort(generatedClients, new Comparator<Client>() {
            @Override
            public int compare(Client o1, Client o2) {
                return Integer.compare(o1.getArrivalTime(), o2.getArrivalTime());
            }
        });
    }


    public boolean isRunning(List<Queue> queueList) {
        if (generatedClients.size() != 0) {
            return true;
        } else {
            for (Queue q : queueList) {
                if (q.getQueueLength() != 0) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void run() {
        int time = 0;
        ArrayList<Queue> queueList = scheduler.getQueues();
        while (isRunning(queueList) && time<simIntervalInt) {
            ui.updateTime(time);
            Client client = generatedClients.peekFirst();
            while (client != null && client.getArrivalTime() <= time) {
                if (scheduler.addClient(client)) {
                    addAverageServiceTime(client.getServiceTime());
                    generatedClients.remove(0);
                } else
                    break;
                client = generatedClients.peekFirst();
            }
            peakHour(queueList, time);
            print(queueList, time);
            time++;
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        ui.updateTime(time);
        print(queueList, time);
        ui.updateLog("=====================================RESULTS==============================================\n");
        averageWaitTime = averageWaitTime / (float) nrClientsInt;
        avgServiceTime = avgServiceTime / (float) nrClientsInt;
        ui.updateLog("Average wait time: " + averageWaitTime);
        ui.updateLog("\nAverage service time: " + avgServiceTime);
        ui.updateLog("\nPeak hour: " + peakTime + " with " + peakNrClients + " clients");
        for (Queue q : queueList) {
            q.stop();
        }
    }

    public static void main(String[] args) {
        UserInterface ui = new UserInterface();
        ui.setVisible(true);
        ui.validateListener(
                (ActionEvent e) -> {
                    String minArr = ui.getMinArr();
                    String maxArr = ui.getMaxArr();
                    String minSer = ui.getMinSer();
                    String maxSer = ui.getMaxSer();
                    String nrClients = ui.getNrClients();
                    String nrQs = ui.getNrQs();
                    String simInterval = ui.getSimInterval();
                    int minArrival, maxArrival, minService, maxService, nrClientsInt, nrQsInt, simIntervalInt;
                    try {
                        minArrival = Integer.parseInt(minArr);
                        maxArrival = Integer.parseInt(maxArr);
                        minService = Integer.parseInt(minSer);
                        maxService = Integer.parseInt(maxSer);
                        nrClientsInt = Integer.parseInt(nrClients);
                        nrQsInt = Integer.parseInt(nrQs);
                        simIntervalInt = Integer.parseInt(simInterval);
                        if (minArrival > maxArrival || minService > maxService || nrClientsInt <= 0 || nrQsInt <= 0
                                || simIntervalInt <= 0 || minArrival < 0 || maxArrival < 0 || minService < 0 || maxService < 0) {
                            JOptionPane.showMessageDialog(null, "Invalid data",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, "Good Data!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        }
                    } catch (NumberFormatException er) {
                        JOptionPane.showMessageDialog(null, "Invalid data",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
        );
        ui.startListener((ActionEvent e) -> {
            SimulationManager simulationManager = new SimulationManager(ui);
            Thread t = new Thread(simulationManager);
            t.start();
        });
    }
}
