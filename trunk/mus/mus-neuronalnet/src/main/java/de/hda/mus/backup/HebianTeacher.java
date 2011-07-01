package de.hda.mus.backup;

import java.util.ArrayList;
import java.util.HashMap;

import de.hda.mus.neuronalnet.neuron.InputNeuron;
import de.hda.mus.neuronalnet.neuron.OutputNeuron;

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
//	double max_error = 0.1;
//	int[][] pattern = { { 0, 0, 1 }, { 1, 1, 1 }, { 1, 0, 0 }, { 0, 1, 0 } };
//	double learnStep_eta = 0.8;
//	double momentum_alpha = 0.9;
//	boolean batch_update = true;

	public void teach(Double maxError, Double learnStep_eta, Double momentum_alpha, Integer maxIterations) {
		Integer currentIteration = 0;
		Boolean done = false;
		while ((currentIteration < maxIterations) && !done) {
			
			
			// increase iteration counter
			currentIteration++;
		}
		
	}

	public void addRule(ArrayList<Double> inputValues, ArrayList<Double> outputValues) {
		HashMap<InputNeuron, Double> inputPattern = new HashMap<InputNeuron, Double>();
		for (int i=0; i<inputValues.size(); i++) {
			inputPattern.put(this.multiLayerPerceptron.getInputLayer().get(i), inputValues.get(i));
		}
		HashMap<OutputNeuron, Double> outputPattern = new HashMap<OutputNeuron, Double>();
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
		HashMap<OutputNeuron, Double> outputPattern = new HashMap<OutputNeuron, Double>();
		for (int i=0; i<outputValues.length; i++) {
			outputPattern.put(this.multiLayerPerceptron.getOutputLayer().get(i), outputValues[i]);
		}
		this.pattern.add(inputPattern, outputPattern);
	}

}
