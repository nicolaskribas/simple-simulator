package br.edu.uffs.simplesim.simulator;

import br.edu.uffs.simplesim.simulator.components.*;

import java.util.List;

public class Simulator {
    private List<TemporaryUnitGenerator> temporaryUnitGenerators;
    private List<ServiceCenter> serviceCenters;
    private List<TemporaryUnitRouter> temporaryUnitRouters;
    private List<TemporaryUnitExit> temporaryUnitExits;

    public List<TemporaryUnitGenerator> getTemporaryUnitGenerators() {
        return temporaryUnitGenerators;
    }

    public void setTemporaryUnitGenerators(List<TemporaryUnitGenerator> temporaryUnitGenerators) {
        this.temporaryUnitGenerators = temporaryUnitGenerators;
    }

    public List<ServiceCenter> getServiceCenters() {
        return serviceCenters;
    }

    public void setServiceCenters(List<ServiceCenter> serviceCenters) {
        this.serviceCenters = serviceCenters;
    }

    public List<TemporaryUnitRouter> getTemporaryUnitRouters() {
        return temporaryUnitRouters;
    }

    public void setTemporaryUnitRouters(List<TemporaryUnitRouter> temporaryUnitRouters) {
        this.temporaryUnitRouters = temporaryUnitRouters;
    }

    public List<TemporaryUnitExit> getTemporaryUnitExits() {
        return temporaryUnitExits;
    }

    public void setTemporaryUnitExits(List<TemporaryUnitExit> temporaryUnitExits) {
        this.temporaryUnitExits = temporaryUnitExits;
    }
}
