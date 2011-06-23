package de.hda.mus.neuronalnet;

public class Neuron {
	
	
	//schwellwert
	private double threshold;
	
	public Neuron(double threshold){
		this.threshold = threshold;
	}
	
	//Aktivierung
	public double activation(){
		return 0.0;
	}

	public void setThreshold(double threshold) {
		this.threshold = threshold;
	}

	public double getThreshold() {
		return threshold;
	}

}
