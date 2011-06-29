package de.hda.mus.neuronalnet;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
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

	private static MLPnachVorlesung xorMLP;
	private static MLPnachVorlesung xorMLPFinish;
	private static MLPnachVorlesung trainMLP_pca;
	private static MLPnachVorlesung trainMLP_raw;
	private static MLPnachVorlesung testMLP_pca;
	private static MLPnachVorlesung testMLP_raw;

	@BeforeClass
	public static void init() {

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
//		xorMLP.writeWeightsInCSV();
		
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
		
		trainMLP_pca = new MLPnachVorlesung(90, 10, 2, new SigmoidFunction());
		testMLP_pca  = new MLPnachVorlesung(90, 10, 2, new SigmoidFunction());
		trainMLP_raw = new MLPnachVorlesung(906, 20, 2, new SigmoidFunction());
		testMLP_raw  = new MLPnachVorlesung(906, 20, 2, new SigmoidFunction());
	}

	@Test
	public void xorMLPnachVSimTest() {

		double[][] pattern = { { 0, 0, 0 }, { 0, 1, 1 }, { 1, 0, 1 }, { 1, 1, 0 }};
		double learnStep_eta = 0.8;
		double momentum_alpha = 0.9;
		boolean batch_update = true;
		double max_error = 0.01;
		xorMLP.simulation(learnStep_eta, momentum_alpha, pattern, batch_update, max_error);
		xorMLP.writeWeightsInCSV();
	}
	
	@Test
	public void xorMLPFinishTest() {
		double[][] pattern = { { 0, 0, 0 }, { 1, 1, 0 }, { 1, 0, 1 }, { 0, 1, 1 } };
		double learnStep_eta = 0.8;
		double momentum_alpha = 0.9;
		boolean batch_update = true;
		double max_error = 0.01;
		
		xorMLPFinish.simulation(learnStep_eta, momentum_alpha, pattern, batch_update, max_error);
	}
	
	@Test
	public void xorMLPA3CSVoutputsTest() {
		for(int i1 = 0; i1 <= 10;i1++){
			for(int i2 = 0; i2 <= 10;i2++){
				double input1 = i1/10d;
				double input2 = i2/10d;
				double[] pattern = {input1,input2};
				xorMLP.propagate(pattern);
				
				System.out.println(input1+";"+input2+";"+xorMLP.getOutputActivation()[0]);
			}
		}
	}
	
//	@Test
//	public void pca_train_MLPTest() {
//		String pca_train_filename = "resources/train_pca";
//		double[][] pattern = MLPnachVorlesung.readPattern(pca_train_filename);
//		
//		double learnStep_eta = 0.8;
//		double momentum_alpha = 0.9;
//		boolean batch_update = true;
//		double max_error = 0.01;
//		System.out.println("pca_train_MLPTest - patterns:"+pattern.length+" inputs:"+pattern[0].length);
//		trainMLP_pca.simulation(learnStep_eta, momentum_alpha, pattern, batch_update, max_error);
//	}
	
	@Test
	public void pca_test_MLPTest() {
		String pca_train_filename = "resources/test_pca";
		double[][] pattern = MLPnachVorlesung.readPattern(pca_train_filename);
		
		double learnStep_eta = 1;
		double momentum_alpha = 1;
		boolean batch_update = true;
		double max_error = 0.01;
		System.out.println("pca_test_MLPTest - patterns:"+pattern.length+" inputs:"+pattern[0].length);
		testMLP_pca.simulation(learnStep_eta, momentum_alpha, pattern, batch_update, max_error);
	}
	
//	@Test
//	public void raw_train_MLPTest() {
//		String raw_train_filename = "resources/train_raw";
//		double[][] pattern = MLPnachVorlesung.readPattern(raw_train_filename);
//		
//		double learnStep_eta = 0.8;
//		double momentum_alpha = 0.9;
//		boolean batch_update = true;
//		double max_error = 0.01;
//		System.out.println("raw_train_MLPTest - patterns:"+pattern.length+" inputs:"+pattern[0].length);
//		trainMLP_raw.simulation(learnStep_eta, momentum_alpha, pattern, batch_update, max_error);
//	}
//	
//	@Test
//	public void raw_test_MLPTest() {
//		String raw_train_filename = "resources/test_raw";
//		double[][] pattern = MLPnachVorlesung.readPattern(raw_train_filename);
//		
//		double learnStep_eta = 0.8;
//		double momentum_alpha = 0.9;
//		boolean batch_update = true;
//		double max_error = 0.01;
//		System.out.println("raw_test_MLPTest - patterns:"+pattern.length+" inputs:"+pattern[0].length);
//		testMLP_raw.simulation(learnStep_eta, momentum_alpha, pattern, batch_update, max_error);
//	}
	
}
