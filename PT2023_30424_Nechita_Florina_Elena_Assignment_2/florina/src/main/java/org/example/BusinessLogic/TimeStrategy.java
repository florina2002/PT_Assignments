package org.example.BusinessLogic;

import org.example.Model.Server;
import org.example.Model.Task;

import java.util.ArrayList;

public class TimeStrategy implements Strategy{
    @Override
    public boolean addTask(ArrayList<Server> servers, Task task) {
        int nrOfQueues = Scheduler.getMaxNoTasks();
        if (nrOfQueues == 0) {
            return false;
        }
        int min = servers.get(0).getWaitingPeriod();
        Server aux = servers.get(0);
        int minIndex = 1;
        while (minIndex != nrOfQueues) {
            int waitingTime = servers.get(minIndex).getWaitingPeriod();
            if (waitingTime < min) {
                min = waitingTime;
                aux = servers.get(minIndex);
            }
            minIndex++;
        }
        aux.addTask(task);

        SimulationManager.addAverageWaitTime(aux.getWaitingPeriod());
        return true;
    }
}