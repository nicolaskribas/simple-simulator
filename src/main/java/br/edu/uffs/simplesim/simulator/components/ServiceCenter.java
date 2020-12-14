package br.edu.uffs.simplesim.simulator.components;

import br.edu.uffs.simplesim.simulator.components.configuration.ServiceCenterConfiguration;
import br.edu.uffs.simplesim.simulator.montecarlo.MonteCarlo;

import java.util.*;

public class ServiceCenter extends Component implements EventExecutor {
    public static final Integer INFINITE_NUMBER_OF_ATTENDANTS = -1;

    private final String nextComponentName;
    private final Integer numberOfAttendants;
    private final MonteCarlo monteCarlo;
    private final Queue<Attendant> attendantsQueue = new PriorityQueue<>();

    public ServiceCenter(ServiceCenterConfiguration serviceCenterConfiguration) {
        super.setName(serviceCenterConfiguration.getName());
        nextComponentName = serviceCenterConfiguration.getNextComponentName();
        numberOfAttendants = serviceCenterConfiguration.getNumberOfAttendants();
        monteCarlo = new MonteCarlo(serviceCenterConfiguration.getSamples(), serviceCenterConfiguration.getNumberOfClasses());
        for(int i = 1; i <= numberOfAttendants; i++){
            attendantsQueue.add(new Attendant(i));
        }
    }

    @Override
    public Optional<Event> execute(Event event) {
        Attendant attendant = attendantsQueue.poll();
        Double attendanceStartTime = Math.max(attendant.getLastAttendanceEndTime(), event.getTime());
        Double attendanceTotalTime = monteCarlo.generateRandomSample();
        Double attendanceEndTime = attendanceStartTime + attendanceTotalTime;

        if(numberOfAttendantsIsNotInfinite())
            attendant.setLastAttendanceEndTime(attendanceEndTime);

        attendantsQueue.add(attendant);
        Event nextEvent = new Event(attendanceEndTime, nextComponentName);
        return Optional.of(nextEvent);
    }

    private boolean numberOfAttendantsIsNotInfinite() {
        return !numberOfAttendants.equals(INFINITE_NUMBER_OF_ATTENDANTS);
    }
}
