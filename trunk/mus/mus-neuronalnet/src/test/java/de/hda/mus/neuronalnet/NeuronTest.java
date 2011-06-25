package de.hda.mus.neuronalnet;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import de.hda.mus.neuronalnet.neuron.HiddenNeuron;
import de.hda.mus.neuronalnet.neuron.InputNeuron;
import de.hda.mus.neuronalnet.neuron.AbstractNeuron;
import de.hda.mus.neuronalnet.neuron.OutputNeuron;
import de.hda.mus.neuronalnet.transferfunction.SigmoidFunction;

public class NeuronTest {

	MLP multiLayerPerceptron = new MLP();
	InputNeuron bias;
	InputNeuron neuron1;
	InputNeuron neuron2;
	HiddenNeuron neuron3;
	HiddenNeuron neuron4;
	OutputNeuron neuron5;

	@Before
	public void init() {

		multiLayerPerceptron.setDefaultTransferFunction(new SigmoidFunction());

		bias = multiLayerPerceptron.getBiasNeuron();
		
		neuron1 = multiLayerPerceptron.addInputNeuron("neuron 1", 0);
		neuron2 = multiLayerPerceptron.addInputNeuron("neuron 2", 0);
		neuron3 = multiLayerPerceptron.addHiddenNeuron("neuron 3");
		neuron4 = multiLayerPerceptron.addHiddenNeuron("neuron 4");
		neuron5 = multiLayerPerceptron.addOutputNeuron("neuron 5");

		neuron3.putPreNeuron(bias, 1.166454e-02); // set threshold
		neuron3.putPreNeuron(neuron1, 8.514623e-02); // set weight
		neuron3.putPreNeuron(neuron2, -4.774473e-02); // set weight

		neuron4.putPreNeuron(bias, 2.170463e-01); // set threshold
		neuron4.putPreNeuron(neuron1, 2.152450e-01); // set weight
		neuron4.putPreNeuron(neuron2, 1.153351e-01); // set weight

		neuron5.putPreNeuron(bias, -1.844412e-01); // set threshold
		neuron5.putPreNeuron(neuron3, -1.969224e-02); // set weight
		neuron5.putPreNeuron(neuron4, -3.293430e-02); // set weight
	}

//	@Test
//	public void flawTest() {
//		neuron1.setValue(0);
//		neuron2.setValue(0);
//		double output = neuron5.activation();
//		for (int i=0; i<10; i++) {
//			double error = neuron3.flaw(1);
//			System.out.println("n5:" + output + " error"+error);
//		}
//	}

//	@Test
//	public void flawBiasTest() {
//		neuron1.setValue(0);
//		neuron2.setValue(0);
//		double error = bias.flaw(1);
//		System.out.println("error: "+error);
//	}

	@Test
	public void printMLP() {
		neuron1.setValue(0);
		neuron2.setValue(0);
		multiLayerPerceptron.printMLP();
	}

}
