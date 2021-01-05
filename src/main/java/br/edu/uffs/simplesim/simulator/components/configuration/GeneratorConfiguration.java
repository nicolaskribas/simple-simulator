package br.edu.uffs.simplesim.simulator.components.configuration;

import java.util.List;

public class GeneratorConfiguration {
    private String name;
    private List<Double> samples;
    private Integer numberOfClasses;
    private Integer numberOfTemporaryUnitsToGenerate;
    private String nextComponentName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Double> getSamples() {
        return samples;
    }

    public void setSamples(List<Double> samples) {
        this.samples = samples;
    }

    public Integer getNumberOfClasses() {
        return numberOfClasses;
    }

    public void setNumberOfClasses(Integer numberOfClasses) {
        this.numberOfClasses = numberOfClasses;
    }

    public Integer getNumberOfTemporaryUnitsToGenerate() {
        return numberOfTemporaryUnitsToGenerate;
    }

    public void setNumberOfTemporaryUnitsToGenerate(Integer numberOfTemporaryUnitsToGenerate) {
        this.numberOfTemporaryUnitsToGenerate = numberOfTemporaryUnitsToGenerate;
    }

    public String getNextComponentName() {
        return nextComponentName;
    }

    public void setNextComponentName(String nextComponentName) {
        this.nextComponentName = nextComponentName;
    }
}
