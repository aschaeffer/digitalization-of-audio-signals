package de.hda.mus.neuronalnet;

import java.util.HashMap;

import de.hda.mus.neuronalnet.transferfunction.TransferFunction;

public class Neuron {
	
	private TransferFunction transferFunction;
	private HashMap<Neuron, Double> preNeurons;
	
	public Neuron(TransferFunction transferFunction){
		this.setPreNeurons(new HashMap<Neuron, Double>());
		this.transferFunction = transferFunction;
	}
	
	//Aktivierung
	public double activation(){
		double inputSum = 0.0;
		inputSum = inputSummation();
		Neuron[] neurons = new Neuron[preNeurons.size()];
		preNeurons.keySet().toArray(neurons);
//		System.out.println("inputSum <-> bias act" + inputSum +" <->"+neurons[0].activation());
//		System.out.println("inputSum <-> bias thr" + inputSum +" <->"+preNeurons.get(neurons[0]));
		if(inputSum>=preNeurons.get(neurons[0]))
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

	public void setPreNeurons(HashMap<Neuron, Double> preNeurons) {
		this.preNeurons = preNeurons;
	}

	public HashMap<Neuron, Double> getPreNeurons() {
		return preNeurons;
	}

	public void setTransferFunction(TransferFunction transferFunction) {
		this.transferFunction = transferFunction;
	}

	public TransferFunction getTransferFunction() {
		return transferFunction;
	}


	
	
}
