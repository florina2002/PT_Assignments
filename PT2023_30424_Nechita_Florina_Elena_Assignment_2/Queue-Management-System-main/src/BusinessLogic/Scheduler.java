package BusinessLogic;

import Model.Client;
import Model.Queue;

import java.util.ArrayList;

public class Scheduler {
    private ArrayList<Queue> queues;
    private static int maxNumberOfQueues;
    private Strategy strategy=new ShortestTime();

    public Scheduler(int maxNumberOfQueues) {
        queues = new ArrayList<>();
        this.maxNumberOfQueues = maxNumberOfQueues;
        for(int i = 0; i < maxNumberOfQueues; i++) {
            queues.add(new Queue(i+1));
            queues.get(i).start();
        }
    }

    public boolean addClient(Client client) {
        return strategy.addClient(queues, client);
    }

    public ArrayList<Queue> getQueues() {
        return queues;
    }

        public static int getMaxNumberOfQueues() {
        return maxNumberOfQueues;
    }
}
