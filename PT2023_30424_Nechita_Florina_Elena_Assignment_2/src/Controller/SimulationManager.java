package Controller;

import Model.Client;
import Model.ClientQueue;
import View.QueueView;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.sleep;

public class SimulationManager implements Runnable {
    private Scheduler scheduler;
    private Integer simulationTime = 0;
    private Integer nbClients = 0;
    private Integer nbQueues = 0;
    private Integer minArrival = 0;
    private Integer maxArrival = 0;
    private Integer minService = 0;
    private Integer maxService = 0;
    private AtomicInteger time = new AtomicInteger(0);
    private ArrayList<Client> newClients = new ArrayList<>();
    private BufferedWriter writer;
    private int totalService = 0;
    public QueueView queueView;
    private String strategy;

    public SimulationManager(QueueView queueView) {
        this.queueView = queueView;
        try {
            File file = createFile();
            writer = new BufferedWriter(new FileWriter(file.getName()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Integer getNbClients() {
        return nbClients;
    }

    public void setValues(ArrayList<Integer> inputs, String strategy) {
        this.nbClients = inputs.get(0);
        this.nbQueues = inputs.get(1);
        this.simulationTime = inputs.get(2);
        this.minArrival = inputs.get(3);
        this.maxArrival = inputs.get(4);
        this.minService = inputs.get(5);
        this.maxService = inputs.get(6);
        this.strategy = strategy;
        scheduler = new Scheduler(nbQueues, this.strategy);
    }

    public void generateClients() {
        Random rand = new Random();
        for (int i = 1; i <= nbClients; i++) {
            int arrival = rand.nextInt(minArrival, maxArrival + 1);
            int service = rand.nextInt(minService, maxService + 1);
            newClients.add(new Client(i, arrival, service));
            totalService += service;
        }
    }

    public float calculateAverageWaiting() {
        int waiting = 0;
        for (ClientQueue cq : scheduler.getQueues()) {
            waiting += cq.getTotalWaiting().intValue();
        }
        return (waiting * 1.0f) / (nbQueues * 1.0f);
    }

    public File createFile() throws IOException {
        File file = new File("simulation.txt");
        boolean created = file.createNewFile();
        if (created) {
            System.out.println("File created");
        } else {
            System.out.println("File already exists");
        }
        return file;
    }

    public void writeFile(String string) {
        try {
            writer.write(string);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(string);
    }

    public String writeSimDetails(int peakHour) {
        String string;
        string = "Average waiting time: " + calculateAverageWaiting() + "\n";
        string = string + "Average service time: " + (1.0f * totalService / nbClients) + "\n";
        string = string + "Peak hour: " + peakHour + "\n";
        return string;
    }

    public void closeWriter() {
        try {
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printWaitingClients() {
        String string;
        if (newClients.size() > 0) {
            string = "Waiting clients: ";
            this.queueView.logText.setText(this.queueView.logText.getText() + string);
            writeFile(string);
        }

        for (int i = 0; i < newClients.size(); i++) {
            if (newClients.get(i).getArrivalTime() <= time.intValue()) {
                scheduler.strategy.addClient(newClients.get(i), scheduler.queues);
                newClients.remove(newClients.get(i));
            }
        }

        for (Client c : newClients) {
            string = c.toString() + ", ";
            writeFile(string);
            this.queueView.logText.setText(this.queueView.logText.getText() + string);
        }
        this.queueView.logText.setText(this.queueView.logText.getText() + "\n");
        writeFile("\n");
    }

    @Override
    public void run() {
        generateClients();
        String string = null;
        int peakHour = 0, maxNbActiveClients = 0, currentClients = 0;
        while (time.intValue() <= simulationTime) {
            string = "\nTime: " + time.toString() + "\n";
            this.queueView.logText.setText(this.queueView.logText.getText() + string);
            writeFile(string);
            printWaitingClients();
            for (ClientQueue cq : scheduler.getQueues()) {
                currentClients += cq.currentClients();
            }
            if (currentClients > maxNbActiveClients) {
                maxNbActiveClients = currentClients;
                peakHour = time.intValue();
            }
            currentClients = 0;
            this.queueView.logText.setText(this.queueView.logText.getText() + scheduler.toString());
            writeFile(scheduler.toString());
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            time.getAndIncrement();
        }
        if (newClients.isEmpty() || time.intValue() > simulationTime) {
            string = writeSimDetails(peakHour);
        }
        writeFile(string);
        this.queueView.logText.setText(this.queueView.logText.getText() + string);
        closeWriter();
    }
}