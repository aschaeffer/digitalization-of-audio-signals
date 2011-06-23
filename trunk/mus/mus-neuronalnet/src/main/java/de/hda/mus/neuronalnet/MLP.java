package de.hda.mus.neuronalnet;

import java.util.ArrayList;

import de.hda.mus.neuronalnet.transferfunction.SigmoidFunction;
import de.hda.mus.neuronalnet.transferfunction.TransferFunction;

public class MLP {

//	Bitte implementieren Sie ein mehrschichtiges Perzeptron (MLP). Das MLP soll eine Eingangs-schicht, eine versteckte Schicht und eine Ausgabeschicht haben. Das von Ihnen zu implemen-tierende MLP soll �ber folgende Features verf�gen:
//		� Anzahl der Neuronen in den Schichten konfigurierbar
//		� Transferfunktion der Neuronen der Ausgabeschicht w�hlbar
//		� Lernrate und Momentum frei w�hlbar
//		� Single oder Batch Update w�hlbar
//		� W�hrend des Trainings wird der quadratische Fehler f�r die Trainingsmenge f�r jeden Zyklus berechnet.
//	Anmerkung:
//		� Ein Trainingszyklus setzt sich aus Propagation- und Back-Propagation-Berechnungen f�r alle Muster der Trainingsmenge zusammen.
	
	// Neuron biasNeuron;
	private ArrayList<Neuron> inputLayer = new ArrayList<Neuron>();
	private ArrayList<Neuron> hiddenLayer = new ArrayList<Neuron>();
	private ArrayList<Neuron> outputLayer = new ArrayList<Neuron>();
	TransferFunction defaultTransferFunction = new SigmoidFunction();

	public void setDefaultTransferFunction(TransferFunction transferFunction) {
		this.defaultTransferFunction = transferFunction;
	}

	public InputNeuron addInputNeuron(double value) {
		InputNeuron neuron = new InputNeuron(value, this.defaultTransferFunction);
		inputLayer.add(neuron);
		return neuron;
	}

	public InputNeuron addInputNeuron(double value, TransferFunction transferFunction) {
		InputNeuron neuron = new InputNeuron(value, transferFunction);
		inputLayer.add(neuron);
		return neuron;
	}

	public Neuron addHiddenNeuron() {
		Neuron neuron = new Neuron(this.defaultTransferFunction);
		hiddenLayer.add(neuron);
		return neuron;
	}

	public Neuron addHiddenNeuron(TransferFunction transferFunction) {
		Neuron neuron = new Neuron(transferFunction);
		hiddenLayer.add(neuron);
		return neuron;
	}

	public Neuron addOutputNeuron() {
		Neuron neuron = new Neuron(this.defaultTransferFunction);
		outputLayer.add(neuron);
		return neuron;
	}

	public Neuron addOutputNeuron(TransferFunction transferFunction) {
		Neuron neuron = new Neuron(transferFunction);
		outputLayer.add(neuron);
		return neuron;
	}

}
