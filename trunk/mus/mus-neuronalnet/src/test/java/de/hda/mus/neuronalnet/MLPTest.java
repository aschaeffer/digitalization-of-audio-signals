package de.hda.mus.neuronalnet;

import org.junit.Before;
import org.junit.Test;

public class MLPTest {

	@Before
	public void init() {
		
	}

	@Test
	public void xorMLPTest() {
		MLP xorMLP = new MLP();
		Neuron neuron1 = xorMLP.addInputNeuron(transferFunction);
		Neuron neuron2 = xorMLP.addInputNeuron(transferFunction);
		Neuron neuron3 = xorMLP.addHiddenNeuron(1.166454e-02, transferFunction);
		Neuron neuron4 = xorMLP.addHiddenNeuron(2.170463e-01, transferFunction);
		Neuron neuron5 = xorMLP.addOutputNeuron(-1.844412e-01, transferFunction);

		neuron3.putPreNeuron(neuron1, 8.514623e-02);
		neuron3.putPreNeuron(neuron2, -4.774473e-02);

		neuron4.putPreNeuron(neuron1, 2.152450e-01);
		neuron4.putPreNeuron(neuron2, 1.153351e-01);

		neuron5.putPreNeuron(neuron3, -1.969224e-02);
		neuron5.putPreNeuron(neuron4, -3.293430e-02);

		/*
		Bias: 0
		Input: 1 2 
		Hidden: 3 4 
		Output: 5 
		Threshold
		0 3 1.166454e-02
		0 4 2.170463e-01
		0 5 
		Input -> Hidden
		1 3 8.514623e-02
		1 4 2.152450e-01
		2 3 -4.774473e-02
		2 4 1.153351e-01
		Hidden -> Output
		3 5 -1.969224e-02
		4 5 -3.293430e-02
		*/
	}

}
