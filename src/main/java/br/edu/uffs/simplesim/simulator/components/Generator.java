package br.edu.uffs.simplesim.simulator.components;

import br.edu.uffs.simplesim.simulator.components.configuration.GeneratorConfiguration;
import br.edu.uffs.simplesim.simulator.montecarlo.MonteCarlo;

import java.util.ArrayList;
import java.util.List;

public class Generator extends Component {

    private final String nextComponentName;
    private final Integer numberOfTemporaryUnitsToGenerate;

    private final MonteCarlo monteCarlo;

    public Generator(GeneratorConfiguration temporaryUnitGeneratorsConfigurations) {
        super.setName(temporaryUnitGeneratorsConfigurations.getName());
        nextComponentName = temporaryUnitGeneratorsConfigurations.getNextComponentName();
        monteCarlo = new MonteCarlo(temporaryUnitGeneratorsConfigurations.getSamples(), temporaryUnitGeneratorsConfigurations.getNumberOfClasses());
        numberOfTemporaryUnitsToGenerate = temporaryUnitGeneratorsConfigurations.getNumberOfTemporaryUnitsToGenerate();
    }

    public List<Event> generateEvents() {
        List<Event> generatedEvents = new ArrayList<>();
        for (int i = 0; i < numberOfTemporaryUnitsToGenerate; i++) {
            generatedEvents.add(new Event(monteCarlo.generateRandomSample(), nextComponentName));
        }
        return generatedEvents;
    }
}
