package org.example.Model;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable{

    private BlockingQueue<Task> tasks;
    private AtomicInteger waitingPeriod;
    private int finalTime;

    private int running = 1;

    public int currentTime;

    public Server(int finalTime){
        //initiate queue and waiting period
        tasks = new ArrayBlockingQueue<Task>(20);
        waitingPeriod = new AtomicInteger();
        this.finalTime = finalTime;
        running = 1;
    }

    public void addTask(Task newTask){
        //add task to queue
        //increment waiting period

        tasks.add(newTask);
        waitingPeriod.addAndGet(newTask.getServiceTime());
    }

    public ArrayList<Task> getTask(){
        return new ArrayList<>(tasks);
    }



    @Override
    public void run() {
        currentTime = 0;

        while (running == 1){
            //take next task from queue
            //stop the thread for a time equal with the task's processing time
            //decrement waiting period
            try {
                Task task = tasks.poll(1000, TimeUnit.MILLISECONDS);
                while (task != null && task.getServiceTime() != 0){ //if cu while de inlocuit pt service time
                    int aux = task.getServiceTime();
                    task.setServiceTime(aux - 1);
                    waitingPeriod.addAndGet(-1);
                    if (task.getServiceTime() == 0){
                        tasks.remove(task); //aici poate nu merge
                    }
                }

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            currentTime++;
            if (currentTime == finalTime){
                running = 0;
                break;
            }
        }

    }

    public void removeTask (Task t){
        if (waitingPeriod.equals(0)){
            tasks.remove(t);
        }
    }

    public int getWaitingPeriod() {return waitingPeriod.get();}

    public Task[] getTasks() {
        //return the tasks in the queue
        return null;
    }
}
