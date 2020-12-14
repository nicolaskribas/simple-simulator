package br.edu.uffs.simplesim.simulator.components;

import br.edu.uffs.simplesim.simulator.components.configuration.RouterConfiguration;
import br.edu.uffs.simplesim.simulator.configuration.NextComponentProbability;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class RouterTest {

    @Test
    public void execute() {
        RouterConfiguration routerConfiguration = new RouterConfiguration();
        routerConfiguration.setName("saida_ou_medicos");

        NextComponentProbability nextComponentProbability1 = new NextComponentProbability();
        nextComponentProbability1.setName("saida");
        nextComponentProbability1.setProbability(0.3);

        NextComponentProbability nextComponentProbability2 = new NextComponentProbability();
        nextComponentProbability2.setName("medicos");
        nextComponentProbability2.setProbability(0.7);

        List<NextComponentProbability> nextComponentProbabilities= new ArrayList<>();
        nextComponentProbabilities.add(nextComponentProbability2);
        nextComponentProbabilities.add(nextComponentProbability1);


        routerConfiguration.setNextComponentsProbabilities(nextComponentProbabilities);

        Router router = new Router(routerConfiguration);
        router.execute(new Event(12.2, "sla"));
    }
}