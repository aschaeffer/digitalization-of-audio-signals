package de.hda.mus.neuronalnet;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import de.hda.mus.neuronalnet.transferfunction.SigmoidFunction;

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

public class MLPTest {

	MLP multiLayerPerceptron = new MLP();
	InputNeuron bias;
	InputNeuron neuron1;
	InputNeuron neuron2;
	Neuron neuron3;
	Neuron neuron4;
	Neuron neuron5;

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

	@Test
	public void xorMLPTest00() {
		neuron1.setValue(0);
		neuron2.setValue(0);
		double output = neuron5.activation();
		System.out.println("n1:0 n2:0 n5:" + output);
		assertEquals(output, 1);
	}

	@Test
	public void xorMLPTest01() {
		neuron1.setValue(0);
		neuron2.setValue(1);
		double output = neuron5.activation();
		System.out.println("n1:0 n2:1 n5:" + output);
		assertEquals(output, 0);
	}

	@Test
	public void xorMLPTest10() {
		neuron1.setValue(1);
		neuron2.setValue(0);
		double output = neuron5.activation();
		System.out.println("n1:1 n2:0 n5:" + output);
		assertEquals(output, 0);
	}

	@Test
	public void xorMLPTest11() {
		neuron1.setValue(1);
		neuron2.setValue(1);
		double output = neuron5.activation();
		System.out.println("n1:1 n2:1 n5:" + output);
		assertEquals(output, 1);
	}

}
