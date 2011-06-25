package de.hda.mus.neuronalnet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map.Entry;

import de.hda.mus.neuronalnet.neuron.HiddenNeuron;
import de.hda.mus.neuronalnet.neuron.InputNeuron;
import de.hda.mus.neuronalnet.neuron.Neuron;
import de.hda.mus.neuronalnet.neuron.OutputNeuron;
import de.hda.mus.neuronalnet.transferfunction.LinearFunction;
import de.hda.mus.neuronalnet.transferfunction.SigmoidFunction;
import de.hda.mus.neuronalnet.transferfunction.TransferFunction;

/**
 * Bitte implementieren Sie ein mehrschichtiges Perzeptron (MLP). Das MLP soll
 * eine Eingangs-schicht, eine versteckte Schicht und eine Ausgabeschicht haben.
 * Das von Ihnen zu implemen-tierende MLP soll ueber folgende Features
 * verfolgen: - Anzahl der Neuronen in den Schichten konfigurierbar -
 * Transferfunktion der Neuronen der Ausgabeschicht waehlbar - Lernrate und
 * Momentum frei waehlbar - Single oder Batch Update waehlbar - Waehrend des
 * Trainings wird der quadratische Fehler fuer die Trainingsmenge fuer jeden
 * Zyklus berechnet.
 * 
 * Anmerkung: - Ein Trainingszyklus setzt sich aus Propagation- und
 * Back-Propagation-Berechnungen fuer alle Muster der Trainingsmenge zusammen.
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
	private ArrayList<HiddenNeuron> hiddenLayer = new ArrayList<HiddenNeuron>();

	/**
	 * The output layer of the MLP.
	 */
	private ArrayList<OutputNeuron> outputLayer = new ArrayList<OutputNeuron>();

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
	 * 
	 * @param transferFunction
	 *            The default transfer function.
	 */
	public void setDefaultTransferFunction(TransferFunction transferFunction) {
		this.defaultTransferFunction = transferFunction;
	}

	/**
	 * Gets the bias neuron.
	 * 
	 * @return
	 */
	public InputNeuron getBiasNeuron() {
		return this.biasNeuron;
	}

	/**
	 * Add an input neuron.
	 * 
	 * @param name
	 *            Name of the neuron.
	 * @param value
	 *            The input value of the neuron.
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
	 * @param name
	 *            Name of the neuron.
	 * @param value
	 *            The input value of the neuron.
	 * @param transferFunction
	 *            The transfer function of the neuron.
	 * @return Returns an input neuron.
	 */
	public InputNeuron addInputNeuron(String name, double value,
			TransferFunction transferFunction) {
		InputNeuron neuron = new InputNeuron(value, transferFunction);
		inputLayer.add(neuron);
		neuron.setName("Input " + name);
		return neuron;
	}

	/**
	 * Add a hidden neuron.
	 * 
	 * @param name
	 *            Name of the neuron.
	 * @return Returns a neuron.
	 */
	public HiddenNeuron addHiddenNeuron(String name) {
		HiddenNeuron neuron = new HiddenNeuron(this.defaultTransferFunction);
		hiddenLayer.add(neuron);
		neuron.setName("Hidden " + name);
		return neuron;

	}

	/**
	 * Add a hidden neuron.
	 * 
	 * @param name
	 *            Name of the neuron.
	 * @param transferFunction
	 *            The transfer function of the neuron.
	 * @return Returns a neuron.
	 */
	public HiddenNeuron addHiddenNeuron(String name,
			TransferFunction transferFunction) {
		HiddenNeuron neuron = new HiddenNeuron(transferFunction);
		hiddenLayer.add(neuron);
		neuron.setName("Hidden " + name);
		return neuron;
	}

	/**
	 * Add an output neuron.
	 * 
	 * @param name
	 *            Name of the neuron.
	 * @return Returns a neuron.
	 */
	public OutputNeuron addOutputNeuron(String name) {
		OutputNeuron neuron = new OutputNeuron(this.defaultTransferFunction);
		outputLayer.add(neuron);
		neuron.setName("Output " + name);
		return neuron;
	}

	/**
	 * Add an output neuron.
	 * 
	 * @param name
	 *            Name of the neuron.
	 * @param transferFunction
	 *            The transfer function of the neuron.
	 * @return Returns a neuron.
	 */
	public OutputNeuron addOutputNeuron(String name,
			TransferFunction transferFunction) {
		OutputNeuron neuron = new OutputNeuron(transferFunction);
		outputLayer.add(neuron);
		neuron.setName("Output " + name);
		return neuron;
	}

	/**
	 * Returns all neurons (bias, input neurons, hidden neurons and output
	 * neurons) of the MLP.
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



	private void update_weight_neuron(double learnStep_eta,
			double momentum_alpha, Neuron currentNeuron, Neuron preNeuron,
			double flawDelta) {
		double update = -1 * learnStep_eta * flawDelta; // schritt gemäß
														// Gradient
														// System.out.println("neuron.weightedFlaw(target) "+flawDelta);
		// System.out.println("update "+update);
		update += momentum_alpha
				* currentNeuron.getOldUpdateValueForPreNeuron(preNeuron); // Momentum
																			// Term
																			// System.out.println("update "+update);
		double newWeight = currentNeuron.getPreNeurons().get(preNeuron)
				+ update;

		// System.out.println(currentNeuron.getPreNeurons().get(preNeuron)
		// +" + "+update +"=" + newWeight);

		currentNeuron.putPreNeuron(preNeuron, newWeight); // Gewicht wird
															// geändert
															// System.out.println(currentNeuron.getPreNeurons().get(preNeuron));
		currentNeuron.putOldUpdateValueForPreNeuron(preNeuron, update);// Speicherung
																		// des
																		// aktuellen
																		// updates
		// reset des Gradienten
	}

	/**
	 * 
	 * @param learnStep_eta
	 * @param momentum_alpha
	 * @param pattern
	 * @param batch_update
	 * @return gesamt Error nach dem Lernen
	 */
	public double learn(double learnStep_eta, double momentum_alpha,
			int[][] pattern, boolean batch_update) {

		for (Neuron neuron : getAllNeurons()) {
			for (Neuron preNeuron : neuron.getPreNeurons().keySet()) {
				// System.out.println(neuron.getName() +
				// "<-> "+preNeuron.getName());

				double flawDelta = 0.0;

				for (int[] p : pattern) {
					for (int i = 0; i < inputLayer.size(); i++) {
						inputLayer.get(i).setValue(p[i]);
					}

					flawDelta += neuron.weightedFlaw(p[2]);

					if (!batch_update) {
						update_weight_neuron(learnStep_eta, momentum_alpha,
								neuron, preNeuron, flawDelta);
						flawDelta = 0.0;
					}
				}
				if (batch_update) {
					update_weight_neuron(learnStep_eta, momentum_alpha, neuron,
							preNeuron, flawDelta);
					flawDelta = 0.0;
				}

			}
		}

		double overallFlaw = 0.0;
		for (Neuron output : outputLayer) {
			for (int[] p : pattern) {
				for (int i = 0; i < inputLayer.size(); i++) {
					inputLayer.get(i).setValue(p[i]);
				} 
				overallFlaw += Math.pow((p[2] - output.activation()), 2);
				System.out.println(overallFlaw);
			}
		}
		return overallFlaw;
	}

	public ArrayList<InputNeuron> getInputLayer() {
		return inputLayer;
	}

	public ArrayList<HiddenNeuron> getHiddenLayer() {
		return hiddenLayer;
	}

	public ArrayList<OutputNeuron> getOutputLayer() {
		return outputLayer;
	}

	public void xorSimulation(double learnStep_eta, double momentum_alpha,
			int[][] pattern, double max_error, boolean batch_update) {

		System.out.println("start xor sim");

		double error = 1.0;
		for (int i = 1; max_error < error; i++) {
			// System.out.println(i + ". SimStep Error=" + error+
			// "--------------------------------");
			error = learn(learnStep_eta, momentum_alpha, pattern, batch_update);
			System.out.println(i + ". SimStep Error=" + error
					+ "--------------------------------");
			if (i == 1000) {
				printMLP();
				break;
			}
		}
	}

	/**
	 * Prints the MLP.
	 */
	public void printMLP() {
		System.out.println("===========OUTPUT LAYER===========");
		ListIterator<OutputNeuron> outputLayerIterator = outputLayer
				.listIterator(0);
		while (outputLayerIterator.hasNext()) {
			OutputNeuron neuron = outputLayerIterator.next();
			System.out.println(" Name: " + neuron.getName() + " Act: "
					+ neuron.activation());
			Iterator<Entry<Neuron, Double>> preIterator = neuron
					.getPreNeurons().entrySet().iterator();
			while (preIterator.hasNext()) {
				Entry<Neuron, Double> entry = preIterator.next();
				System.out.println("   Pre: " + entry.getKey().getName()
						+ " Weight: " + entry.getValue() + " Act: "
						+ entry.getKey().activation());
			}
		}
		System.out.println("===========HIDDEN LAYER===========");
		ListIterator<HiddenNeuron> hiddenLayerIterator = hiddenLayer
				.listIterator(0);
		while (hiddenLayerIterator.hasNext()) {
			HiddenNeuron neuron = hiddenLayerIterator.next();
			System.out.println(" Name: " + neuron.getName());
			Iterator<Entry<Neuron, Double>> preIterator = neuron
					.getPreNeurons().entrySet().iterator();
			while (preIterator.hasNext()) {
				Entry<Neuron, Double> entry = preIterator.next();
				System.out.println("   Pre: " + entry.getKey().getName()
						+ " Weight: " + entry.getValue() + " Act: "
						+ entry.getKey().activation());
			}
		}
		System.out.println("===========INPUT LAYER===========");
		ListIterator<InputNeuron> inputLayerIterator = inputLayer
				.listIterator(0);
		while (inputLayerIterator.hasNext()) {
			InputNeuron neuron = inputLayerIterator.next();
			System.out.println(" Name: " + neuron.getName());
			System.out.println("   Value: " + neuron.getValue());
		}
	}

}
