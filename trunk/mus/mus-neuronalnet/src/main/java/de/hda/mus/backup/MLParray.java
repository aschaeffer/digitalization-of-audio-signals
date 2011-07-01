/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hda.mus.backup;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Vector;


public class MLParray {
    private double[] inputLayer;        // input layer
    private double[] hiddenLayer;   	// hidden layer
    private double[] outputLayer;       // output layer
    private double[] outputDelta;  // injizierte Fehler
    private double[] hiddenLayerDelta;  // implizierter Fehler
    private double[][] inputHiddenConnection;   // Gewichte zwichen input layer und hidden layer
    private double[][] hiddenOutputConnection;  // Gewichte zeischen hidden layer und output layer
    private double[][] deltaWeightHiddenOutputConnection;   // Gewichtsveränderung per zylkus zwischen hidden layer und output layer
    private double[][] deltaWeightInputHiddenConnection;    // Gewischtsveränderung per zyklus ziwschen input layeru und hidden layer
    private double[][] oldDWIHC;
    private double[][] oldDWHOC;
    private int method = 2;
    private double learningRate;
    private double momentum;
    private int updateType;
    private Random rand;
    private long _seed;

    // setter und getter für private variables
    public double getLearningRate() {
        return learningRate;
    }

    public void setLearningRate(double learningRate) {
        this.learningRate = learningRate;
    }

    public int getMethod() {
        return method;
    }

    public void setMethod(int method) {
        this.method = method;
    }

    public double getMomentum() {
        return momentum;
    }

    public void setMomentum(double momentum) {
        this.momentum = momentum;
    }

    public int getUpdateType() {
        return updateType;
    }

    public void setUpdateType(int updateType) {
        this.updateType = updateType;
    }

    public Vector readMyFiles( String fileName ) {
        return this.readData(fileName);
    }

    public void setBias(double bias) {
        hiddenLayer[0] = bias;
        outputLayer[0] = bias;
    }

    public void reset() {
        for ( int i=0; i < hiddenLayerDelta.length; i++ ) {
            hiddenLayerDelta[i] = 0;
        }
        

        for ( int i=0; i < outputDelta.length; i++ ) {
            outputDelta[i] = 0;
        }
    }

    public int inputLength() {
        return this.inputLayer.length;
    }

    public String getOutput() {
        String outputString = "";
        for ( int i = 1; i < outputLayer.length; i++ ) {
            if (i == outputLayer.length-1 ) {
                outputString += new Double(outputLayer[i]).toString();
            } else {
                outputString += new Double(outputLayer[i]).toString() + ";";
            }
            
        }

        return outputString;
    }

    MLParray(int numberInput, int numberHidden, int numberOutput) {
        
        _seed = System.currentTimeMillis();
        rand = new Random(_seed);
        //bias wird als erstes Element gesetzt im Hidden- und Outputarray
        numberHidden = numberHidden+1;
        numberOutput = numberOutput+1;
        inputLayer = new double[numberInput];
        hiddenLayer = new double[numberHidden];
        outputLayer = new double[numberOutput];

        // input -> hidden
        inputHiddenConnection = new double[numberInput+1][numberHidden];
        hiddenLayerDelta = new double[numberHidden];
        deltaWeightInputHiddenConnection = new double[numberInput+1][numberHidden];
        oldDWIHC = new double[numberInput+1][numberHidden];
        // hidden -> output
        hiddenOutputConnection = new double[numberHidden][numberOutput];
        outputDelta = new double[numberOutput];
        deltaWeightHiddenOutputConnection = new double[numberHidden][numberOutput];
        oldDWHOC = new double[numberHidden][numberOutput];
    }

    public void propagate(double[] inputValues) {
        
        System.arraycopy(inputValues, 0, inputLayer, 0, inputLayer.length);

        //compute inputHiddenConnection
        for ( int i=1; i < hiddenLayer.length;i++ ) {
            double activation = hiddenLayer[0] * inputHiddenConnection[0][i];
            for ( int j=0; j < inputLayer.length; j++ ) {
                activation += inputLayer[j] * inputHiddenConnection[j+1][i];
            }
            hiddenLayer[i] = calculateActivation(activation);
        }

        //compute hiddenOutputConnection
        for ( int i=1; i < outputLayer.length; i++ ) {
            double activation = outputLayer[0] * hiddenOutputConnection[0][i];
            for ( int j=1; j < hiddenLayer.length; j++ ) {
                activation += hiddenLayer[j] * hiddenOutputConnection[j][i];
            }
            outputLayer[i]=calculateActivation(activation);
        }
        System.out.println(outputLayer[1]);
    }

    public double calculateActivation(double activation) {
        //0 = Sprungfunktion, 1= lineare Funktion, 2 = Sigmoidfunktion;
        if ( method == 0 ) { // Sprunkfunktion
            if (activation >= 0) {
                activation = 1;
            } else {
                activation = 0;
            }
        } else if ( method == 2 ) { // Sigmoudfunktion
            activation = 1 / ( 1 + Math.exp( (activation * -1) ) );
        }
        return activation;  //lineare Funktion, wenn keine Bedingung erfüllt ist
    }

    public void backPropagate(double target) {
        //injizierter Fehler
        for ( int i=1; i < outputLayer.length; i++ ) {
            outputDelta[i] = (-1) * ( target - outputLayer[i] );
            outputDelta[i] *= outputLayer[i] * ( 1 - outputLayer[i] ); //Ableitung Sigmoid
        }

        //implizierter Fehler
        for ( int i=1 ; i < hiddenLayer.length; i++ ) {
            hiddenLayerDelta[i] = 0;
            for ( int j=1; j < outputLayer.length; j++ ) {
                hiddenLayerDelta[i] += hiddenOutputConnection[i][j] * outputDelta[j];
            }
            hiddenLayerDelta[i] *= hiddenLayer[i] * ( 1 - hiddenLayer[i] );
        }
    }

    public void learn() {
        //bias
        for ( int i=1; i < hiddenLayer.length; i++ ) {
            deltaWeightInputHiddenConnection[0][i] += (hiddenLayer[0] * hiddenLayerDelta[i]);
        }

        for ( int i=1; i < outputLayer.length; i++ ) {
            deltaWeightHiddenOutputConnection[0][i] += (outputLayer[0] * outputDelta[i]); //* learningRate + momentum * deltaWeightHiddenOutputConnection[0][i];
        }

        //input -> hidden
        for ( int i=0; i < inputLayer.length; i++ ) {
            for ( int j = 1; j < hiddenLayer.length; j++ ) {
                deltaWeightInputHiddenConnection[i+1][j] += (inputLayer[i] * hiddenLayerDelta[j]);// * learningRate;
            }
        }

        //hidden -> output
        for ( int i=1; i < hiddenLayer.length; i++ ) {
            for ( int j= 1; j < outputLayer.length; j++ ) {
                deltaWeightHiddenOutputConnection[i][j] += (hiddenLayer[i] * outputDelta[j]);// * learningRate;
            }
        }
    }

    public void updateWeights() {
        double update = 0;
        for ( int i=1; i < hiddenLayer.length; i++ ) {
            update = ( -1 * deltaWeightInputHiddenConnection[0][i] * learningRate ) + ( momentum * oldDWIHC[0][i] );
            oldDWIHC[0][i] = update;
            inputHiddenConnection[0][i] += update;
            deltaWeightInputHiddenConnection[0][i] = 0;
        }

        for ( int i=1; i < outputLayer.length; i++ ) {
            update = ( -1 * deltaWeightHiddenOutputConnection[0][i] * learningRate ) + ( momentum * oldDWHOC[0][i] );
            oldDWHOC[0][i] = update;
            hiddenOutputConnection[0][i] += update;
            deltaWeightHiddenOutputConnection[0][i] = 0;
        }

        //input -> hidden
        for ( int i=0; i < inputLayer.length; i++ ) {
            for (int j = 1; j < hiddenLayer.length; j++ ) {
                update = ( -1 * deltaWeightInputHiddenConnection[i+1][j] * learningRate ) + (momentum * oldDWIHC[i+1][j]);
                oldDWIHC[i+1][j]= update;
                inputHiddenConnection[i+1][j] += update;
                deltaWeightInputHiddenConnection[i+1][j] = 0;
            }
        }

        //hidden -> output
        for ( int i=1; i < hiddenLayer.length; i++ ) {
            for ( int j= 1; j < outputLayer.length; j++ ) {
                update = ( -1 * deltaWeightHiddenOutputConnection[i][j] * learningRate ) + ( momentum * oldDWHOC[i][j] );
                oldDWHOC[i][j] = update;
                hiddenOutputConnection[i][j] += update;
                deltaWeightHiddenOutputConnection[i][j] = 0;
            }
        }
    }

    public double calculateError(double target) {
        double error = 0.0;
        for ( int i=1; i < outputLayer.length; i++ ) {
                error += Math.pow( target-outputLayer[i],2 );
        }
        System.out.println( "error:"+error );
        return error;
    }

    public void writeData(String filename, boolean inputHidden) {
        try {
            filename = this.existsFile(filename);
            FileWriter newFile = new FileWriter(filename);

            if (inputHidden) {
                for ( int i=0; i < inputHiddenConnection.length; i++ ) {
                   for ( int j=1; j < inputHiddenConnection[i].length; j++ ) {
                        newFile.append( i + ";" + j + ";" + inputHiddenConnection[i][j] + "\n" );
                    }
                }
            } else {
                for ( int i=0; i < hiddenOutputConnection.length; i++ ) {
                    for ( int j=1; j < hiddenOutputConnection[i].length; j++ ) {
                        newFile.append( i + ";" + j + ";" + hiddenOutputConnection[i][j] + "\n" );
                    }
                }
            }
            newFile.flush();
            newFile.close();
        } catch (FileNotFoundException e) {
            System.out.println("Datei nicht gefunden");
        } catch (IOException e) {
            System.out.println("E/A-Fehler");
        }
    }

    public void writeStringToFile(String string, String filename) {
        try {
            filename = this.existsFile(filename);
            FileWriter newFile = new FileWriter(filename);
            newFile.append(string);
            newFile.flush();
            newFile.close();
        } catch (FileNotFoundException e) {
            System.out.println("Datei nicht gefunden");
        } catch (IOException e) {
            System.out.println("E/A-Fehler");
        }
    }

    public void randomWeights() {
        for ( int i=0; i < inputHiddenConnection.length; i++ ) {
            for ( int j=1; j < inputHiddenConnection[i].length; j++ ) {
                inputHiddenConnection[i][j] = rand.nextDouble()-0.5;
                }
        }

        for ( int i=0; i < hiddenOutputConnection.length; i++ ) {
            for ( int j=1; j < hiddenOutputConnection[i].length; j++ ) {
                hiddenOutputConnection[i][j] = rand.nextDouble()-0.5;
            }
        }
    }

    public void initWeights( String fileName, boolean inputHiddenConSelected ) {
        Vector input = this.readData(fileName);
        if ( inputHiddenConSelected ) {
            for ( int i=0 ; i < input.size(); i++ ) {
                int indexI = ( (Double) ((Vector) input.get(i)).get(0) ).intValue();
                int indexJ = ( (Double) ((Vector) input.get(i)).get(1) ).intValue();
                inputHiddenConnection[indexI][indexJ] = (Double)((Vector) input.get(i)).get(2);
            }
        } else {
            for ( int i=0; i < input.size(); i++ ) {
                int indexI = ( (Double) ((Vector) input.get(i) ).get(0)).intValue();
                int indexJ = ( (Double) ((Vector) input.get(i) ).get(1)).intValue();
                hiddenOutputConnection[indexI][indexJ] = (Double) ( (Vector) input.get(i) ).get(2);
            }
        }
    }

    private String existsFile(String strFilePath) {
         boolean exists = new File(strFilePath).exists();
            int counter = 1;

            while( exists ) {
                if (counter > 1) {
                    strFilePath = strFilePath.substring(3, strFilePath.length());
                }
                strFilePath = "(" + counter + ")" + strFilePath;
                counter++;
                exists = new File(strFilePath).exists();
            }
         return strFilePath;
    }

    private Vector readData(String filename) {
        Vector netRow = new Vector();
        try {
            FileReader file = new FileReader(filename);
            BufferedReader data = new BufferedReader(file);
            String zeile = "";
            String[] split = null;
            while ( (zeile = data.readLine() ) != null ) {
                split = zeile.split(";");
                Vector netColumn = new Vector();
                for ( int i=0 ; i < split.length; i++ ) {
                        //leere Zeilen ignorieren
                        String inputString = split[i] ;
                        inputString = inputString.replaceFirst(",", ".");
                        netColumn.add( new Double(inputString) );
                }
                netRow.add(netColumn);
            }
            file.close();
        } catch (FileNotFoundException e) {
            System.out.println("Datei nicht gefunden");
        } catch (IOException e) {
            System.out.println("E/A-Fehler");
        }
        return netRow;
    }
}
