package de.hda.mus.neuronalnet;

import java.util.ArrayList;
import java.util.ListIterator;

import de.hda.mus.neuronalnet.transferfunction.LinearFunction;
import de.hda.mus.neuronalnet.transferfunction.SigmoidFunction;
import de.hda.mus.neuronalnet.transferfunction.TransferFunction;

/**
 * Bitte implementieren Sie ein mehrschichtiges Perzeptron (MLP). Das MLP soll eine Eingangs-schicht, eine versteckte Schicht und eine Ausgabeschicht haben. Das von Ihnen zu implemen-tierende MLP soll ueber folgende Features verfolgen:
 * - Anzahl der Neuronen in den Schichten konfigurierbar
 * - Transferfunktion der Neuronen der Ausgabeschicht waehlbar
 * - Lernrate und Momentum frei waehlbar
 * - Single oder Batch Update waehlbar
 * - Waehrend des Trainings wird der quadratische Fehler fuer die Trainingsmenge fuer jeden Zyklus berechnet.
 * 
 * Anmerkung:
 * - Ein Trainingszyklus setzt sich aus Propagation- und Back-Propagation-Berechnungen fuer alle Muster der Trainingsmenge zusammen.
 * 
 * @author aschaeffer
 * @author khalid
 *
 */
public class MLP {

	/**
	 * The bias weight is a constant.
	 */
	public static final double BIAS_VALUE = 1;
	
	/**
	 * The bias neuron of the MLP.
	 */
	private InputNeuron biasNeuron;
	
	/**
	 * The input layer of the MLP.
	 */
	private ArrayList<Neuron> inputLayer = new ArrayList<Neuron>();
	
	/**
	 * The hidden layer of the MLP.
	 */
	private ArrayList<Neuron> hiddenLayer = new ArrayList<Neuron>();
	
	/**
	 * The output layer of the MLP.
	 */
	private ArrayList<Neuron> outputLayer = new ArrayList<Neuron>();
	
	/**
	 * The default transfer function is the sigmoid function.
	 */
	TransferFunction defaultTransferFunction = new SigmoidFunction();

	/**
	 * The constructor initializes also the bias neuron.
	 */
	public MLP() {
		this.biasNeuron = new InputNeuron(BIAS_VALUE, new LinearFunction());
		this.biasNeuron.setName("Bias Neuron 0");
	}

	/**
	 * Sets a default transfer function.
	 * @param transferFunction The default transfer function.
	 */
	public void setDefaultTransferFunction(TransferFunction transferFunction) {
		this.defaultTransferFunction = transferFunction;
	}

	/**
	 * Gets the bias neuron.
	 * @return
	 */
	public InputNeuron getBiasNeuron() {
		return this.biasNeuron;
	}

	/**
	 * Add an input neuron.
	 * 
	 * @param name Name of the neuron.
	 * @param value The input value of the neuron.
	 * @return Returns an input neuron.
	 */
	public InputNeuron addInputNeuron(String name, double value) {
		InputNeuron neuron = new InputNeuron(value, new LinearFunction());
		inputLayer.add(neuron);
		neuron.setName("Input " + name);
		return neuron;
	}

	/**
	 * Add an input neuron.
	 * 
	 * @param name Name of the neuron.
	 * @param value The input value of the neuron.
	 * @param transferFunction The transfer function of the neuron.
	 * @return Returns an input neuron.
	 */
	public InputNeuron addInputNeuron(String name, double value, TransferFunction transferFunction) {
		InputNeuron neuron = new InputNeuron(value, transferFunction);
		inputLayer.add(neuron);
		neuron.setName("Input " + name);
		return neuron;
	}

	/**
	 * Add a hidden neuron.
	 * 
	 * @param name Name of the neuron.
	 * @return Returns a neuron.
	 */
	public Neuron addHiddenNeuron(String name) {
		Neuron neuron = new Neuron(this.defaultTransferFunction);
		hiddenLayer.add(neuron);
		neuron.setName("Hidden " + name);
		return neuron;
	}

	/**
	 * Add a hidden neuron.
	 * 
	 * @param name Name of the neuron.
	 * @param transferFunction The transfer function of the neuron.
	 * @return Returns a neuron.
	 */
	public Neuron addHiddenNeuron(String name, TransferFunction transferFunction) {
		Neuron neuron = new Neuron(transferFunction);
		hiddenLayer.add(neuron);
		neuron.setName("Hidden " + name);
		return neuron;
	}

	/**
	 * Add an output neuron.
	 * 
	 * @param name Name of the neuron.
	 * @return Returns a neuron.
	 */
	public Neuron addOutputNeuron(String name) {
		Neuron neuron = new Neuron(this.defaultTransferFunction);
		outputLayer.add(neuron);
		neuron.setName("Output " + name);
		return neuron;
	}

	/**
	 * Add an output neuron.
	 * 
	 * @param name Name of the neuron.
	 * @param transferFunction The transfer function of the neuron.
	 * @return Returns a neuron.
	 */
	public Neuron addOutputNeuron(String name, TransferFunction transferFunction) {
		Neuron neuron = new Neuron(transferFunction);
		outputLayer.add(neuron);
		neuron.setName("Output " + name);
		return neuron;
	}

	
	
	public void back_propagate(double learnStep_eta, double momentum_alpha){
		
	}

	/**
	 * Forward propagation of the MLP
	 */
	public void propagate() {
		ListIterator<Neuron> iterator = outputLayer.listIterator(0);
		while (iterator.hasNext()) {
			Neuron outputNeuron = iterator.next();
			outputNeuron.activation();
		}
	}
	
	private void update_weight(){
		
	}
}
