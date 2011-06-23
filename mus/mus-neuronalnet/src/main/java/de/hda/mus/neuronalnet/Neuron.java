package de.hda.mus.neuronalnet;

import java.util.HashMap;

import de.hda.mus.neuronalnet.transferfunction.TransferFunction;

public class Neuron {
	
	
	//schwellwert
	private double threshold;
	private TransferFunction transferFunction;
	
	private HashMap<Neuron, Double> preNeurons;
	
	public Neuron(double threshold , TransferFunction transferFunction){
		this.threshold = threshold;
		this.setPreNeurons(new HashMap<Neuron, Double>());
		this.transferFunction = transferFunction;
	}
	
	//Aktivierung
	public double activation(){
		double inputSum = 0.0;
		inputSum = inputSummation();
		if(inputSum>=threshold)
			return transferFunction.proceedFunction(inputSum);
		else
			return 0.0;
	}
	
	//Aktivierung
	private double inputSummation(){
		double sum = 0.0;
		for(Neuron n : preNeurons.keySet()){
			sum += n.activation() * preNeurons.get(n);
		}
		return sum;
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
