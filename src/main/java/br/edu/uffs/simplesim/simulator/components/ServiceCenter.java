package br.edu.uffs.simplesim.simulator.components;

import br.edu.uffs.simplesim.simulator.components.configuration.ServiceCenterConfiguration;
import br.edu.uffs.simplesim.simulator.montecarlo.MonteCarlo;

import java.util.*;

public class ServiceCenter extends Component implements EventExecutor {
    public static final Integer INFINITE_NUMBER_OF_SERVANTS = -1;

    private final String nextComponentName;
    private final Integer numberOfServants;
    private final MonteCarlo monteCarlo;
    private final Queue<Servant> servantsQueue;

    private int eventsExecuted;
    private Double cumulativeWaitingTime;

    public ServiceCenter(ServiceCenterConfiguration serviceCenterConfiguration) {
        setName(serviceCenterConfiguration.getName());
        nextComponentName = serviceCenterConfiguration.getNextComponentName();
        numberOfServants = serviceCenterConfiguration.getNumberOfAttendants();
        monteCarlo = new MonteCarlo(serviceCenterConfiguration.getSamples(), serviceCenterConfiguration.getNumberOfClasses());
        servantsQueue = createServantsQueue(serviceCenterConfiguration.getNumberOfAttendants());
        eventsExecuted = 0;
        cumulativeWaitingTime = 0.0;
    }

    private Queue<Servant> createServantsQueue(Integer numberOfServants) {
        Queue<Servant> queue = new PriorityQueue<>();

        if (numberOfServants == INFINITE_NUMBER_OF_SERVANTS) {
            queue.addAll(createServants(1));
        } else {
            queue.addAll(createServants(numberOfServants));
        }

        return queue;
    }

    private Collection<Servant> createServants(Integer numberOfServants) {
        List<Servant> servants = new ArrayList<>();
        for (int id = 1; id <= numberOfServants; id++)
            servants.add(new Servant(String.valueOf(id)));
        return servants;
    }

    @Override
    public Optional<Event> execute(Event event) {
        Servant servant = servantsQueue.poll();

        Double serviceStartTime = Math.max(servant.getLastServiceEndTime(), event.getTime());
        Double totalServiceTime = monteCarlo.generateRandomSample();
        Double serviceEndTime = serviceStartTime + totalServiceTime;

        if (numberOfServants != INFINITE_NUMBER_OF_SERVANTS)
            servant.updateTimes(serviceStartTime, totalServiceTime);

        servantsQueue.add(servant);

        addWaitToStatistics(serviceStartTime - event.getTime());

        Event nextEvent = new Event(nextComponentName, serviceEndTime, event.getTemporaryEntity());
        return Optional.of(nextEvent);
    }

    private void addWaitToStatistics(double waitingTime) {
        cumulativeWaitingTime += waitingTime;
        eventsExecuted += 1;
    }

    @Override
    public String getStatistics() {
        String statistics = "";

        statistics += '*' + getName() + '*' + '\n';
        statistics += "Average service time: " + monteCarlo.getAverageGeneratedValue() + '\n';
        statistics += "Average wait time: " + cumulativeWaitingTime / eventsExecuted + '\n';

        return statistics;
    }
}
