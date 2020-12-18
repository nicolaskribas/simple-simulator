package br.edu.uffs.simplesim.simulator.montecarlo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MonteCarlo {
    private Integer cumulativeFrequency;
    private final List<Class<Double>> classes = new ArrayList<>();
    private final Random random = new Random();
    private Double cumulativeGeneratedValues;
    private int numberOfGeneratedValues;

    public MonteCarlo(List<Double> samples, Integer numberOfClasses) {
        cumulativeGeneratedValues = 0.0;
        numberOfGeneratedValues = 0;

        Collections.sort(samples);
        double lowestSample = samples.get(0);
        double highestSample = samples.get(samples.size() - 1);
        double totalRange = highestSample - lowestSample;
        double classRange = totalRange / numberOfClasses;

        for (int i = 0; i < numberOfClasses; i++) {
            Double midpoint = lowestSample + classRange * i + classRange / 2;
            classes.add(new Class<>(midpoint));
        }
        for (Double sample : samples) {
            int index = (int) ((sample - lowestSample) / classRange);
            if (sample.equals(highestSample)) index -= 1;
            Class<Double> aClass = classes.get(index);
            aClass.addOneToFrequency();
        }

        cumulativeFrequency = 0;
        for (Class<Double> aClass : classes) {
            cumulativeFrequency += aClass.getFrequency();
            aClass.setCumulativeFrequency(cumulativeFrequency);
        }
        if (cumulativeFrequency.compareTo(samples.size()) != 0)
            throw new RuntimeException("Cumulative frequency is not equal to the number of samples, this should never happen!");
    }


    public Double generateRandomSample() {
        int randomInteger = random.nextInt(cumulativeFrequency) + 1;

        for(Class<Double> aClass : classes){
            if(randomInteger <= aClass.getCumulativeFrequency()){
                addToCumulativeGeneratedValues(aClass.getMidpoint());
                return aClass.getMidpoint();
            }
        }
        throw new RuntimeException("Invalid random number, this should never happen!");
    }

    private void addToCumulativeGeneratedValues(Double midpoint) {
        numberOfGeneratedValues += 1;
        cumulativeGeneratedValues += midpoint;
    }

    public Double getAverageGeneratedValue(){
        return cumulativeGeneratedValues/numberOfGeneratedValues;
    }
}
