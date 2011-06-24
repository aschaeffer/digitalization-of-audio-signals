package de.hda.mus.neuronalnet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map.Entry;

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
	private ArrayList<InputNeuron> inputLayer = new ArrayList<InputNeuron>();
	
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
		InputNeuron neuron = new InputNeuron(value);
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
	 * Returns all neurons (bias, input neurons, hidden neurons and
	 * output neurons) of the MLP.
	 * 
	 * @return A list of neurons
	 */
	public List<Neuron> getAllNeurons() {
		ArrayList<Neuron> neurons = new ArrayList<Neuron>();
		neurons.add(this.biasNeuron);
		neurons.addAll(this.inputLayer);
		neurons.addAll(this.hiddenLayer);
		neurons.addAll(this.outputLayer);
		return neurons;
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

	public int countNeurons(){
		return inputLayer.size()+hiddenLayer.size()+outputLayer.size()+1;
	}
	
	public int startHidden(){
		return inputLayer.size();
	}
	
	
	public void back_propagate(double learnStep_eta, double momentum_alpha, double target){
		
//		for(Neuron n: inputLayer){
//			
//		}
		
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
	
	
	public void learn(double learnStep_eta, double momentum_alpha, double target){
		for(Neuron neuron : getAllNeurons()){
			for(Neuron preNeuron : neuron.getPreNeurons().keySet()){
				double update = -1 * learnStep_eta * preNeuron.weightedFlaw(target); //schritt gemäß Gradient
				update += momentum_alpha * neuron.getOldUpdateValueForPreNeuron(preNeuron); //Momentum Term
				neuron.putPreNeuron(preNeuron, neuron.getPreNeurons().get(preNeuron) + update); // Gewicht wird geändert
				neuron.putOldUpdateValueForPreNeuron(preNeuron, update);//Speicherung des aktuellen updates
				//reset des Gradienten
			}
		}
	}
	
	public void simulation(double learnStep_eta, double momentum_alpha){
		
	}
	
	public ArrayList<Neuron> getOutputLayer(){
		return outputLayer;
	}
	
	public void xorSimulation(double learnStep_eta, double momentum_alpha){
		double error = 1;
		for(int i=1; error > 0.5;i++){
			
		}
	}

	/**
	 * Prints the MLP.
	 */
	public void printMLP() {
		System.out.println("===========OUTPUT LAYER===========");
		ListIterator<Neuron> outputLayerIterator = outputLayer.listIterator(0);
		while (outputLayerIterator.hasNext()) {
			Neuron neuron = outputLayerIterator.next();
			System.out.println(" Name: " + neuron.getName());
			Iterator<Entry<Neuron, Double>> preIterator =  neuron.getPreNeurons().entrySet().iterator();
			while (preIterator.hasNext()) {
				Entry<Neuron, Double> entry = preIterator.next();
				System.out.println("   Pre: " + entry.getKey().getName() + " Weight: " + entry.getValue());
			}
		}
		System.out.println("===========HIDDEN LAYER===========");
		ListIterator<Neuron> hiddenLayerIterator = hiddenLayer.listIterator(0);
		while (hiddenLayerIterator.hasNext()) {
			Neuron neuron = hiddenLayerIterator.next();
			System.out.println(" Name: " + neuron.getName());
			Iterator<Entry<Neuron, Double>> preIterator =  neuron.getPreNeurons().entrySet().iterator();
			while (preIterator.hasNext()) {
				Entry<Neuron, Double> entry = preIterator.next();
				System.out.println("   Pre: " + entry.getKey().getName() + " Weight: " + entry.getValue());
			}
		}
		System.out.println("===========INPUT LAYER===========");
		ListIterator<InputNeuron> inputLayerIterator = inputLayer.listIterator(0);
		while (inputLayerIterator.hasNext()) {
			InputNeuron neuron = inputLayerIterator.next();
			System.out.println(" Name: " + neuron.getName());
			System.out.println("   Value: " + neuron.getValue());
		}
	}

}
