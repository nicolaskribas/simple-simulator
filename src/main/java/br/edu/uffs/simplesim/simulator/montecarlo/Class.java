package br.edu.uffs.simplesim.simulator.montecarlo;

public class Class<T> implements Comparable<Integer>{
    private T midpoint;
    private Integer frequency;
    private Integer cumulativeFrequency;

    public Class(T midpoint) {
        this.midpoint = midpoint;
        this.frequency = 0;
        this.cumulativeFrequency = 0;
    }

    public Class(T midpoint, Integer frequency, Integer cumulativeFrequency){
        this.midpoint = midpoint;
        this.frequency = frequency;
        this.cumulativeFrequency = cumulativeFrequency;
    }

    public Integer getCumulativeFrequency() {
        return cumulativeFrequency;
    }

    public void setCumulativeFrequency(Integer cumulativeFrequency) {
        this.cumulativeFrequency = cumulativeFrequency;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public void addOneToFrequency() {
        frequency = frequency + 1;
    }

    public T getMidpoint() {
        return midpoint;
    }

    public void setMidpoint(T midpoint) {
        this.midpoint = midpoint;
    }


    @Override
    public int compareTo(Integer other) {
        return this.getCumulativeFrequency().compareTo(other);
    }
}
