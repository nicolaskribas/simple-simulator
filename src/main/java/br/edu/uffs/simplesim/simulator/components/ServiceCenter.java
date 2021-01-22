package br.edu.uffs.simplesim.simulator.components;

import br.edu.uffs.simplesim.simulator.components.configuration.ServiceCenterConfiguration;
import br.edu.uffs.simplesim.simulator.montecarlo.MonteCarlo;
import br.edu.uffs.simplesim.simulator.utils.Utils;

import java.util.*;

public class ServiceCenter extends EventExecutor {
    public static final Integer INFINITE_NUMBER_OF_SERVANTS = -1;

    private final String nextComponentName;
    private final Integer numberOfServants;
    private final MonteCarlo monteCarlo;
    private final Queue<Servant> servantsQueue;

    private int eventsExecuted;
    private Double cumulativeWaitingTime;
    private Double maxWaitingTime;
    private int cumulativeSizeOfQueue;

    public ServiceCenter(ServiceCenterConfiguration serviceCenterConfiguration) {
        setName(serviceCenterConfiguration.getName());
        nextComponentName = serviceCenterConfiguration.getNextComponentName();
        numberOfServants = serviceCenterConfiguration.getNumberOfAttendants();
        monteCarlo = new MonteCarlo(serviceCenterConfiguration.getSamples(), serviceCenterConfiguration.getNumberOfClasses());
        servantsQueue = createServantsQueue(serviceCenterConfiguration.getNumberOfAttendants());
        eventsExecuted = 0;
        cumulativeWaitingTime = 0.0;
        cumulativeSizeOfQueue = 0;
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
    public Optional<Event> executeNextEvent() {
        Event event = eventsQueue.poll();
        Servant servant = servantsQueue.poll();

        Double serviceStartTime = Math.max(servant.getLastServiceEndTime(), event.getTime());
        Double totalServiceTime = monteCarlo.generateRandomSample();
        Double serviceEndTime = serviceStartTime + totalServiceTime;

        if (numberOfServants != INFINITE_NUMBER_OF_SERVANTS)
            servant.updateTimes(serviceStartTime, totalServiceTime);

        servantsQueue.add(servant);

        addWaitToStatistics(serviceStartTime - event.getTime(), serviceStartTime);

        Event nextEvent = new Event(nextComponentName, serviceEndTime, event.getTemporaryEntity());
        return Optional.of(nextEvent);
    }

    private void addWaitToStatistics(double waitingTime, double currentTime) {
        cumulativeWaitingTime += waitingTime;
        eventsExecuted += 1;
        maxWaitingTime = Math.max(maxWaitingTime, waitingTime);
        cumulativeSizeOfQueue += countSizeOfTheQueueBefore(currentTime);

        System.out.println(Utils.formatDouble(currentTime) + " " + getName() + " queue: " + countSizeOfTheQueueBefore(currentTime));
    }

    private int countSizeOfTheQueueBefore(double currentTime) {
        int count = 0;
        for (Event event : eventsQueue) {
            if (event.getTime() <= currentTime) {
                count += 1;
            }
        }
        return count;
    }

    @Override
    public String getStatistics(Double endOfSimulationTime) {
        System.out.println(getName() + "Events executed: " + eventsExecuted);

        String statistics = "";

        statistics += '*' + getName() + '*' + '\n';
        statistics += "Average service time: " + Utils.formatDouble(monteCarlo.getAverageGeneratedValue()) + '\n';
        statistics += "Average waiting time: " + Utils.formatDouble(cumulativeWaitingTime / eventsExecuted) + '\n';
        statistics += "Max waiting time: " + Utils.formatDouble(maxWaitingTime) + '\n';
        if(numberOfServants != INFINITE_NUMBER_OF_SERVANTS) {
            statistics += "Idle time of each servant: (id of servant - idle time)" + getIdleTimeAsString(endOfSimulationTime) + '\n';
            statistics += "Average idle time of servants: " + Utils.formatDouble(getAverageIdleTime(endOfSimulationTime)) + '\n';
            statistics += "Average size of queue: " + Utils.formatDouble((double) cumulativeSizeOfQueue / eventsExecuted) + '\n';
        }

        return statistics;
    }


    private String getIdleTimeAsString(Double endOfSimulationTime) {
        String idleTimes = "";
        for (Servant servant : servantsQueue)
            idleTimes += " | " + servant.getStatistics(endOfSimulationTime);

        return idleTimes;
    }

    private Double getAverageIdleTime(Double endOfSimulationTime) {
        Double idleTimeSum = 0.0;
        for (Servant servant : servantsQueue)
            idleTimeSum += servant.getIdleTime(endOfSimulationTime);
        return idleTimeSum / servantsQueue.size();
    }
}
