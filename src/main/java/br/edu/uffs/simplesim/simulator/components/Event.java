package br.edu.uffs.simplesim.simulator.components;

public class Event implements Comparable<Event> {
    private Double time;
    private String eventExecutorInChargeName;


    public Event(Double time, String eventExecutorInChargeName) {
        this.time = time;
        this.eventExecutorInChargeName = eventExecutorInChargeName;
    }

    @Override
    public int compareTo(Event other) {
        Double thisTime = this.getTime();
        Double otherTime = other.getTime();
        return thisTime.compareTo(otherTime);
    }


    public Double getTime() {
        return time;
    }

    public String getEventExecutorInChargeName() {
        return eventExecutorInChargeName;
    }

}
