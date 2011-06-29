package de.hda.mus.neuronalnet;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import de.hda.mus.neuronalnet.neuron.HiddenNeuron;
import de.hda.mus.neuronalnet.neuron.InputNeuron;
import de.hda.mus.neuronalnet.neuron.AbstractNeuron;
import de.hda.mus.neuronalnet.neuron.OutputNeuron;
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

/**
 * Tests for a XOR multi layer perceptron.
 */
public class MLPnachVTest {

	private MLPnachVorlesung xorMLP;
	private MLPnachVorlesung xorMLPFinish;

	@Before
	public void init() {

		Double[][] weights = new Double[6][6];
		Double[][] weightsFinish = new Double[6][6];
		for (int i = 0; i < weights.length; i++) {
			for (int j = 0; j < weights.length; j++) {
				weights[i][j] = 0.0;
				weightsFinish[i][j] = 0.0;
			}
		}
//		neuron3.putPreNeuron(bias, 1.166454e-02); // set threshold
//		neuron3.putPreNeuron(neuron1, 8.514623e-02); // set weight
//		neuron3.putPreNeuron(neuron2, -4.774473e-02); // set weight
		weights[0][3] =  1.166454e-02;
		weights[1][3] =  8.514623e-02;
		weights[2][3] = -4.774473e-02;
//		neuron4.putPreNeuron(bias, 2.170463e-01); // set threshold
//		neuron4.putPreNeuron(neuron1, 2.152450e-01); // set weight
//		neuron4.putPreNeuron(neuron2, 1.153351e-01); // set weight
		weights[0][4] =  2.170463e-01;
		weights[1][4] =  2.152450e-01;
		weights[2][4] =  1.153351e-01;
//		neuron5.putPreNeuron(bias, -1.844412e-01); // set threshold
//		neuron5.putPreNeuron(neuron3, -1.969224e-02); // set weight
//		neuron5.putPreNeuron(neuron4, -3.293430e-02); // set weight
		weights[0][5] = -1.844412e-01;
		weights[3][5] = -1.969224e-02;
		weights[4][5] = -3.293430e-02;
		xorMLP = new MLPnachVorlesung(2, 2, 1, weights, new SigmoidFunction());
		
		//mlp finish
		
//		neuron3.putPreNeuron(bias, -5.930721430666804); // set threshold
//		neuron3.putPreNeuron(neuron1, 3.835846530669538); // set weight
//		neuron3.putPreNeuron(neuron2, 3.808799936247644); // set weight
		weightsFinish[0][3] = -5.930721430666804;
		weightsFinish[1][3] =  3.835846530669538;
		weightsFinish[2][3] =  3.808799936247644;
//		neuron4.putPreNeuron(bias, -2.7664328994583656); // set threshold
//		neuron4.putPreNeuron(neuron1, 6.290702460009742); // set weight
//		neuron4.putPreNeuron(neuron2, 6.25740081951328); // set weight
		weightsFinish[0][4] =  -2.7664328994583656;
		weightsFinish[2][4] =   6.290702460009742;
		weightsFinish[1][4] =   6.25740081951328;
//		neuron5.putPreNeuron(bias, -3.2519613904918914); // set threshold
//		neuron5.putPreNeuron(neuron3, -8.398206239139247); // set weight
//		neuron5.putPreNeuron(neuron4, 7.384794323630908); // set weight
		weightsFinish[0][5] = -3.2519613904918914;
		weightsFinish[3][5] = -8.398206239139247;
		weightsFinish[4][5] =  7.384794323630908;
		
		xorMLPFinish = new MLPnachVorlesung(2, 2, 1, weightsFinish, new SigmoidFunction());
	}

	@Test
	public void xorMLPnachVSimTest() {

//		int[][] pattern = { { 0, 0, 0 }, { 1, 1, 0 }, { 1, 0, 1 }, { 0, 1, 1 } };
		double[][] pattern = { { 0, 0, 0 }, { 0, 1, 1 }, { 1, 0, 1 }, { 1, 1, 0 }};
		double learnStep_eta = 0.8;
		double momentum_alpha = 0.9;
		boolean batch_update = true;
		double max_error = 0.01;
		xorMLP.simulation(learnStep_eta, momentum_alpha, pattern, batch_update, max_error);
	}
	
//	@Test
//	public void xorMLPFinishTest() {
//		double[][] pattern = { { 0, 0, 0 }, { 1, 1, 0 }, { 1, 0, 1 }, { 0, 1, 1 } };
//		double learnStep_eta = 0.8;
//		double momentum_alpha = 0.9;
//		boolean batch_update = true;
//		double max_error = 0.01;
//		
//		xorMLPFinish.simulation(learnStep_eta, momentum_alpha, pattern, batch_update, max_error);
//	}
//	
	@Test
	public void xorMLPA3Test() {

		double learnStep_eta = 0.8;
		double momentum_alpha = 0.9;
		boolean batch_update = true;
		double max_error = 0.01;
		for(int input1 = 0; input1 <= 10;input1++){
			for(int input2 = 0; input2 <= 10;input2++){
				double[] pattern = {input1/10,input2/10};
				xorMLP.propagate(pattern);
				
				System.out.println(input1/10+";"+input2/10+";"+xorMLP.getOutputActivation()[0]);
			}
		}
	}
}