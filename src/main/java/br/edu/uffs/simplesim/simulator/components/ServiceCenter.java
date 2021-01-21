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
    private Double maxWaitingTime;

    public ServiceCenter(ServiceCenterConfiguration serviceCenterConfiguration) {
        setName(serviceCenterConfiguration.getName());
        nextComponentName = serviceCenterConfiguration.getNextComponentName();
        numberOfServants = serviceCenterConfiguration.getNumberOfAttendants();
        monteCarlo = new MonteCarlo(serviceCenterConfiguration.getSamples(), serviceCenterConfiguration.getNumberOfClasses());
        servantsQueue = createServantsQueue(serviceCenterConfiguration.getNumberOfAttendants());
        eventsExecuted = 0;
        cumulativeWaitingTime = 0.0;
        maxWaitingTime = 0.0;
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
        maxWaitingTime = Math.max(maxWaitingTime, waitingTime);
    }

    @Override
    public String getStatistics() {
        System.out.println(getName() + "Events executed: " + eventsExecuted);

        String statistics = "";

        statistics += '*' + getName() + '*' + '\n';
        statistics += "Average service time: " + monteCarlo.getAverageGeneratedValue() + '\n';
        statistics += "Average waiting time: " + cumulativeWaitingTime / eventsExecuted + '\n';
        statistics += "Max waiting time: " + maxWaitingTime + '\n';
        statistics += "Idle time of each servant: (id of servant - idle time)" + getIdleTimeAsString() + '\n';
        statistics += "Average idle time of servants: " + getAverageIdleTime() + '\n';

        return statistics;
    }



    private String getIdleTimeAsString() {
        String idleTimes = "";
        for (Servant servant : servantsQueue)
            idleTimes += " | " + servant.getStatistics();

        return idleTimes;
    }
    private Double getAverageIdleTime() {
        Double idleTimeSum = 0.0;
        for(Servant servant : servantsQueue)
            idleTimeSum += servant.getIdleTime();
        return idleTimeSum / servantsQueue.size();
    }
}
