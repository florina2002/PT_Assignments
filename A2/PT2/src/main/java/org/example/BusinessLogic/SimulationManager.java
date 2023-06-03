package org.example.BusinessLogic;

import org.example.GUI.SimulationFrame;
import org.example.Model.Server;
import org.example.Model.Task;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class SimulationManager implements Runnable {

    private static File log;
    private SimulationFrame simulationFrame;
    private int minArrival;
    private int maxArrival;
    private int minService;
    private int maxService;
    private int noOfTasks;
    private int noOfServers;
    private int simulationInterval;
    private static float averageWaitTime = 0f;
    private static float avgServiceTime = 0f;
    private Scheduler scheduler;
    private LinkedList<Task> generatedTasks = new LinkedList<>();

    private int peakTime = 0;
    private int peakNrClients = 0;

    FileWriter myWriter;

    public SimulationManager(SimulationFrame interfaceText) {
        this.simulationFrame = interfaceText;;
        String minArr = interfaceText.getMinArr();
        String nrClients = interfaceText.getNrClients();
        String nrQs = interfaceText.getNrQs();
        String maxArr = interfaceText.getMaxArr();
        String minSer = interfaceText.getMinSer();
        String maxSer = interfaceText.getMaxSer();


        String simInterval = interfaceText.getSimInterval();
        try {
            minArrival = Integer.parseInt(minArr);
            maxArrival = Integer.parseInt(maxArr);
            minService = Integer.parseInt(minSer);
            maxService = Integer.parseInt(maxSer);
            noOfTasks = Integer.parseInt(nrClients);
            noOfServers = Integer.parseInt(nrQs);
            simulationInterval = Integer.parseInt(simInterval);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ERROR: Invalid input!",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        scheduler = new Scheduler(noOfServers);
        generateRandomClients(noOfTasks, minArrival, maxArrival, minService, maxService);
    }

    public static void addAverageWaitTime(int averageWaitingTime) {

        averageWaitTime += averageWaitingTime;
    }

    public static void addAverageServiceTime(int averageServiceTime) {

        avgServiceTime += averageServiceTime;
    }

    public void peakHour(ArrayList<Server> serverList, int time) {
        int peakNrClients = 0;
        for (Server q : serverList) {
            peakNrClients+=q.serverLenght();
        }
        if(peakNrClients>this.peakNrClients) {
            this.peakTime = time;
            this.peakNrClients = peakNrClients;
        }
    }

    public void print(ArrayList<Server> serverList, int time) {
        simulationFrame.updateLog("\n\n\n\n");
        simulationFrame.updateLog("Time: " + time +"\n");
        simulationFrame.updateLog("Waiting: ");
        for (Task c : generatedTasks) {
            simulationFrame.updateLog("(" +c.getID() + "," + c.getArrivalTime() + "," + c.getServiceTime() + ")  ");

        }
        simulationFrame.updateLog("\n");
        for (Server q : serverList) {
            Task[] c = q.getTasks();
            if (c.length == 0) {
                simulationFrame.updateLog("Server " + q.getQueueId() + ": empty" + "\n");
            }


            else {
                simulationFrame.updateLog("Server " + q.getQueueId() + ": ");
                for (Task task : c) {
                    simulationFrame.updateLog("(" + task.getID() + "," + task.getArrivalTime()
                            + "," + task.getServiceTime() + ")  ");


                }
                simulationFrame.updateLog("\n");
            }
        }
    }

    public static void createLog() {
        try {
            log = new File("LogTest3.txt");
            if (log.createNewFile()) {
                System.out.println("File created: " + log.getName());
            }

        } catch (IOException e) {
            System.out.println("ERROR");
            e.printStackTrace();
        }
    }

   public void printInFile(ArrayList<Server> serverList, int time) {
       try {
           myWriter.append("\n\n\n\n");
       } catch (IOException e) {
           throw new RuntimeException(e);
       }
       try {
           myWriter.append("Time: " + time +"\n");
       } catch (IOException e) {
           throw new RuntimeException(e);
       }
       try {
           myWriter.append("Waiting: ");
       } catch (IOException e) {
           throw new RuntimeException(e);
       }
       for (Task c : generatedTasks) {
           try {
               myWriter.append("(" +c.getID() + "," + c.getArrivalTime() + "," + c.getServiceTime() + ")  ");
           } catch (IOException e) {
               throw new RuntimeException(e);
           }

       }
       try {
           myWriter.append("\n");
       } catch (IOException e) {
           throw new RuntimeException(e);
       }
       for (Server q : serverList) {
            Task[] c = q.getTasks();
            if (c.length == 0) {
                try {
                    myWriter.append("Server " + q.getQueueId() + ": empty" + "\n");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }


            else {
                try {
                    myWriter.append("Server " + q.getQueueId() + ": ");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                for (Task task : c) {
                    try {
                        myWriter.append("(" + task.getID() + "," + task.getArrivalTime()
                                + "," + task.getServiceTime() + ")  ");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }


                }
                try {
                    myWriter.append("\n");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void writeInLog(float averageWaitingTime, float averageServiceTime, int peakHour) {
        try {
            myWriter.append("Avg waiting time: " + averageWaitingTime + "\n");
            myWriter.append("Avg service time: " + averageServiceTime + "\n");
            myWriter.append("Peak hour: " + peakHour);
            myWriter.close();
        } catch (IOException e) {
            System.out.println("ERROR");
            e.printStackTrace();
        }
    }




    public void generateRandomClients(int nrClients, int minArrival, int maxArrival, int minService, int maxService) {

        for (int i = 0; i < nrClients; i++) {
            int getRandomArrival = (int) (Math.random() * (maxArrival - minArrival + 1) + minArrival);
            int getRandomService = (int) (Math.random() * (maxService - minService + 1) + minService);
            Task task = new Task(i + 1, getRandomArrival, getRandomService);
            generatedTasks.add(task);
        }
        Collections.sort(generatedTasks, new Comparator<Task>() {
            @Override
            public int compare(Task o1, Task o2) {

                return Integer.compare(o1.getArrivalTime(), o2.getArrivalTime());
            }
        });
    }


    public boolean runsThread(List<Server> serverList) {
        if (generatedTasks.size() != 0) {
            return true;
        } else {
            for (Server q : serverList) {
                if (q.serverLenght() != 0) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void run() {
        try {
            myWriter = new FileWriter("LogTest3.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        int time = 0;
        ArrayList<Server> serverList = scheduler.getServers();
        while (runsThread(serverList) && time< simulationInterval) {
            simulationFrame.currentTimeUpdate(time);
            Task task = generatedTasks.peekFirst();
            while (task != null && task.getArrivalTime() <= time) {
                if (scheduler.addTask(task)) {
                    addAverageServiceTime(task.getServiceTime());
                    generatedTasks.remove(0);
                } else
                    break;
                task = generatedTasks.peekFirst();
            }
            peakHour(serverList, time);
            print(serverList, time);
            printInFile(serverList,time);
            time++;
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        simulationFrame.currentTimeUpdate(time);
        print(serverList, time);
        printInFile(serverList, time);
        simulationFrame.updateLog("\n\n\n\nResults:\n");
        averageWaitTime = averageWaitTime / (float) noOfTasks;
        avgServiceTime = avgServiceTime / (float) noOfTasks;
        simulationFrame.updateLog("Avg waiting time: " + averageWaitTime);
        simulationFrame.updateLog("\nAvg service time: " + avgServiceTime);
        simulationFrame.updateLog("\nPeak hour: " + peakTime);

        writeInLog(averageWaitTime, avgServiceTime, peakTime);
    }




    public static void main(String[] args) {
        SimulationFrame simulationFrame = new SimulationFrame();
        simulationFrame.setVisible(true);

        simulationFrame.startListener((ActionEvent e) -> {
            SimulationManager simulationManager = new SimulationManager(simulationFrame);
            Thread t = new Thread(simulationManager);
            createLog();
            t.start();
        });
    }
}