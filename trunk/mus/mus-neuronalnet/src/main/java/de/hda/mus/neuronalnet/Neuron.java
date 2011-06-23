package de.hda.mus.neuronalnet;

import java.util.HashMap;

import de.hda.mus.neuronalnet.transferfunction.TransferFunction;

public class Neuron {
	
	private String name;
	private Neuron bias;
	private TransferFunction transferFunction;

	/**
	 * Vorgängerneuronen und das zugehörige Gewicht
	 */
	private HashMap<Neuron, Double> preNeurons = new HashMap<Neuron, Double>();
	
	/**
	 * Nachfolgerneuronen und das zugehörige Gewicht
	 */
	private HashMap<Neuron, Double> adjacentNeurons = new HashMap<Neuron, Double>();
	
	public Neuron(TransferFunction transferFunction){
		this.transferFunction = transferFunction;
	}
	
	/**
	 * Aktivierungsfunktion des Neurons
	 * @return output
	 */
	public double activation(){
		double inputSum = 0.0;
		inputSum = inputSummation();
		Neuron[] neurons = new Neuron[preNeurons.size()];
		preNeurons.keySet().toArray(neurons);
		double threshold = preNeurons.get(bias);
		if(inputSum>=threshold) {
			System.out.println(this.name + " feuert: inputSum (" + inputSum + ") >= threshold (" + threshold + ")");
			return transferFunction.proceedFunction(inputSum);
		} else {
			System.out.println(this.name + " feuert nicht: inputSum (" + inputSum + ") < threshold (" + threshold + ")");
			return 0.0;
		}
	}
	
	/**
	 * calculate the flaw of the current 
	 * only for hidden Neurons!
	 * @param target is the expected value! it can be 0 or 1
	 * @return flaw of the hidden Neuron
	 */
	public double flaw(double target){
		double flaw = 0.0;
		
		//calculate Output flaw injectflaw
		for(Neuron outputNeuron : adjacentNeurons.keySet()){
			double outputActivation = outputNeuron.activation();
			double outputFlaw = -1*(target - outputActivation);
			flaw += outputFlaw*transferFunction.proceedDerivativeFunction(outputActivation);
		}
		
		return flaw * transferFunction.proceedDerivativeFunction(activation());
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
		if (this.preNeurons.size() == 0) {
			this.bias = neuron;
		}
		preNeurons.put(neuron, value);
		neuron.getAdjacentNeurons().put(this, value);
	}

	public void setPreNeurons(HashMap<Neuron, Double> preNeurons) {
		this.preNeurons = preNeurons;
	}

	public HashMap<Neuron, Double> getPreNeurons() {
		return preNeurons;
	}

	public HashMap<Neuron, Double> getAdjacentNeurons() {
		return adjacentNeurons;
	}

	public void setTransferFunction(TransferFunction transferFunction) {
		this.transferFunction = transferFunction;
	}

	public TransferFunction getTransferFunction() {
		return transferFunction;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}


	
	
}
