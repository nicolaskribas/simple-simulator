package br.edu.uffs.simplesim.simulator.components;

public class TemporaryEntity {

    private int id;
    private Double arrivalTime;

    public TemporaryEntity(int id, Double arrivalTime) {
        this.arrivalTime = arrivalTime;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Double arrivalTime) {
        this.arrivalTime = arrivalTime;
    }



}
