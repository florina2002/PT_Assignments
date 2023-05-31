package BusinessLogic;

import Model.Client;
import Model.Queue;

import java.util.ArrayList;

public class ShortestQueue implements Strategy {

    @Override
    public boolean addClient(ArrayList<Queue> queues, Client c) {
        int nrOfQueues = Scheduler.getMaxNumberOfQueues();
        if (nrOfQueues > 0) {
            int min = queues.get(0).getQueueLength();
            int minIndex = 1;
            Queue chosen = queues.get(0);
            while (minIndex != nrOfQueues) {
                int length = queues.get(minIndex).getQueueLength();
                if (length < min) {
                    min = length;
                    chosen = queues.get(minIndex);
                }
                minIndex++;
            }
            SimulationManager.addAverageWaitTime(chosen.getWaitingPeriod());
            chosen.addClient(c);
            return true;
        }
        return false;
    }
}
