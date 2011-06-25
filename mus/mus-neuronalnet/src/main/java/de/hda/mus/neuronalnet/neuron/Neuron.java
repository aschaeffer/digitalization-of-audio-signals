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
public class Neuron {
	
	/**
	 * Name des Neurons.
	 */
	private String name;
	
	/**
	 * Das bias Neuron.
	 */
	private Neuron bias;
	
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
	public Neuron(TransferFunction transferFunction) {
		this.transferFunction = transferFunction;
	}

	/**
	 * Constructor for the neuron using the sigmoid transfer function
	 * as default.
	 * 
	 */
	public Neuron() {
		this.transferFunction = new SigmoidFunction();
	}

	/**
	 * Aktivierungsfunktion des Neurons
	 * @return output aka activation
	 */
	public double activation(){
		double inputSum = 0.0;
		inputSum = inputSummation();
		Neuron[] neurons = new Neuron[preNeurons.size()];
		preNeurons.keySet().toArray(neurons);
		double threshold = preNeurons.get(bias);
		return transferFunction.proceedFunction(inputSum-threshold);
//		if(inputSum>=threshold) {
//			System.out.println(this.name + " feuert: inputSum (" + inputSum + ") >= threshold (" + threshold + ")");
//			return transferFunction.proceedFunction(inputSum);
//		} else {
//			System.out.println(this.name + " feuert nicht: inputSum (" + inputSum + ") < threshold (" + threshold + ")");
//			return 0.0;
//		}
	}
	
	/**
	 * Calculates the flaw of the current neuron.
	 * Only used for hidden Neurons!
	 * 
	 * @param target is the expected value!
	 * @return flaw of the output Neuron
	 */
	private double flaw(double target){

		return -1*(target - activation()) * transferFunction.proceedDerivativeFunction(activation());
	}
	
	/**
	 * Returns the weighted flaw for a expected value. 
	 * @param target the expected value
	 * @return weighted flaw of the neuron
	 */
	public double weightedFlaw(double target){
		double flaw = 0.0;
		
		for(Neuron outputNeuron : adjacentNeurons.keySet()){
			flaw += activation() * outputNeuron.weightedFlaw(target);
//			System.out.println("flaw: "+flaw +"="+ activation()+"*"+adjacentNeuron.flaw(target));
		}
		
		return flaw;
	}

	/**
	 * Summation of the weighted inputs.
	 */
	private double inputSummation() {
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
		if (this.preNeurons.size() == 0) {
			this.bias = neuron;
		}
		preNeurons.put(neuron, value);
		neuron.getAdjacentNeurons().put(this, value);
		oldUpdateValues.put(neuron, 0.0);
	}
	
	public void setPreNeurons(HashMap<Neuron, Double> preNeurons) {
		this.preNeurons = preNeurons;
	}

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

}