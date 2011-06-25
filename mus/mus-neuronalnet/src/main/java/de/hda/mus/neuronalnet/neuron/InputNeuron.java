package de.hda.mus.neuronalnet.neuron;

import de.hda.mus.neuronalnet.transferfunction.LinearFunction;
import de.hda.mus.neuronalnet.transferfunction.TransferFunction;

/**
 * InputNeuron is a specialized Neuron with an input value.
 * 
 * @author aschaeffer
 * @author khalid
 *
 */
public class InputNeuron extends AbstractNeuron {

	/**
	 * The input value.
	 */
	private double value;

	/**
	 * Constructor for the input neuron.
	 * @param value The initial input value.
	 * @param transferFunction The transfer function.
	 */
	public InputNeuron(double value, TransferFunction transferFunction) {
		super(transferFunction);
		this.value = value;
	}
	
	/**
	 * Constructor for the input neuron using the linear transfer function.
	 * 
	 * @param value The initial input value.
	 */
	public InputNeuron(double value) {
		super(new LinearFunction());
		this.value = value;
	}

	/**
	 * An simplified activation function for input neurons.
	 * @return The activation of the input neuron (usally the input value).
	 */
	@Override
	public double activation(){
		//System.out.println(this.getName() + " feuert: value=" + getTransferFunction().proceedFunction(value) + " (" + this.getValue() + ")");
		return getTransferFunction().proceedFunction(value);
	}
	
	/**
	 * Get the input value.
	 * @return The input values.
	 */
	public double getValue() {
		return this.value;
	}

	/**
	 * Setter for the input value.
	 * @param value The new input value.
	 */
	public void setValue(double value) {
		this.value = value;
	}

	/**
	 * There is no weighted flaw for input neurons.
	 */
	@Override
	public double weightedFlaw(double target) {
		return 0;
	}

}
