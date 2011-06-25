package de.hda.mus.neuronalnet.neuron;

import de.hda.mus.neuronalnet.transferfunction.TransferFunction;

/**
 * OutputNeuron is a specialized Neuron with an input value.
 * 
 * @author aschaeffer
 * @author khalid
 *
 */
public class OutputNeuron extends AbstractNeuron implements Neuron {

	/**
	 * Constructor for the output neuron.
	 * @param transferFunction The transfer function.
	 */
	public OutputNeuron(TransferFunction transferFunction) {
		super(transferFunction);
	}

	/**
	 * Aktivierungsfunktion des Neurons
	 * @return output aka activation
	 */
	public double activation(){
		double inputSum = 0.0;
		inputSum = inputSummation();
		AbstractNeuron[] neurons = new AbstractNeuron[this.getPreNeurons().size()];
		this.getPreNeurons().keySet().toArray(neurons);
		return this.getTransferFunction().proceedFunction(inputSum);
	}

	/**
	 * TODO: doc
	 */
	@Override
	public double weightedFlaw(double target){
		double injFlaw = -1*(target - activation()) * getTransferFunction().proceedDerivativeFunction(activation());
		return injFlaw*activation();
	}

}
