package de.hda.mus.neuronalnet;

import java.util.HashMap;

public class LearningPattern {

	HashMap<HashMap<InputNeuron, Double>, HashMap<Neuron, Double>> pattern = new HashMap<HashMap<InputNeuron, Double>, HashMap<Neuron, Double>>();
	
	public void add(HashMap<InputNeuron, Double> inputPattern, HashMap<Neuron, Double> outputPattern) {
		pattern.put(inputPattern, outputPattern);
	}

}
