package br.edu.uffs.simplesim.simulator.components;

import br.edu.uffs.simplesim.simulator.montecarlo.MonteCarlo;

public class Attendant implements Comparable<Attendant> {

    public enum Type {UNITARY, INFINITE};

    private Integer id;
    private Double lastAttendanceEndTime;
    private Type type;

    public Attendant(Integer id, Type type){
        this.id = id;
        this.type = type;
        lastAttendanceEndTime = 0.0;
    }

    @Override
    public int compareTo(Attendant other) {
        return this.lastAttendanceEndTime.compareTo(other.lastAttendanceEndTime);
    }

    public Double attend(Event event, Double attendanceTime) {
        Double attendanceStartTime = Math.max(lastAttendanceEndTime, event.getTime());
        Double attendanceEndTime = attendanceStartTime + attendanceTime;

        if(type == Type.UNITARY)
            lastAttendanceEndTime = attendanceEndTime;


        return attendanceEndTime;
    }
}
