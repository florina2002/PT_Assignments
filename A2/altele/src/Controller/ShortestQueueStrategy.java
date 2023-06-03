package Controller;

import Model.Client;
import Model.ClientQueue;

import java.util.List;

public class ShortestQueueStrategy implements Strategy {
    List<ClientQueue> queues;

    public ShortestQueueStrategy(List<ClientQueue> queues) {
        this.queues = queues;
    }

    @Override
    public void addClient(Client client, List<ClientQueue> queues) {
        int minimum = Integer.MAX_VALUE;
        int i = 0;
        for (int index = 0; index < queues.size(); index++) {
            if (queues.get(index).getClients().size() < minimum) {
                minimum = queues.get(index).getClients().size();
                i = index;
            }
        }
        queues.get(i).addClient(client);
    }
}
