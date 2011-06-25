package de.hda.mus.neuronalnet;

import java.util.HashMap;

import de.hda.mus.neuronalnet.neuron.InputNeuron;
import de.hda.mus.neuronalnet.neuron.Neuron;

public class LearningPattern {

	HashMap<HashMap<InputNeuron, Double>, HashMap<Neuron, Double>> pattern = new HashMap<HashMap<InputNeuron, Double>, HashMap<Neuron, Double>>();
	
	public void add(HashMap<InputNeuron, Double> inputPattern, HashMap<Neuron, Double> outputPattern) {
		pattern.put(inputPattern, outputPattern);
	}

}
