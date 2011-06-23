package de.hda.mus.neuronalnet;

import java.util.HashMap;

public class Neuron {
	
	
	//schwellwert
	private double threshold;
	
	private HashMap<Neuron, Double> preNeurons;
	
	public Neuron(double threshold){
		this.threshold = threshold;
		this.setPreNeurons(new HashMap<Neuron, Double>());
	}
	
	//Aktivierung
	public double activation(){
		return 0.0;
	}
	
	public void putPreNeuron(Neuron neuron, Double value) {
		preNeurons.put(neuron, value);
	}

	public void setThreshold(double threshold) {
		this.threshold = threshold;
	}

	public double getThreshold() {
		return threshold;
	}

	public void setPreNeurons(HashMap<Neuron, Double> preNeurons) {
		this.preNeurons = preNeurons;
	}

	public HashMap<Neuron, Double> getPreNeurons() {
		return preNeurons;
	}


}
