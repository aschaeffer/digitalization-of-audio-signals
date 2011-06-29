package de.hda.mus.neuronalnet.neuron;

import java.util.HashMap;

import de.hda.mus.neuronalnet.transferfunction.TransferFunction;

public interface Neuron {

	public double activation();
	public double weightedFlaw(double target);
	public double flaw(double target);
	public String getName();
	public void setName(String name);
	public TransferFunction getTransferFunction();
	public void setTransferFunction(TransferFunction transferFunction);
	public HashMap<Neuron, Double> getAdjacentNeurons();
	public HashMap<Neuron, Double> getPreNeurons();
	public double getOldUpdateValueForPreNeuron(Neuron pre);
	public void putOldUpdateValueForPreNeuron(Neuron pre, double updateValue);
	public void putPreNeuron(Neuron neuron, Double value);
	
}
