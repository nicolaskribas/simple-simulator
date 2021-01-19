package br.edu.uffs.simplesim.simulator;

import br.edu.uffs.simplesim.simulator.components.*;
import br.edu.uffs.simplesim.simulator.components.configuration.ServiceCenterConfiguration;
import br.edu.uffs.simplesim.simulator.components.configuration.ExitConfiguration;
import br.edu.uffs.simplesim.simulator.components.configuration.GeneratorConfiguration;
import br.edu.uffs.simplesim.simulator.components.configuration.RouterConfiguration;
import br.edu.uffs.simplesim.simulator.configurationbeans.*;

import java.util.*;

public class Simulator {
    private final Integer maxSimulationTime;
    private final Queue<Event> eventsQueue = new PriorityQueue<>();

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
        for (ServiceCenter serviceCenter : serviceCenters) eventExecutorsMapping.put(serviceCenter.getName(), serviceCenter);
        for (Router router : routers) eventExecutorsMapping.put(router.getName(), router);
        for (Exit exit : exits) eventExecutorsMapping.put(exit.getName(), exit);
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
        while (!eventsQueue.isEmpty()) {
            executeNextEvent();
        }
    }

    private void executeNextEvent() {
        Event event = eventsQueue.poll();
        Optional<Event> resultEvent = executeEvent(event);
        if (resultEvent.isPresent()) queuesEventIfItIsAfterMaxSimulationTime(resultEvent.get());
    }

    private Optional<Event> executeEvent(Event event) {
        String eventExecutorInChargeName = event.getEventExecutorInChargeName();
        EventExecutor eventExecutorInCharge = eventExecutorsMapping.get(eventExecutorInChargeName);
        return eventExecutorInCharge.execute(event);
    }

    private void queuesEventIfItIsAfterMaxSimulationTime(Event event) {
        if (event.getTime() < maxSimulationTime) eventsQueue.add(event);
    }

    private void generateTemporaryUnitsEvents() {
        for (Generator generator : generators) {
            eventsQueue.addAll(generator.generateArrivalEvents());
        }
    }

    public String getMetricsAsString() {
        String metrics = "";

        for (Generator generator : generators) metrics += generator.getStatistics() + '\n';
        for (ServiceCenter serviceCenter : serviceCenters) metrics += serviceCenter.getStatistics() + '\n';
        for (Router router : routers) metrics += router.getStatistics() + '\n';
        for (Exit exit: exits) metrics += exit.getStatistics() + '\n';

        return metrics;
    }
}
