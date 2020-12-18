package br.edu.uffs.simplesim.simulator.configurationbeans;

import br.edu.uffs.simplesim.simulator.components.configuration.ServiceCenterConfiguration;
import br.edu.uffs.simplesim.simulator.components.configuration.ExitConfiguration;
import br.edu.uffs.simplesim.simulator.components.configuration.GeneratorConfiguration;
import br.edu.uffs.simplesim.simulator.components.configuration.RouterConfiguration;

import java.util.List;

public class Configuration {
    private List<GeneratorConfiguration> generatorsConfigurations;
    private List<ServiceCenterConfiguration> serviceCentersConfigurations;
    private List<RouterConfiguration> routersConfigurations;
    private List<ExitConfiguration> exitsConfigurations;
    private Integer maxSimulationTime;

    public List<GeneratorConfiguration> getGeneratorsConfigurations() {
        return generatorsConfigurations;
    }

    public void setGeneratorsConfigurations(List<GeneratorConfiguration> generatorsConfigurations) {
        this.generatorsConfigurations = generatorsConfigurations;
    }

    public List<ServiceCenterConfiguration> getServiceCentersConfigurations() {
        return serviceCentersConfigurations;
    }

    public void setServiceCentersConfigurations(List<ServiceCenterConfiguration> serviceCentersConfigurations) {
        this.serviceCentersConfigurations = serviceCentersConfigurations;
    }

    public List<RouterConfiguration> getRoutersConfigurations() {
        return routersConfigurations;
    }

    public void setRoutersConfigurations(List<RouterConfiguration> routersConfigurations) {
        this.routersConfigurations = routersConfigurations;
    }

    public List<ExitConfiguration> getExitsConfigurations() {
        return exitsConfigurations;
    }

    public void setExitsConfigurations(List<ExitConfiguration> exitsConfigurations) {
        this.exitsConfigurations = exitsConfigurations;
    }

    public Integer getMaxSimulationTime() {
        return maxSimulationTime;
    }

    public void setMaxSimulationTime(Integer maxSimulationTime) {
        this.maxSimulationTime = maxSimulationTime;
    }
}
