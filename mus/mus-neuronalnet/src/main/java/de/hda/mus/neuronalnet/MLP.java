package de.hda.mus.neuronalnet;

public class MLP {

//	Bitte implementieren Sie ein mehrschichtiges Perzeptron (MLP). Das MLP soll eine Eingangs-schicht, eine versteckte Schicht und eine Ausgabeschicht haben. Das von Ihnen zu implemen-tierende MLP soll über folgende Features verfügen:
//		• Anzahl der Neuronen in den Schichten konfigurierbar
//		• Transferfunktion der Neuronen der Ausgabeschicht wählbar
//		• Lernrate und Momentum frei wählbar
//		• Single oder Batch Update wählbar
//		• Während des Trainings wird der quadratische Fehler für die Trainingsmenge für jeden Zyklus berechnet.
//	Anmerkung:
//		• Ein Trainingszyklus setzt sich aus Propagation- und Back-Propagation-Berechnungen für alle Muster der Trainingsmenge zusammen.
	
	private NeuronLayer inputLayer;
	private NeuronLayer hiddenLayer;
	private NeuronLayer outputLayer;

	public MLP(int numInputNeuron, int numHiddenNeuron, int numOutputNeuron) {
		inputLayer = new NeuronLayer(numInputNeuron);
		hiddenLayer = new NeuronLayer(numHiddenNeuron);
		outputLayer = new NeuronLayer(numOutputNeuron);
	}

//	Bias: 0
//	Input: 1 2 
//	Hidden: 3 4 
//	Output: 5 
//	Threshold
//	0 3 1.166454e-02
//	0 4 2.170463e-01
//	0 5 -1.844412e-01
//	Input -> Hidden
//	1 3 8.514623e-02
//	1 4 2.152450e-01
//	2 3 -4.774473e-02
//	2 4 1.153351e-01
//	Hidden -> Output
//	3 5 -1.969224e-02
//	4 5 -3.293430e-02
	
	
	   public static void main( String[] args ) {
	       
	    }
}
