package Controller;

import Model.Client;
import Model.ClientQueue;

import java.util.ArrayList;
import java.util.List;

public class TimeStrategy implements Strategy {

    public List<ClientQueue> clientQueues = new ArrayList<>();

    public TimeStrategy(List<ClientQueue> clientQueues) {
        this.clientQueues = clientQueues;
    }

    @Override
    public void addClient(Client client, List<ClientQueue> queues) {
        int minimum = Integer.MAX_VALUE;
        int i = 0;
        for (int index = 0; index < queues.size(); index++) {
            if (queues.get(index).getWaitingTime().intValue() < minimum) {
                minimum = queues.get(index).getWaitingTime().intValue();
                i = index;
            }
        }
        queues.get(i).addClient(client);
    }
}
