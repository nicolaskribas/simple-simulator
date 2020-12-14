package br.edu.uffs.simplesim.simulator.components.configuration;

import java.util.List;

public class ServiceCenterConfiguration {
    private String name;
    private Integer numberOfAttendants;
    private List<Double> samples;
    private Integer numberOfClasses;
    private String nextComponentName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumberOfAttendants() {
        return numberOfAttendants;
    }

    public void setNumberOfAttendants(Integer numberOfAttendants) {
        this.numberOfAttendants = numberOfAttendants;
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

    public String getNextComponentName() {
        return nextComponentName;
    }

    public void setNextComponentName(String nextComponentName) {
        this.nextComponentName = nextComponentName;
    }
}
