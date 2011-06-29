package de.hda.mus.neuronalnet;

import java.io.File;
import java.io.FileWriter;
import java.text.DecimalFormat;

import de.hda.mus.neuronalnet.transferfunction.TransferFunction;

public class MLPnachVorlesung{

	private int mlpSize;
	private int inputSize;
	private int hiddenSize;
	private int outputSize;
	private TransferFunction transferFunction;
	

	private Double[][] deltaWeight;
	private Double[][] oldUpdate;
	private Double[]    activity;
	private Double[][]   weights;
	

	public MLPnachVorlesung(int inputSize, int hiddenSize, int outputSize, Double[][] weights, TransferFunction transferFunction) {	
		this.inputSize = inputSize;
		this.hiddenSize = hiddenSize;
		this.outputSize = outputSize;
		mlpSize = 1+inputSize+ hiddenSize+ outputSize;
		
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
//		printMatrix(weights);
		this.transferFunction = transferFunction;
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
		
		//fill input fields
		for (int i = 0; i < (endInput()-startInput()); i++) {
			activity[i+startInput()] = (double) inputs[i];
		}
		
        //propagate the activation 
        for ( int i=startHidden(); i < endOutput();i++ ) {
            double activation = activity[0] * weights[0][i];
            for ( int j=1; j < endHidden() ; j++ ) {
                activation += activity[j] * weights[j][i];
//                System.out.println(activation+"+="+ activity[j] +"*"+ weights[j][i]);
            }
            activity[i] = transferFunction.proceedFunction(activation);
        }
//        printArray("Activity: ",activity);
	}

	public void back_propagate(double target) {

		Double[] delta = new Double[mlpSize];
		// initial delta array
		for (int i = 0; i < delta.length; i++) {
				delta[i] = 0.0;
		}
		// injizierter Fehler
		for (int i = startOutput(); i < endOutput(); i++) {
			delta[i] = (-1) * (target - activity[i])* transferFunction.proceedDerivativeFunction(activity[i]);
//			System.out.println(target+"-"+activity[i]+")*"+transferFunction.proceedDerivativeFunction(activity[i]));
		}
		// implizierter Fehler
		for (int i = startHidden(); i < endHidden(); i++) {
			for (int j = startOutput(); j < endOutput(); j++) {
				delta[i] += weights[i][j] * delta[j];
//				System.out.println("weight= "+ weight +"*"+ delta[j]);
			}
			delta[i] *= transferFunction.proceedDerivativeFunction(activity[i]);;
		}
		//calculate Gradient
		for (int i = 0; i < mlpSize; i++) {
			for (int j = 0; j < mlpSize; j++) {
				deltaWeight[i][j] += (activity[i] * delta[j]);
			}
		}
//		printArray("delta: ", delta);
//		printMatrix(deltaWeight);
	}

	 public void update_weight(double learningRate, double momentum) {
	        double update = 0;
//	        printMatrix(weights);
//	        System.out.println("-------------------");
//	        printMatrix(deltaWeight);
	        for ( int i=0; i < mlpSize; i++ ) {
	            for ( int j= 0; j < mlpSize; j++ ) {
	            	if(deltaWeight[i][j] == 0){
	            		continue;
	            	}
	                update = ( -1 * deltaWeight[i][j] * learningRate ) + ( momentum * oldUpdate[i][j] );
	                oldUpdate[i][j] = update;
	                weights[i][j] +=  update;
	                deltaWeight[i][j] = 0.0;
	            }
	        }
	    }
	
	private void reset_delta() {
		for (int i = 0; i < mlpSize; i++) {
			for (int j = 0; j < mlpSize; j++) {
				deltaWeight[i][j] = 0.0;
			}
		}
	}

	public double[] getOutputActivation(){
		double[] output = new double[outputSize];
		for ( int i=startOutput(); i < endOutput(); i++ ) {
			output[i-startOutput()] = activity[i];
		}
		return output;
	}
	
	public void writeWeightsInCSV(){
		try
		{
//			File file = new File("resources/weights"+System.currentTimeMillis()+".csv");
			File file = new File("resources/weights.csv");
			FileWriter fw = new FileWriter(file);
			StringBuffer sb = new StringBuffer();
			
			
			for (int i = 0; i < mlpSize; i++) {
				for (int j = 0; j < mlpSize; j++) {
					sb.append(weights[i][j]+";");
				}
				sb.append("\n");
			}
			
			fw.write(sb.toString());
			
			fw.flush();
			fw.close();
		}
		catch(Exception e){
			
		}
	}
	public void simulation(double eta, double alpha, double[][] pattern, boolean batch_update , double max_error) {
		System.out.println("----simulation----");
		int max_iteration = 100000;
				for (int i = 1; i < max_iteration; i++) {

			for (double[] p : pattern) {
				propagate(p);
				back_propagate(p[2]);
				if (!batch_update) {
					update_weight(eta, alpha);
					reset_delta();
				}
			}
			if (batch_update) {
				update_weight(eta, alpha);
				reset_delta();
			}
			double error = 0.0;
			for (double[] p : pattern) {
				propagate(p);
				error += calculateError(p[2]);
				System.out.println("Patter "+p[0]+" "+p[1]+"->"+activity[5]);
			}
			System.out.println(i + ".Step error= " + error);
			if(max_error>error){
				break;
			}			
		}
	}
	
    private double calculateError(double target) {
        double error = 0.0;
        for ( int i=startOutput(); i < endOutput(); i++ ) {
        	error += Math.pow( target-activity[i],2);
        }
        return error;
    }

	private int startInput() {
		return 1;
	}
	
	private int startHidden() {
		return 1+inputSize;
	}

	private int startOutput() {
		return 1+inputSize+hiddenSize;
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
	
	
	public static void printArray(String preDesc, Double[] array) {
		System.out.print(preDesc);
		DecimalFormat format = new DecimalFormat("0.000000");
		for (int i = 0; i < array.length; i++) {
			Double v = array[i];
			System.out.print((v >= 0 ? " " : "") + format.format(v) + " ||");		
		}
		System.out.println();
	}
	
	public static void printMatrix(Double[][] matrix) {
		DecimalFormat format = new DecimalFormat("0.000000");
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix.length; j++) {
				Double v = matrix[i][j];
				System.out.print((v > 0 ? " " : "") + format.format(v) + " ||");
			}
			System.out.println();
		}
	}
}
