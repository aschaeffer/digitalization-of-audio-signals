package de.hda.mus.neuronalnet;

import java.util.ArrayList;
import java.util.List;

public class NeuronLayer {

	private List<Neuron> neurons;
	
	//Transferfunktion der Neuronen der Ausgabeschicht wählbar
	private static String[] TRANSFER_OPTION = {"sigmoid", "linear", "sprung"};
	private String transferFunction = TRANSFER_OPTION[0];
	
	public NeuronLayer(int numberNeurons){
		neurons = new ArrayList<Neuron>(numberNeurons);
		for(int i=0;i<numberNeurons;i++){
//TODO			neurons.add(new Neuron());
		}
	}

	public void setTransferFunction(String transferFunction) {
		this.transferFunction = transferFunction;
	}

	public String getTransferFunction() {
		return transferFunction;
	}
}
