package br.edu.uffs.simplesim.simulator.components.configuration;

import br.edu.uffs.simplesim.simulator.configurationbeans.NextComponentProbability;

import java.util.List;

public class RouterConfiguration {

    private String name;
    private List<NextComponentProbability> nextComponentsProbabilities;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<NextComponentProbability> getNextComponentsProbabilities() {
        return nextComponentsProbabilities;
    }

    public void setNextComponentsProbabilities(List<NextComponentProbability> nextComponentsProbabilities) {
        this.nextComponentsProbabilities = nextComponentsProbabilities;
    }
}
