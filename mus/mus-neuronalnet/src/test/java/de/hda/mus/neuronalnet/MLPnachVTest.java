package de.hda.mus.neuronalnet;

import org.junit.BeforeClass;
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

/**
 * Tests for a XOR multi layer perceptron.
 */
public class MLPnachVTest {

	private static MLP xorMLP;
	private static MLP trainMLP_pca;
	private static MLP trainMLP_raw;

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

		weights[0][3] =  1.166454e-02;
		weights[1][3] =  8.514623e-02;
		weights[2][3] = -4.774473e-02;

		weights[0][4] =  2.170463e-01;
		weights[1][4] =  2.152450e-01;
		weights[2][4] =  1.153351e-01;

		weights[0][5] = -1.844412e-01;
		weights[3][5] = -1.969224e-02;
		weights[4][5] = -3.293430e-02;
		xorMLP = new MLP(2, 2, 1, weights, new SigmoidFunction());

		trainMLP_pca = new MLP(90, 10, 2, new SigmoidFunction());
		trainMLP_raw = new MLP(906, 10, 2, new SigmoidFunction());
	}

	@Test
	public void xorMLPnachVSimTest() {

		double[][] pattern = { { 0, 0, 0 }, { 0, 1, 1 }, { 1, 0, 1 }, { 1, 1, 0 }};
		double learnStep_eta = 0.01;
		double momentum_alpha = 0.7;
		boolean batch_update = true;
		double max_error = 0.01;
		xorMLP.simulation(learnStep_eta, momentum_alpha, pattern, batch_update, max_error);
		String filename = "resources/weights.csv";
		xorMLP.writeWeightsInCSV(filename);
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

	@Test
	public void pca_train_MLPTest() {
		String pca_train_filename = "resources/train_pca";
		String pca_weights_filename = "resources/pca_weights.csv";
		double[][] pattern = MLP.readPattern(pca_train_filename);
		
		String pca_test_filename = "resources/test_pca";
		double[][] expectedPattern = MLP.readPattern(pca_test_filename);
		
		double learnStep_eta = 0.01;
		double momentum_alpha = 0.7;
		boolean batch_update = true;
		double max_error = 5;
		System.out.println("pca_train_MLPTest - patterns:"+pattern.length+" inputs:"+pattern[0].length);
		trainMLP_pca.simulation(learnStep_eta, momentum_alpha, pattern, batch_update, max_error, expectedPattern);
		
		trainMLP_pca.writeWeightsInCSV(pca_weights_filename);
	}

	@Test
	public void raw_train_MLPTest() {
		String raw_train_filename = "resources/train_raw";
		String raw_weights_filename = "resources/raw_weights.csv";
		double[][] pattern = MLP.readPattern(raw_train_filename);
		
		String raw_test_filename = "resources/test_raw";
		double[][] expectedPattern = MLP.readPattern(raw_test_filename);
		
		double learnStep_eta = 0.01;
		double momentum_alpha = 0.7;
		boolean batch_update = true;
		double max_error = 1;
		System.out.println("pca_train_MLPTest - patterns:"+pattern.length+" inputs:"+pattern[0].length);
		trainMLP_raw.simulation(learnStep_eta, momentum_alpha, pattern, batch_update, max_error, expectedPattern);
		
		trainMLP_raw.writeWeightsInCSV(raw_weights_filename);
	}
		
}
