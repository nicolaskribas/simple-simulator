package br.edu.uffs.simplesim.simulator.components;

import java.util.List;

public class ServiceCenter {
    private String name;
    private List<Integer> observations;
    private int numberOfClasses;
    private String next;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getObservations() {
        return observations;
    }

    public void setObservations(List<Integer> observations) {
        this.observations = observations;
    }

    public int getNumberOfClasses() {
        return numberOfClasses;
    }

    public void setNumberOfClasses(int numberOfClasses) {
        this.numberOfClasses = numberOfClasses;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }
}
