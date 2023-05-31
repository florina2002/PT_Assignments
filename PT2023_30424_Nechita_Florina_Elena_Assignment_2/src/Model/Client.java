package Model;

import java.util.Random;

public class Client {
    public int id;
    public int arrivalTime;
    public int serviceTime;

    public Client(int id, int arrivalTime, int serviceTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
    }

    public int getId() {
        return id;
    }


    public int getArrivalTime() {
        return arrivalTime;
    }


    public int getServiceTime() {
        return serviceTime;
    }

    @Override
    public String toString() {
        return "(" + this.getId() + ", " + this.getArrivalTime() + ", " + this.serviceTime + ")";
    }
}
