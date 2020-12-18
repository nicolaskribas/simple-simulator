package br.edu.uffs.simplesim.simulator.components;


public abstract class Component {
    private String name;

    public final String getName() {
        return name;
    }

    public final void setName(String name) {
        this.name = name;
    }

    public abstract String getStatistics();
}
