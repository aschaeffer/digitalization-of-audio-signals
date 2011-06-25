package de.hda.mus.neuronalnet;

import java.util.HashMap;

import de.hda.mus.neuronalnet.neuron.InputNeuron;
import de.hda.mus.neuronalnet.neuron.OutputNeuron;

public class LearningPattern {

	HashMap<HashMap<InputNeuron, Double>, HashMap<OutputNeuron, Double>> pattern = new HashMap<HashMap<InputNeuron, Double>, HashMap<OutputNeuron, Double>>();
	
	public void add(HashMap<InputNeuron, Double> inputPattern, HashMap<OutputNeuron, Double> outputPattern) {
		pattern.put(inputPattern, outputPattern);
	}

}
