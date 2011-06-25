package de.hda.mus.neuronalnet.neuron;

import de.hda.mus.neuronalnet.transferfunction.TransferFunction;

/**
 * OutputNeuron is a specialized Neuron with an input value.
 * 
 * @author aschaeffer
 * @author khalid
 *
 */
public class OutputNeuron extends Neuron {

	/**
	 * Constructor for the output neuron.
	 * @param transferFunction The transfer function.
	 */
	public OutputNeuron(TransferFunction transferFunction) {
		super(transferFunction);
	}
	
	@Override
	public double weightedFlaw(double target){
		double injFlaw = -1*(target - activation()) * getTransferFunction().proceedDerivativeFunction(activation());
		return injFlaw*activation();
	}
}
