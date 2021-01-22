package br.edu.uffs.simplesim.simulator.components;

import br.edu.uffs.simplesim.simulator.components.configuration.RouterConfiguration;
import br.edu.uffs.simplesim.simulator.configurationbeans.NextComponentProbability;
import br.edu.uffs.simplesim.simulator.montecarlo.Class;
import br.edu.uffs.simplesim.simulator.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;


public class Router extends EventExecutor {

    private int cumulativeFrequency;
    private final List<Class<String>> classes = new ArrayList<>();
    private final Random random = new Random();

    public Router(RouterConfiguration routerConfiguration) {
        setName(routerConfiguration.getName());

        cumulativeFrequency = 0;
        for (NextComponentProbability nextComponentProbability : routerConfiguration.getNextComponentsProbabilities()) {
            Long frequencyAsLong = Math.round(nextComponentProbability.getProbability() * 100);
            int frequency = frequencyAsLong.intValue();
            cumulativeFrequency += frequency;
            Class<String> aClass = new Class<>(nextComponentProbability.getName(), frequency, cumulativeFrequency);
            classes.add(aClass);
        }
    }

    @Override
    public Optional<Event> executeNextEvent() {
        Event event = eventsQueue.poll();
        int randomInteger = random.nextInt(cumulativeFrequency) + 1;

        System.out.println(Utils.formatDouble(event.getTime()) + " " + getName() + " ROUTER");

        for (Class<String> aClass : classes) {
            if (randomInteger <= aClass.getCumulativeFrequency()) {
                Event nextEvent = new Event(aClass.getMidpoint(), event.getTime(), event.getTemporaryEntity());
                return Optional.of(nextEvent);
            }
        }
        throw new RuntimeException("Invalid random number on " + getName());
    }

    @Override
    public String getStatistics(Double endOfSimulationTime) {
        return "TODO";
    }
}
