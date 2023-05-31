package org.example.BusinessLogic;

import org.example.Model.Server;
import org.example.Model.Task;

import java.util.ArrayList;

public class ShortestQueueStrategy implements Strategy {

    @Override
    public boolean addTask(ArrayList<Server> servers, Task c) {
        int nrOfQueues = Scheduler.getMaxNoTasks();

        if (nrOfQueues > 0) {
            int min = servers.get(0).serverLenght();
            int indaux = 1;
            Server aux = servers.get(0);
            while (indaux != nrOfQueues) {
                int length = servers.get(indaux).serverLenght();
                if (length < min) {
                    min = length;
                    aux = servers.get(indaux);
                }
                indaux++;
            }

            SimulationManager.addAverageWaitTime(aux.getWaitingPeriod());
            aux.addTask(c);
            return true;
        }
        return false;
    }
}