package br.edu.uffs.simplesim.simulator.components;

public class Event implements Comparable<Event> {
    private Double time;
    private String eventExecutorInChargeName;
    private TemporaryEntity temporaryEntity;


    public Event(String eventExecutorInChargeName, Double time, TemporaryEntity temporaryEntity) {
        this.time = time;
        this.eventExecutorInChargeName = eventExecutorInChargeName;
        this.temporaryEntity = temporaryEntity;
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

    public TemporaryEntity getTemporaryEntity() {
        return temporaryEntity;
    }
}
