package org.example.Model;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;


public class Server extends Thread {
    private BlockingQueue<Task> tasks;
    private final AtomicInteger waitingPeriod;
    private int ID;

    public Server(int ID){
        tasks = new LinkedBlockingQueue<>();
        waitingPeriod = new AtomicInteger();
        this.ID = ID;
    }

    public synchronized void addTask(Task task){
        tasks.add(task);
        waitingPeriod.addAndGet(task.getServiceTime());
    }

    public synchronized int serverLenght() {

        return tasks.size();
    }

    public int getQueueId() {

        return ID;
    }

    public Task[] getTasks(){
        Task[] tasks =new Task[this.tasks.size()];
        int i=0;
        for(Task task : this.tasks){
            tasks[i++]= task;
        }
        return tasks;
    }

    public int getWaitingPeriod() {

        return waitingPeriod.get();
    }

    public void run(){
        while(true){
            Task task = tasks.peek();
            if(task != null){
                try {
                    sleep(500);
                    task.decreaseServiceTime();
                    if(task.getServiceTime()==0){
                        tasks.poll();
                    }
                    waitingPeriod.decrementAndGet();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
