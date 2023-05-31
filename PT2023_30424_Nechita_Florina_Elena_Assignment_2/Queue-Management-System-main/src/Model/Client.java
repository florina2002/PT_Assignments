package Model;

public class Client{
    private int id;
    private int arrivalTime;
    private int serviceTime;

    //set id
    public Client(int id, int arrivalTime, int serviceTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
    }
    //
    public Client() {
        this.id=0;
        this.arrivalTime=0;
        this.serviceTime=0;
    }

    public void decreaseServiceTime() {
        this.serviceTime--;
    }

    //region get&set

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(int serviceTime) {
        this.serviceTime = serviceTime;
    }



    //endregion
}
