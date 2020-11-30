package br.edu.uffs.simplesim.simulator.components;

import java.util.List;

public class TemporaryUnitRouter {

    private String name;
    private List<Next> next;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Next> getNext() {
        return next;
    }

    public void setNext(List<Next> next) {
        this.next = next;
    }
}
