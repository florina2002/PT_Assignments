package org.example.BusinessLogic;
import org.example.Model.Task;
import org.example.Model.Server;
import java.util.ArrayList;

public class Scheduler {
    private ArrayList<Server> servers;
    private static int maxNoTasks;
    private Strategy strategy = new TimeStrategy();

    public Scheduler(int maxNoTasks) {
        servers = new ArrayList<>();
        this.maxNoTasks = maxNoTasks;
        for(int i = 0; i < maxNoTasks; i++) {
            servers.add(new Server(i+1));
            servers.get(i).start();
        }
    }

    public ArrayList<Server> getServers() {
        return servers;
    }

    public static int getMaxNoTasks() {
        return maxNoTasks;
    }

    public boolean addTask(Task task) {

        return strategy.addTask(servers, task);
    }


}