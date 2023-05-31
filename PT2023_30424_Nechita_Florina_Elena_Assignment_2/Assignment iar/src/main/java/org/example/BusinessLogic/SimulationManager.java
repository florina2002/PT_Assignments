package org.example.BusinessLogic;

import org.example.GUI.SimulationFrame;
import org.example.Model.Server;
import org.example.Model.Task;

import java.util.*;

public class SimulationManager implements Runnable {
    //data read from UI
    public int timeLimit = 100; //maximum processing time - read from UI
    public int maxProcessingTime = 10; //maximum processing time - read from UI
    public int minProcessingTime = 2; //minimum processing time - read from UI
    public int numberOfServers = 3; //number of servers - read from UI
    public int numberOfClients = 7; //number of clients - read from UI
    public SelectionPolicy selectionPolicy = SelectionPolicy.SHORTEST_TIME;


    //entity responsible with queue management and client distribution
    private Scheduler scheduler;
    //frame for displaying the simulation
    private SimulationFrame frame;
    //pool of tasks (client shopping in the store)
    private List<Task> generatedTasks = new ArrayList<>();

    public SimulationManager() {
        //initialize the scheduler
        // => create and start numberOfServers threads
        // => initialize selection strategy => createStrategy
        // initialize frame to display the simulation
        //generated numberOfClients clients using generateNRandomTasks()
        //and store them to generatedTasks

        this.scheduler = new Scheduler(7, 3, 20);

    }

    private void generateNRandomTasks(int numberOfClients) {
        //generate N random tasks
        //-random processing time
        //minProcessingTime < processingTime < maxProcessingTime
        //-random arrival time
        //sort list with respect to arrivalTime

        int i;
        Random randomNo = new Random();
        for(i=0; i < numberOfClients; i++){
            int auxID = i+1;
            int auxArrivalTime = randomNo.nextInt(timeLimit/4);
            int auxProcessingTime = minProcessingTime + randomNo.nextInt(maxProcessingTime - minProcessingTime+1);
            int auxServiceTime =  auxProcessingTime;
            Task aux = new Task(auxID, auxArrivalTime, auxServiceTime);

            generatedTasks.add(aux);

        }
    }

    @Override
    public void run() {
        int currentTime = 0;
        int i = 0;

        generateNRandomTasks(numberOfClients);
        Collections.sort(generatedTasks, new SortByArrivalTime());


        while (currentTime < timeLimit) {
            //iterate generatedTasks list and pick tasks that have
            //the arrivalTime equal with the currentTime
            //-send task to queue by calling dispatchTask() from Scheduler
            //-delete client from list
            //update UI frame

            int k = 0;
            while (k < generatedTasks.size()){
                if (generatedTasks.get(k).getArrivalTime() == currentTime){
//                    try {
//                        Thread.sleep(10);
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    }


                        scheduler.dispatchTask(generatedTasks.get(k));
                        generatedTasks.remove(k);


                }
                else k++;
            }

            printQueue(scheduler.getServers(), currentTime);


            currentTime++;
            //wait an interval of one second

            Thread.currentThread().interrupt();

        }
    }

    public void printQueue(List<Server> servers, int currentTime){
        int noServer = 1;

        System.out.print("Waiting clients: ");
        for (Task t : generatedTasks){
            System.out.print("(" + t.getID() + ", " + t.getArrivalTime() + ", " + t.getServiceTime() + ") ");
        }
        System.out.print("\n Time: " + currentTime + "\n");

        for (Server server : servers){
            ArrayList<Task> tasks = server.getTask();

            if(tasks.isEmpty() == false ){
                System.out.println("Queue " + noServer + ": ");
                int rmvCnt = 0;
                for (Task t : tasks){
                    if (server.getWaitingPeriod() == 0){
                        server.removeTask(t);
                        if(server.getTasks() == null) {
                            System.out.print("EMPTY\n");
                        }
                    } else System.out.print("(" + t.getID() + ", " + t.getArrivalTime() + ", " + t.getServiceTime() + ")");

                }
                System.out.println("\n");

            }
            else System.out.println("Queue empty");
            noServer++;
        }
        System.out.println("\n");
    }

    public static class SortByArrivalTime implements Comparator<Task> {

        public int compare (Task t1, Task t2) {
            return t1.getArrivalTime() - t2.getArrivalTime();
        }


    }

    public static void main(String[] args) {
        SimulationManager gen = new SimulationManager();
        Thread t = new Thread(gen);
        t.start();
    }


}
