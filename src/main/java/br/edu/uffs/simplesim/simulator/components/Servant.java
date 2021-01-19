package br.edu.uffs.simplesim.simulator.components;

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
    public String getStatistics() {
        String statistics = "";

        statistics += getName() + '\n';
        statistics += "Idle time: " + idleTime + '\n';

        return statistics;
    }

    public Double getLastServiceEndTime() {
        return lastServiceEndTime;
    }

}
