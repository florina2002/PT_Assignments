package BusinessLogic;

import Model.Client;

import java.util.ArrayList;
import Model.Queue;

public class ShortestTime implements Strategy{
    @Override
    public boolean addClient(ArrayList<Queue> queues, Client client) {
        int nrOfQueues = Scheduler.getMaxNumberOfQueues();
        if (nrOfQueues == 0) {
            return false;
        }
        int min = queues.get(0).getWaitingPeriod();
        Queue chosen = queues.get(0);
        int minIndex = 1;
        while (minIndex != nrOfQueues) {
            int waitingTime = queues.get(minIndex).getWaitingPeriod();
            if (waitingTime < min) {
                min = waitingTime;
                chosen = queues.get(minIndex);
            }
            minIndex++;
        }
        chosen.addClient(client);
        SimulationManager.addAverageWaitTime(chosen.getWaitingPeriod());
        return true;
    }
}
