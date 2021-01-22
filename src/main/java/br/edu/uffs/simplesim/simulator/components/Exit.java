package br.edu.uffs.simplesim.simulator.components;

import br.edu.uffs.simplesim.simulator.components.configuration.ExitConfiguration;
import br.edu.uffs.simplesim.simulator.utils.Utils;

import java.util.Optional;

public class Exit extends EventExecutor {
    private int temporaryEntitiesExited;
    private Double cumulativePermanenceTime;

    public Exit(ExitConfiguration exitConfiguration) {
        setName(exitConfiguration.getName());
        temporaryEntitiesExited = 0;
        cumulativePermanenceTime = 0.0;
    }

    @Override
    public Optional<Event> executeNextEvent() {
        Event exitEvent = eventsQueue.poll();
        addEventToStatistics(exitEvent);
        return Optional.empty(); // Always return empty because the temporary entity exited the simulation
    }

    private void addEventToStatistics(Event exitEvent) {
        temporaryEntitiesExited += 1;
        cumulativePermanenceTime += exitEvent.getTime() - exitEvent.getTemporaryEntity().getArrivalTime();
        System.out.println(Utils.formatDouble(exitEvent.getTime()) + " " + getName() + " EXIT ");
    }

    @Override
    public String getStatistics(Double endOfSimulationTime) {
        String statistics = "";

        statistics += '*' + getName() + '*' + '\n';
        statistics += "Number of temporary entities that passed thought this exit: " + temporaryEntitiesExited + '\n';
        statistics += "Average time spent by temporary entities in the model: " + Utils.formatDouble(cumulativePermanenceTime/temporaryEntitiesExited) + '\n';

        return statistics;
    }
}
