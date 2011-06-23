package de.hda.mus.neuronalnet;

import de.hda.mus.neuronalnet.transferfunction.TransferFunction;

public class InputNeuron extends Neuron {
	
	private double value;

	public InputNeuron(double value, TransferFunction transferFunction) {
		super(-1, transferFunction);
		this.value = value;
	}
	
	@Override
	public double activation(){
		return getTransferFunction().proceedFunction(value);
	}
	
	public double getValue() {
		return this.value;
	}

	public void setValue(double value) {
		this.value = value;
	}

}
