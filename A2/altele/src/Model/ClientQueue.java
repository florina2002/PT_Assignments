package Model;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.currentThread;
import static java.lang.Thread.sleep;

public class ClientQueue implements Runnable {
    private final LinkedBlockingQueue<Client> clients;
    private final AtomicInteger waitingTime;
    private final AtomicInteger serviceTime;
    private final AtomicInteger totalWaiting;
    private final int queueID;


    public ClientQueue(int id) {
        clients = new LinkedBlockingQueue<>();
        waitingTime = new AtomicInteger(0);
        serviceTime = new AtomicInteger(0);
        totalWaiting = new AtomicInteger(0);
        queueID = id;
    }

    public void addClient(Client client) {
        clients.add(client);
        int toAdd = client.getServiceTime();
        waitingTime.getAndAdd(toAdd);
        totalWaiting.getAndAdd(toAdd);
    }

    public AtomicInteger getTotalWaiting() {
        return totalWaiting;
    }

    public LinkedBlockingQueue<Client> getClients() {
        return clients;
    }

    public AtomicInteger getWaitingTime() {
        return waitingTime;
    }

    @Override
    public void run() {
        while (currentThread().isAlive()) {
            if (clients.size() > 0) {
                try {
                    Client servedClient = clients.peek();
                    int timeToServe = servedClient.getServiceTime();
                    sleep(1000 * timeToServe);
                    clients.poll();
                    waitingTime.getAndAdd(-servedClient.getServiceTime());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public int currentClients() {
        return clients.size();
    }

    @Override
    public String toString() {
        String printed = "";
        printed += "Queue " + queueID + ": ";
        if (clients.size() > 0) {
            for (Client c : clients) {
                printed += c.toString() + "; ";
            }
        } else {
            printed += "closed";
        }
        return printed;
    }


}