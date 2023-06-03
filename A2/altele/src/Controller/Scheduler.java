package Controller;

import Model.ClientQueue;

import java.util.ArrayList;
import java.util.List;

public class Scheduler {
    protected List<ClientQueue> queues;
    private List<Thread> threads;
    protected Strategy strategy;

    public List<ClientQueue> getQueues() {
        return this.queues;
    }

    public Scheduler(int nrQueues, String strategy) {
        this.queues = new ArrayList<>();
        this.threads = new ArrayList<>();
        for (int i = 1; i <= nrQueues; i++) {
            ClientQueue clientQueue = new ClientQueue(i);
            this.queues.add(clientQueue);
            Thread thread = new Thread(clientQueue);
            threads.add(thread);
            thread.start();
        }
        if (strategy.equals("Time Strategy")) {
            this.strategy = new TimeStrategy(this.queues);
        } else if (strategy.equals("Shortest Queue Strategy")) {
            this.strategy = new ShortestQueueStrategy(this.queues);
        }
    }

    @Override
    public String toString() {
        String print = "";
        for (ClientQueue clientQueue : this.queues) {
            print = print + clientQueue.toString() + '\n';
        }
        print = print + '\n';
        return print;
    }
}
