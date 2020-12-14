package br.edu.uffs.simplesim.simulator.components;

import br.edu.uffs.simplesim.simulator.components.configuration.RouterConfiguration;
import br.edu.uffs.simplesim.simulator.configuration.NextComponentProbability;
import br.edu.uffs.simplesim.simulator.montecarlo.Class;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;


public class Router extends Component implements EventExecutor {

    private int cumulativeFrequency;
    private final List<Class<String>> classes = new ArrayList<>();
    private final Random random = new Random();

    public Router(RouterConfiguration routerConfiguration) {
        super.setName(routerConfiguration.getName());

        cumulativeFrequency = 0;
        for (NextComponentProbability nextComponentProbability : routerConfiguration.getNextComponentsProbabilities()) {
            Long frequencyAsLong = Math.round(nextComponentProbability.getProbability() * 100);
            int frequency = frequencyAsLong.intValue();
            cumulativeFrequency += frequency;
            Class<String> aClass = new Class<String>(nextComponentProbability.getName(), frequency, cumulativeFrequency);
            classes.add(aClass);
        }
    }

    @Override
    public Optional<Event> execute(Event event) {
        int randomInteger = random.nextInt(cumulativeFrequency) + 1;

        System.out.println("Random: "+ randomInteger);
        for(Class<String> aClass : classes){
            if(randomInteger <= aClass.getCumulativeFrequency()){
                Event nextEvent = new Event(event.getTime(), aClass.getMidpoint());
                return Optional.of(nextEvent);
            }
        }
        return Optional.empty();
    }
}
