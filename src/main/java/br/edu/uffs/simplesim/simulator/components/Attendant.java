package br.edu.uffs.simplesim.simulator.components;

public class Attendant implements Comparable<Attendant> {
    private Integer id;
    private Double lastAttendanceEndTime;

    public Attendant(Integer id){
        this.id = id;
        lastAttendanceEndTime = 0.0;
    }

    @Override
    public int compareTo(Attendant other) {
        return this.getLastAttendanceEndTime().compareTo(other.getLastAttendanceEndTime());
    }

    public Double getLastAttendanceEndTime() {
        return lastAttendanceEndTime;
    }

    public void setLastAttendanceEndTime(Double lastAttendanceEndTime) {
        this.lastAttendanceEndTime = lastAttendanceEndTime;
    }
}
