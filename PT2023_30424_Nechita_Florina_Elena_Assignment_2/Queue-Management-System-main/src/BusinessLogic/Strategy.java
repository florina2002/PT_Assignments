package BusinessLogic;

import Model.Client;
import Model.Queue;
import java.util.ArrayList;

public interface Strategy {
    boolean addClient(ArrayList<Queue> queues, Client c);
}