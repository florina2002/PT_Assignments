package Model;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;


public class Queue extends Thread {
    private BlockingQueue<Client> queue;
    private final AtomicInteger waitingPeriod;
    private int id;

    public Queue(int id){
        queue=new LinkedBlockingQueue<>();
        waitingPeriod=new AtomicInteger();
        this.id=id;
    }

    public synchronized void addClient(Client client){
        queue.add(client);
        waitingPeriod.addAndGet(client.getServiceTime());
    }

    public synchronized int getQueueLength() {
        return queue.size();
    }

    public int getQueueId() {
        return id;
    }

    public Client[] getClients(){
        Client [] clients=new Client[queue.size()];
        int i=0;
        for(Client client:queue){
            clients[i++]=client;
        }
        return clients;
    }

    public int getWaitingPeriod() {
        return waitingPeriod.get();
    }

    public void run(){
        while(true){
            Client client=queue.peek();
            if(client!=null){
                try {
                    sleep(500);
                    client.decreaseServiceTime();
                    if(client.getServiceTime()==0){
                        queue.poll();
                    }
                    waitingPeriod.decrementAndGet();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
