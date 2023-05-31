package org.example.BusinessLogic;
//poll sa decrementam waiting time

import org.example.Model.Server;
import org.example.Model.Task;

import java.util.ArrayList;
import java.util.List;

public class Scheduler {
    private List<Server> servers = new ArrayList<>();
    private int maxNoServers;
    private int maxNoClients;
    private int finalTime;
    private int currentTime;
    private int maxTasksPerServer;
    private Strategy strategy;



    public Scheduler (int noClients, int noServers, final int timeLimit) {
        //for maxNoServers
        //- create server object
        //-create thread with the object

        currentTime = 0;
        for (int currentServer = 0; currentServer < noServers; currentServer++){
            Server server = new Server(finalTime);
            Thread queue = new Thread(server);
            servers.add(server);
        }

        this.maxNoServers = maxNoServers;
        this.maxTasksPerServer = maxTasksPerServer;
        this.finalTime = finalTime;
    }



    public void dispatchTask(Task t){
        //call the strategy addTask method
        int minWaitingPeriod = 10000;
        int minWaitingService = 0;
        int i = 0;
        for (Server server : servers){
            if (server.getWaitingPeriod() < minWaitingPeriod){
                minWaitingPeriod = server.getWaitingPeriod();
                minWaitingService = 1;
            }
            i++;
        }

//        try {
//            Thread.sleep(10);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }

        servers.get(minWaitingService).addTask(t);

    }

    public List<Server> getServers() {
        return servers;
    }


}
