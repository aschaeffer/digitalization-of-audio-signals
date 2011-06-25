package de.hda.mus.neuronalnet;

import java.util.ArrayList;
import java.util.HashMap;

import de.hda.mus.neuronalnet.neuron.InputNeuron;
import de.hda.mus.neuronalnet.neuron.Neuron;

/**
 * Trains a multi layer perceptron.
 * 
 * @author aschaeffer
 * @author khalid
 *
 */
public class HebianTeacher {

	MLP multiLayerPerceptron;
	LearningPattern pattern = new LearningPattern();

	public HebianTeacher(MLP multiLayerPerceptron) {
		this.multiLayerPerceptron = multiLayerPerceptron;
	}

	public void teach(Integer maxIterations) {
		
	}

	public void addRule(ArrayList<Double> inputValues, ArrayList<Double> outputValues) {
		HashMap<InputNeuron, Double> inputPattern = new HashMap<InputNeuron, Double>();
		for (int i=0; i<inputValues.size(); i++) {
			inputPattern.put(this.multiLayerPerceptron.getInputLayer().get(i), inputValues.get(i));
		}
		HashMap<Neuron, Double> outputPattern = new HashMap<Neuron, Double>();
		for (int i=0; i<outputValues.size(); i++) {
			outputPattern.put(this.multiLayerPerceptron.getOutputLayer().get(i), outputValues.get(i));
		}
		this.pattern.add(inputPattern, outputPattern);
	}

	public void addRule(double[] inputValues, double[] outputValues) {
		HashMap<InputNeuron, Double> inputPattern = new HashMap<InputNeuron, Double>();
		for (int i=0; i<inputValues.length; i++) {
			inputPattern.put(this.multiLayerPerceptron.getInputLayer().get(i), inputValues[i]);
		}
		HashMap<Neuron, Double> outputPattern = new HashMap<Neuron, Double>();
		for (int i=0; i<outputValues.length; i++) {
			outputPattern.put(this.multiLayerPerceptron.getOutputLayer().get(i), outputValues[i]);
		}
		this.pattern.add(inputPattern, outputPattern);
	}

}
