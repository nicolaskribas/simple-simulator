package br.edu.uffs.simplesim;

import br.edu.uffs.simplesim.simulator.Simulator;
import br.edu.uffs.simplesim.simulator.components.ServiceCenter;
import br.edu.uffs.simplesim.simulator.components.TemporaryUnitExit;
import br.edu.uffs.simplesim.simulator.components.TemporaryUnitGenerator;
import br.edu.uffs.simplesim.simulator.components.TemporaryUnitRouter;

import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.*;

public class App {
    public static void main(String[] args) throws IOException {
        Reader reader = null;
        try {
            reader = new FileReader("simconf.yaml");
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            return;
        }
//
//        Constructor constructor = new Constructor(Simulator.class);
//        TypeDescription customTypeDescription = new TypeDescription(Simulator.class);

//        customTypeDescription.addPropertyParameters("temporaryUnitGenerators", TemporaryUnitGenerator.class);
//        customTypeDescription.addPropertyParameters("serviceCenters", ServiceCenter.class);
//        customTypeDescription.addPropertyParameters("temporaryUnitRouters", TemporaryUnitRouter.class);
//        customTypeDescription.addPropertyParameters("temporaryUnitExits", TemporaryUnitExit.class);
//        constructor.addTypeDescription(customTypeDescription);
//        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("simconf.yamml");

        Yaml yaml = new Yaml(new Constructor(Simulator.class));
        Simulator simulator = yaml.load(reader);
        for (TemporaryUnitGenerator temporaryUnitGenerator : simulator.getTemporaryUnitGenerators()) {
            System.out.println(temporaryUnitGenerator.getName());
        }
        for (ServiceCenter serviceCenter : simulator.getServiceCenters()) {
            System.out.println(serviceCenter.getName());
        }
        for (TemporaryUnitRouter temporaryUnitRouter : simulator.getTemporaryUnitRouters()) {
            System.out.println(temporaryUnitRouter.getName());
        }
        for (TemporaryUnitExit temporaryUnitExit : simulator.getTemporaryUnitExits()) {
            System.out.println(temporaryUnitExit.getName());
        }
    }
}
