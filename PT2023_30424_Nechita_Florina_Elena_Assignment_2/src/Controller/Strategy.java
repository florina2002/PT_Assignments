package Controller;

import Model.Client;
import Model.ClientQueue;

import java.util.List;

public interface Strategy {
    void addClient(Client client, List<ClientQueue> queues);
}
