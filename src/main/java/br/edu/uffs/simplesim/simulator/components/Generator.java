package br.edu.uffs.simplesim.simulator.components;

import br.edu.uffs.simplesim.simulator.components.configuration.GeneratorConfiguration;
import br.edu.uffs.simplesim.simulator.montecarlo.MonteCarlo;

import java.util.ArrayList;
import java.util.List;

public class Generator extends Component {
    private static int temporaryEntitiesGenerated = 0;

    private final String nextComponentName;
    private final Integer numberOfTemporaryUnitsToGenerate;

    private final MonteCarlo monteCarlo;

    public Generator(GeneratorConfiguration temporaryUnitGeneratorsConfigurations) {
        setName(temporaryUnitGeneratorsConfigurations.getName());
        nextComponentName = temporaryUnitGeneratorsConfigurations.getNextComponentName();
        monteCarlo = new MonteCarlo(temporaryUnitGeneratorsConfigurations.getSamples(), temporaryUnitGeneratorsConfigurations.getNumberOfClasses());
        numberOfTemporaryUnitsToGenerate = temporaryUnitGeneratorsConfigurations.getNumberOfTemporaryUnitsToGenerate();
    }

    public List<Event> generateArrivalEvents() {
        List<Event> generatedEvents = new ArrayList<>();
        for (int i = 0; i < numberOfTemporaryUnitsToGenerate; i++) {
            generatedEvents.add(generateNewArrivalEvent());
        }
        return generatedEvents;
    }

    private Event generateNewArrivalEvent(){
        Double arrivalTime = monteCarlo.generateRandomSample();
        TemporaryEntity temporaryEntity = new TemporaryEntity(nextTemporaryEntityId(), arrivalTime);
        return new Event(nextComponentName, arrivalTime, temporaryEntity);
    }

    private int nextTemporaryEntityId() {
        temporaryEntitiesGenerated += 1;
        return temporaryEntitiesGenerated;
    }

    @Override
    public String getStatistics() {
        return "TODO";
    }
}
