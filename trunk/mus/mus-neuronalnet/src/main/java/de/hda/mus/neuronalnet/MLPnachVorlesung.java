package de.hda.mus.neuronalnet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

import de.hda.mus.neuronalnet.transferfunction.TransferFunction;

public class MLPnachVorlesung {

	private int mlpSize;
	private int inputSize;
	private int hiddenSize;
	private int outputSize;
	private TransferFunction transferFunction;

	private Double[][] deltaWeight;
	private Double[][] oldUpdate;
	public Double[] activity;
	public Double[][] weights;

	private Random random;

	public MLPnachVorlesung(int inputSize, int hiddenSize, int outputSize,
			Double[][] weights, TransferFunction transferFunction) {
		this.inputSize = inputSize;
		this.hiddenSize = hiddenSize;
		this.outputSize = outputSize;
		mlpSize = 1 + inputSize + hiddenSize + outputSize;
		this.transferFunction = transferFunction;
		this.weights = weights;

		deltaWeight = new Double[mlpSize][mlpSize];
		oldUpdate = new Double[mlpSize][mlpSize];
		activity = new Double[mlpSize];

		for (int i = 0; i < mlpSize; i++) {
			for (int j = 0; j < mlpSize; j++) {
				deltaWeight[i][j] = 0.0;
				oldUpdate[i][j] = 0.0;
			}
			activity[i] = 0.0;
		}
		// printMatrix(weights);
	}

	public MLPnachVorlesung(int inputSize, int hiddenSize, int outputSize,
			TransferFunction transferFunction) {
		this.inputSize = inputSize;
		this.hiddenSize = hiddenSize;
		this.outputSize = outputSize;
		this.mlpSize = 1 + inputSize + hiddenSize + outputSize;
		this.transferFunction = transferFunction;
		this.weights = new Double[mlpSize][mlpSize];

		deltaWeight = new Double[mlpSize][mlpSize];
		oldUpdate = new Double[mlpSize][mlpSize];
		activity = new Double[mlpSize];

		for (int i = 0; i < mlpSize; i++) {
			for (int j = 0; j < mlpSize; j++) {
				deltaWeight[i][j] = 0.0;
				oldUpdate[i][j] = 0.0;
				weights[i][j] = 0.0;
			}
			activity[i] = 0.0;
		}
		random = new Random();
		generateRandomWeights();
	}

	private void generateRandomWeights() {
		// Bias
		for (int i = startHidden(); i < endOutput(); i++) {
			weights[0][i] = random.nextDouble() - 0.5;
		}
		// Input -> Hidden
		for (int i = startInput(); i < endInput(); i++) {
			for (int j = startHidden(); j < endHidden(); j++) {
				weights[i][j] = random.nextDouble() - 0.5;
			}
		}
		// Hidden -> Output
		for (int i = startHidden(); i < endHidden(); i++) {
			for (int j = startOutput(); j < endOutput(); j++) {
				weights[i][j] = random.nextDouble() - 0.5;
			}
		}

	}

	public MLPnachVorlesung(Double[][] weights) {
		for (int i = 0; i < mlpSize; i++) {
			for (int j = 0; j < mlpSize; j++) {
				deltaWeight[i][j] = 0.0;
				oldUpdate[i][j] = 0.0;
			}
		}
		this.weights = weights;
	}

	public void propagate(double[] inputs) {
		activity[0] = 1.0;

		// fill input fields
		for (int i = 0; i < (endInput() - startInput()); i++) {
			activity[i + startInput()] = (double) inputs[i];
		}

		// propagate the activation
		for (int i = startHidden(); i < endOutput(); i++) {
			double activation = activity[0] * weights[0][i];
			for (int j = 1; j < endHidden(); j++) {
				activation += activity[j] * weights[j][i];
				// System.out.println(activation+"+="+ activity[j] +"*"+
				// weights[j][i]);
			}
			activity[i] = transferFunction.proceedFunction(activation);
		}
		// printArray("Activity: ",activity);
	}

	public void back_propagate(double[] pattern) {
		double[] delta = new double[mlpSize];
		// initial delta array
		for (int i = 0; i < delta.length; i++) {
			delta[i] = 0.0;
		}
		// injizierter Fehler
		for (int i = startOutput(); i < endOutput(); i++) {
			delta[i] = (-1) * (pattern[inputSize+(i-startOutput())] - activity[i])
					* transferFunction.proceedDerivativeFunction(activity[i]);
			// System.out.println(target+"-"+activity[i]+")*"+transferFunction.proceedDerivativeFunction(activity[i]));
		}
		// implizierter Fehler
		for (int i = startHidden(); i < endHidden(); i++) {
			for (int j = startOutput(); j < endOutput(); j++) {
				delta[i] += weights[i][j] * delta[j];
				// System.out.println("weight= "+ weight +"*"+ delta[j]);
			}
			delta[i] *= transferFunction.proceedDerivativeFunction(activity[i]);
		}
		// calculate Gradient
		// Bias
		for (int i = startHidden(); i < endOutput(); i++) {
			deltaWeight[0][i] += (activity[0] * delta[i]);

		}
		// Input -> Hidden
		for (int i = startInput(); i < endInput(); i++) {
			for (int j = startHidden(); j < endHidden(); j++) {
				deltaWeight[i][j] += (activity[i] * delta[j]);
			}
		}
		// Hidden -> Output
		for (int i = startHidden(); i < endHidden(); i++) {
			for (int j = startOutput(); j < endOutput(); j++) {
				deltaWeight[i][j] += (activity[i] * delta[j]);
			}
		}
		// printArray("delta: ", delta);
		// printMatrix(deltaWeight);
	}

	public void update_weight(double learningRate, double momentum) {
		double update = 0;
		// printMatrix(weights);
		// System.out.println("-------------------");
		// printMatrix(deltaWeight);
		for (int i = 0; i < mlpSize; i++) {
			for (int j = 0; j < mlpSize; j++) {
				if (deltaWeight[i][j] == 0) {
					continue;
				}
				update = (-1 * deltaWeight[i][j] * learningRate)
						+ (momentum * oldUpdate[i][j]);
				oldUpdate[i][j] = update;
				weights[i][j] += update;
				deltaWeight[i][j] = 0.0;
			}
		}
	}

	public void reset_delta() {
		for (int i = 0; i < mlpSize; i++) {
			for (int j = 0; j < mlpSize; j++) {
				deltaWeight[i][j] = 0.0;
			}
		}
	}

	public double[] getOutputActivation() {
		double[] output = new double[outputSize];
		for (int i = startOutput(); i < endOutput(); i++) {
			output[i - startOutput()] = activity[i];
		}
		return output;
	}

	
	
	public void simulation(double eta, double alpha, double[][] pattern,
			boolean batch_update, double max_error) {
		System.out.println("----simulation----");
		int max_iteration = 100000;
		int iter = 1;
		double error =0.0;
		for (int i = 1; i <= max_iteration; i++) {

			for (double[] p : pattern) {
				propagate(p);
				back_propagate(p);
				

				if (!batch_update) {
					update_weight(eta, alpha);
					reset_delta();
				}
			}
			if (batch_update) {
				update_weight(eta, alpha);
				reset_delta();
			}
			
			error = 0.0;
			for (double[] p : pattern) {
				error += calculateError(p);
			}

			if ((i % 100) == 0) {
				System.out.println(i + ".Step error= " + error);
			}

			if (max_error > error) {
				iter = i;
				break;
			}
		}
		System.out.println(iter + ".Step error= " + error);
	}
	
	
	public void simulation(double eta, double alpha, double[][] pattern, boolean batch_update, double max_error, double[][] expected) {
		System.out.println("----simulation----");
		int max_iteration = 100000;
		int iter = 1;
		
		double error =0.0;
		double best_exp_error = 100;
		int best_error_iter = 0;
		for (int i = 1; i <= max_iteration; i++) {

			for (double[] p : pattern) {
				propagate(p);
				back_propagate(p);
				

				if (!batch_update) {
					update_weight(eta, alpha);
					reset_delta();
				}
			}
			if (batch_update) {
				update_weight(eta, alpha);
				reset_delta();
			}
			
			error = 0.0;
			for (double[] p : pattern) {
				error += calculateError(p);
			}

			double expectedPattern_error = 0.0;
			for (double[] p : expected) {
				expectedPattern_error += calculateError(p);
			}
			
			if(best_exp_error>expectedPattern_error){
				best_exp_error  = expectedPattern_error;
				best_error_iter = i;
			}
			
			if ((i % 100) == 0) {
				System.out.println(i + ".Step error= " + error + " expected pattern error %=" + (expectedPattern_error/expected.length)*100 );
			}

			if (max_error > error) {
				iter = i;
				break;
			}
		}
		System.out.println(iter + ".Step error= " + error + " best available error i("+best_error_iter+"): "+best_exp_error);
	}

	public double calculateError(double[] pattern) {
		double error = 0.0;
		propagate(pattern);
		for (int i = 0; i < outputSize; i++) {
			error += Math.pow(
					pattern[inputSize + i] - getOutputActivation()[i], 2);
		}
		return error;
	}

	private int startInput() {
		return 1;
	}

	private int startHidden() {
		return 1 + inputSize;
	}

	private int startOutput() {
		return 1 + inputSize + hiddenSize;
	}

	private int endInput() {
		return startHidden();
	}

	private int endHidden() {
		return startOutput();
	}

	private int endOutput() {
		return mlpSize;
	}

	public static void printArray(String preDesc, double[] array) {
		System.out.print(preDesc);
		DecimalFormat format = new DecimalFormat("0.000");
		for (int i = 0; i < array.length; i++) {
			Double v = array[i];
			System.out.print((v >= 0 ? " " : "") + format.format(v) + " ||");
		}
		System.out.println();
	}

	public static void printMatrix(Double[][] matrix) {
		DecimalFormat format = new DecimalFormat("0.000");
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix.length; j++) {
				Double v = matrix[i][j];
				System.out.print((v > 0 ? " " : "") + format.format(v) + " ||");
			}
			System.out.println();
		}
	}

	public void writeWeightsInCSV(String filename) {
		try {

			File file = new File(filename);
			FileWriter fw = new FileWriter(file);
			StringBuffer sb = new StringBuffer();

			for (int i = 0; i < mlpSize; i++) {
				for (int j = 0; j < mlpSize; j++) {
					sb.append(weights[i][j] + ";");
				}
				sb.append("\n");
			}

			fw.write(sb.toString());

			fw.flush();
			fw.close();
		} catch (Exception e) {

		}
	}
	
	public void readWeightsFromCSV(String filename) {
		try {

			FileReader file = new FileReader(filename);
			BufferedReader data = new BufferedReader(file);
			String line = "";

			
			int row = 0;
			while ((line = data.readLine()) != null) {
				String[] splitLine = line.split(";");
				for (int col = 0; col < mlpSize; col++) {
					weights[row][col] = Double.parseDouble(splitLine[col]);
				}
				row++;
			}
			
			file.close();
		} catch (Exception e) {

		}
	}

	public static double[][] readPattern(String filename) {
		ArrayList<ArrayList<Double>> patterns = new ArrayList<ArrayList<Double>>();
		try {
			FileReader file = new FileReader(filename);
			BufferedReader data = new BufferedReader(file);
			String line = "";

			while ((line = data.readLine()) != null) {
				String[] splitLine = line.split(" ");
				ArrayList<Double> tmpPattern = new ArrayList<Double>();
				double output1 = new Double(splitLine[0]);
				double output2 = new Double(splitLine[1]);
				for (int i = 3; i < splitLine.length; i++) {
					if (splitLine[i].contains(";")) {
						continue;
					}
					tmpPattern.add(new Double(splitLine[i]));
				}
				tmpPattern.add(output1);
				tmpPattern.add(output2);

				patterns.add(tmpPattern);
			}

			file.close();
		} catch (Exception e) {
			System.out.println("Datei nicht gefunden");
		}

		double[][] patternArray = new double[patterns.size()][patterns.get(0)
				.size()];
		for (int i = 0; i < patterns.size(); i++) {
			for (int j = 0; j < patterns.get(i).size(); j++) {
				patternArray[i][j] = patterns.get(i).get(j);
			}
		}
		return patternArray;
	}
}
