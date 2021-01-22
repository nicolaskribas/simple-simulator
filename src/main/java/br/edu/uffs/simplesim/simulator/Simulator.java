package br.edu.uffs.simplesim.simulator;

import br.edu.uffs.simplesim.simulator.components.*;
import br.edu.uffs.simplesim.simulator.components.configuration.ServiceCenterConfiguration;
import br.edu.uffs.simplesim.simulator.components.configuration.ExitConfiguration;
import br.edu.uffs.simplesim.simulator.components.configuration.GeneratorConfiguration;
import br.edu.uffs.simplesim.simulator.components.configuration.RouterConfiguration;
import br.edu.uffs.simplesim.simulator.configurationbeans.*;

import java.util.*;

public class Simulator {
    private final Double maxSimulationTime;
    private final List<EventExecutor> eventExecutorsQueue = new ArrayList<>();

    private final List<Generator> generators;
    private final List<ServiceCenter> serviceCenters;
    private final List<Router> routers;
    private final List<Exit> exits;

    private final Map<String, EventExecutor> eventExecutorsMapping;


    public Simulator(Configuration configuration) {
        maxSimulationTime = configuration.getMaxSimulationTime();
        generators = createTemporaryUnitGenerators(configuration.getGeneratorsConfigurations());
        serviceCenters = createServiceCenters(configuration.getServiceCentersConfigurations());
        routers = createTemporaryUnitRouters(configuration.getRoutersConfigurations());
        exits = createTemporaryUnitExits(configuration.getExitsConfigurations());

        eventExecutorsMapping = new TreeMap<>();
        for (ServiceCenter serviceCenter : serviceCenters)
            eventExecutorsMapping.put(serviceCenter.getName(), serviceCenter);
        for (Router router : routers)
            eventExecutorsMapping.put(router.getName(), router);
        for (Exit exit : exits)
            eventExecutorsMapping.put(exit.getName(), exit);
    }

    private List<Exit> createTemporaryUnitExits(List<ExitConfiguration> temporaryUnitExitsConfigurations) {
        List<Exit> exits = new ArrayList<>();
        for (ExitConfiguration exitConfiguration : temporaryUnitExitsConfigurations) {
            exits.add(new Exit(exitConfiguration));
        }
        return exits;
    }

    private List<Router> createTemporaryUnitRouters(List<RouterConfiguration> temporaryUnitRoutersConfigurations) {
        List<Router> routers = new ArrayList<>();
        for (RouterConfiguration routerConfiguration : temporaryUnitRoutersConfigurations) {
            routers.add(new Router(routerConfiguration));
        }
        return routers;
    }

    private List<ServiceCenter> createServiceCenters(List<ServiceCenterConfiguration> serviceCentersConfigurations) {
        List<ServiceCenter> serviceCenters = new ArrayList<>();
        for (ServiceCenterConfiguration serviceCenterConfiguration : serviceCentersConfigurations) {
            serviceCenters.add(new ServiceCenter(serviceCenterConfiguration));
        }
        return serviceCenters;
    }

    private List<Generator> createTemporaryUnitGenerators(List<GeneratorConfiguration> generatorConfigurations) {
        List<Generator> generators = new ArrayList<>();
        for (GeneratorConfiguration generatorConfiguration : generatorConfigurations) {
            generators.add(new Generator(generatorConfiguration));
        }
        return generators;
    }

    public void simulate() {
        generateTemporaryUnitsEvents();
        while (!eventExecutorsQueue.isEmpty()) {
            executeNextEvent();
        }
    }

    private void executeNextEvent() {
        EventExecutor eventExecutor = eventExecutorsQueue.remove(0);

        Optional<Event> resultEvent = eventExecutor.executeNextEvent();
        if (resultEvent.isPresent()) enqueueEventIfItIsAfterMaxSimulationTime(resultEvent.get());

        if (eventExecutor.hasEventsLeftInTheQueue())
            eventExecutorsQueue.add(eventExecutor);

        Collections.sort(eventExecutorsQueue);
    }

    private void enqueueEventIfItIsAfterMaxSimulationTime(Event event) {
        if (event.getTime() < maxSimulationTime) {
            enqueueEvent(event);
        }
    }

    private void enqueueEvent(Event event) {
        String eventExecutorInChargeName = event.getEventExecutorInChargeName();
        EventExecutor executorInCharge = eventExecutorsMapping.get(eventExecutorInChargeName);

        if (executorInCharge.hasNoEventsLeftInTheQueue()) {
            executorInCharge.enqueueEvent(event);
            eventExecutorsQueue.add(executorInCharge);
        } else {
            executorInCharge.enqueueEvent(event);
        }
        Collections.sort(eventExecutorsQueue);
    }

    private void generateTemporaryUnitsEvents() {
        for (Generator generator : generators) {
            for (Event event : generator.generateArrivalEvents()) {
                enqueueEvent(event);
            }
        }
    }

    public String getStatisticsAsString() {
        String statistics = "";

        for (ServiceCenter serviceCenter : serviceCenters)
            statistics += serviceCenter.getStatistics(maxSimulationTime) + '\n';
        for (Exit exit : exits) statistics += exit.getStatistics(maxSimulationTime) + '\n';

        return statistics;
    }
}
