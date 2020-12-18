package br.edu.uffs.simplesim.simulator.components;

import br.edu.uffs.simplesim.simulator.components.configuration.ServiceCenterConfiguration;
import br.edu.uffs.simplesim.simulator.montecarlo.MonteCarlo;
import br.edu.uffs.simplesim.simulator.components.Attendant.Type;

import java.util.*;

public class ServiceCenter extends Component implements EventExecutor {
    public static final Integer INFINITE_NUMBER_OF_ATTENDANTS = -1;

    private final String nextComponentName;
    private final Integer numberOfAttendants;
    private final MonteCarlo monteCarlo;
    private final Queue<Attendant> attendantsQueue;

    public ServiceCenter(ServiceCenterConfiguration serviceCenterConfiguration) {
        super.setName(serviceCenterConfiguration.getName());
        nextComponentName = serviceCenterConfiguration.getNextComponentName();
        numberOfAttendants = serviceCenterConfiguration.getNumberOfAttendants();
        monteCarlo = new MonteCarlo(serviceCenterConfiguration.getSamples(), serviceCenterConfiguration.getNumberOfClasses());
        attendantsQueue = createAttendantsQueue(serviceCenterConfiguration.getNumberOfAttendants());
    }

    private Queue<Attendant> createAttendantsQueue(Integer numberOfAttendants) {
        Queue<Attendant> queue = new PriorityQueue<>();

        if(numberOfAttendants == INFINITE_NUMBER_OF_ATTENDANTS){
            queue.add(new Attendant(1, Type.INFINITE));
        }else{
            for(Integer id = 1; id <= numberOfAttendants; id++){
                queue.add(new Attendant(id, Type.UNITARY));
            }
        }
        return queue;
    }

    @Override
    public Optional<Event> execute(Event event) {
        Attendant attendant = attendantsQueue.poll();
        Double attendanceTime = monteCarlo.generateRandomSample();
        Double attendanceEndTime = attendant.attend(event, attendanceTime);
        attendantsQueue.add(attendant);

        Event nextEvent = new Event(attendanceEndTime, nextComponentName);
        return Optional.of(nextEvent);
    }

    @Override
    public String getStatistics() {
        return monteCarlo.getAverageGeneratedValue() + "\n";
    }
}
