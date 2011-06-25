package de.hda.mus.neuronalnet.neuron;

import de.hda.mus.neuronalnet.transferfunction.TransferFunction;

public class HiddenNeuron extends Neuron {

	/**
	 * Constructor for the output neuron.
	 * @param transferFunction The transfer function.
	 */
	public HiddenNeuron(TransferFunction transferFunction) {
		super(transferFunction);
	}

	/**
	 * Aktivierungsfunktion des Neurons
	 * @return output aka activation
	 */
	public double activation(){
		double inputSum = 0.0;
		inputSum = inputSummation();
		Neuron[] neurons = new Neuron[this.getPreNeurons().size()];
		this.getPreNeurons().keySet().toArray(neurons);
		return this.getTransferFunction().proceedFunction(inputSum);
	}
	
	/**
	 * Returns the weighted flaw for a expected value. 
	 * @param target the expected value
	 * @return weighted flaw of the neuron
	 */
	@Override
	public double weightedFlaw(double target){
		double implizitFlaw = 0.0;
		
		for(Neuron outputNeuron : this.getAdjacentNeurons().keySet()){
			double weight = this.getAdjacentNeurons().get(outputNeuron);
			double deltaFlaw = outputNeuron.weightedFlaw(target);
			implizitFlaw += weight * deltaFlaw;
		}
		
		return implizitFlaw*activation();
	}

}
