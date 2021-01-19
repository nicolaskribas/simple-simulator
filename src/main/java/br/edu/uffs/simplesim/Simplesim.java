package br.edu.uffs.simplesim;

import br.edu.uffs.simplesim.simulator.Simulator;
import br.edu.uffs.simplesim.simulator.configurationbeans.*;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.*;


public class Simplesim {
    public static final String CONFIGURATION_FILE_NAME = "simconf.yaml";
    public static final String RESULTS_FILE_NAME = "results.txt";

    public static void main(String[] args) {
        Configuration configuration = getConfigurationFromYamlFile();
        Simulator simulator = new Simulator(configuration);
        simulator.simulate();
        writeResultsOnFile(simulator.getMetricsAsString());
    }

    public static Configuration getConfigurationFromYamlFile() {
        Reader reader = getConfigurationFileReader();
        Yaml yaml = new Yaml(new Constructor(Configuration.class));
        Configuration configuration = yaml.load(reader);
        return configuration;
    }

    public static Reader getConfigurationFileReader() {
        try {
            return new FileReader(CONFIGURATION_FILE_NAME);
        } catch (FileNotFoundException exception) {
            throw new RuntimeException("Configuration file not found!");
        }
    }

    private static void writeResultsOnFile(String results) {
        try {
            Writer writer = new FileWriter(RESULTS_FILE_NAME);
            writer.write(results);
            writer.close();
        } catch (IOException exception) {
            throw new RuntimeException("Error writing the simulation result to the file!");
        }
    }
}
