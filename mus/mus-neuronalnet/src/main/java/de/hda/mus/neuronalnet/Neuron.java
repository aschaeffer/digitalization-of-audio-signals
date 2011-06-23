package de.hda.mus.neuronalnet;

import java.util.HashMap;

public class Neuron {
	
	
	//schwellwert
	private double threshold;
	
	private HashMap<Neuron, Double> adjacentNeurons;
	
	public Neuron(double threshold){
		this.threshold = threshold;
//		this.adjacentNeurons = new HashMap<Neuron, Double>();
	}
	
	//Aktivierung
	public double activation(){
		return 0.0;
	}
	
	public void putAdjacentNeuron(Neuron neuron, Double value) {
		adjacentNeurons.put(neuron, value);
	}

	public void setThreshold(double threshold) {
		this.threshold = threshold;
	}

	public double getThreshold() {
		return threshold;
	}

	public void setAdjacentNeurons(HashMap<Neuron, Double> adjacentNeurons) {
		this.adjacentNeurons = adjacentNeurons;
	}

	public HashMap<Neuron, Double> getAdjacentNeurons() {
		return adjacentNeurons;
	}

}
