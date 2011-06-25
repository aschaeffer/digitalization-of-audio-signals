package de.hda.mus.neuronalnet.neuron;

import java.util.HashMap;

import de.hda.mus.neuronalnet.transferfunction.SigmoidFunction;
import de.hda.mus.neuronalnet.transferfunction.TransferFunction;

/**
 * Repräsentiert ein Neuron
 * 
 * @author aschaeffer
 * @author khalid
 *
 */
public abstract class AbstractNeuron {
	
	/**
	 * Name des Neurons.
	 */
	private String name;
	
	/**
	 * Die Transfer-Funktion, die dieses Neuron nutzen soll.
	 */
	private TransferFunction transferFunction;

	/**
	 * Vorgängerneuronen und das zugehörige Gewicht
	 */
	private HashMap<Neuron, Double> preNeurons = new HashMap<Neuron, Double>();
	
	/**
	 * Nachfolgerneuronen und das zugehörige Gewicht
	 */
	private HashMap<Neuron, Double> adjacentNeurons = new HashMap<Neuron, Double>();
	
	/**
	 * für das Lernern der Neuronen (Momentum)
	 */
	private HashMap<Neuron, Double> oldUpdateValues =  new HashMap<Neuron, Double>();
	
	/**
	 * Constructor for the neuron.
	 * @param transferFunction The transfer function.
	 */
	public AbstractNeuron(TransferFunction transferFunction) {
		this.transferFunction = transferFunction;
	}

	/**
	 * Constructor for the neuron using the sigmoid transfer function
	 * as default.
	 * 
	 */
	public AbstractNeuron() {
		this.transferFunction = new SigmoidFunction();
	}

	public abstract double activation();
	
	public abstract double weightedFlaw(double target);

	/**
	 * Summation of the weighted inputs.
	 */
	protected double inputSummation() {
		double sum = 0.0;
		for(Neuron n : preNeurons.keySet()){
			sum += n.activation() * preNeurons.get(n);
		}
		return sum;
	}

	/**
	 * Inserts the pre-neuron and the weight or updates
	 * the weight for the given pre-neuron.
	 * 
	 * @param neuron The pre neuron
	 * @param value The weight
	 */
	public void putPreNeuron(Neuron neuron, Double value) {
		preNeurons.put(neuron, value);
		neuron.getAdjacentNeurons().put((Neuron) this, value);
		oldUpdateValues.put(neuron, 0.0);
	}
	
//	public void setPreNeurons(HashMap<Neuron, Double> preNeurons) {
//		this.preNeurons = preNeurons;
//	}

	/**
	 * Returns the pre neurons.
	 * @return HashMap of neurons and their weight
	 */
	public HashMap<Neuron, Double> getPreNeurons() {
		return preNeurons;
	}

	/**
	 * Returns the adjacent neurons.
	 * @return HashMap of neurons and their weight
	 */
	public HashMap<Neuron, Double> getAdjacentNeurons() {
		return adjacentNeurons;
	}

	/**
	 * Setter for the transfer function.
	 * @param transferFunction The transfer function.
	 */
	public void setTransferFunction(TransferFunction transferFunction) {
		this.transferFunction = transferFunction;
	}

	/**
	 * Getter for the transfer function.
	 * @return The transfer function
	 */
	public TransferFunction getTransferFunction() {
		return transferFunction;
	}

	/**
	 * Setter for the name.
	 * @param name Name of the neuron.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter for the name.
	 * @return Name of the neuron.
	 */
	public String getName() {
		return name;
	}

	public void putOldUpdateValueForPreNeuron(Neuron pre, double updateValue) {
		oldUpdateValues.put(pre, updateValue);
	}
	
	public double getOldUpdateValueForPreNeuron(Neuron pre) {
		return oldUpdateValues.get(pre);
	}

	public abstract double flaw(double target);

}
