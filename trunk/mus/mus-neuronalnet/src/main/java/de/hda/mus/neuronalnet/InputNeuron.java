package de.hda.mus.neuronalnet;

import de.hda.mus.neuronalnet.transferfunction.TransferFunction;

public class InputNeuron extends Neuron {
	
	private double value;

	public InputNeuron(double value, TransferFunction transferFunction) {
		super(transferFunction);
		this.value = value;
	}
	
	@Override
	public double activation(){
		System.out.println(this.getName() + " feuert: value=" + getTransferFunction().proceedFunction(value) + " (" + this.getValue() + ")");
		return getTransferFunction().proceedFunction(value);
	}
	
	public double getValue() {
		return this.value;
	}

	public void setValue(double value) {
		this.value = value;
	}

}
