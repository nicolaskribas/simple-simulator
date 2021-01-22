package br.edu.uffs.simplesim.simulator.components;

import br.edu.uffs.simplesim.simulator.utils.Utils;

public class Servant extends Component implements Comparable<Servant> {

    private Double lastServiceEndTime;


    private Double idleTime;

    public Servant(String name) {
        setName(name);
        lastServiceEndTime = 0.0;
        idleTime = 0.0;
    }

    public void updateTimes(Double serviceStartTime, Double totalServiceTime) {
        idleTime += serviceStartTime - lastServiceEndTime;
        lastServiceEndTime = serviceStartTime + totalServiceTime;
    }

    @Override
    public int compareTo(Servant other) {
        return this.lastServiceEndTime.compareTo(other.lastServiceEndTime);
    }

    @Override
    public String getStatistics(Double endOfSimulationTime) {
        return "(" + getName() + " - " + Utils.formatDouble(getIdleTime(endOfSimulationTime)) + ")";
    }

    public Double getLastServiceEndTime() {
        return lastServiceEndTime;
    }

    public Double getIdleTime(Double endOfSimulationTime) {
        return idleTime + endOfSimulationTime - lastServiceEndTime;
    }
}
