package de.hda.mus.backup;

import de.hda.mus.neuronalnet.neuron.Neuron;

public class CopyOfMLPnachVorlesung extends MLP {

	private int anzNeurons = 6;
	
	private Double[][] deltaWeight = new Double[anzNeurons][anzNeurons];

	private Double[][] oldUpdate = new Double[anzNeurons][anzNeurons];
	private Double[] avtivity = new Double[anzNeurons];

	public CopyOfMLPnachVorlesung() {
		for (int i = 0; i < anzNeurons; i++) {
			for (int j = 0; j < anzNeurons; j++) {
				deltaWeight[i][j] = 0.0;
				oldUpdate[i][j] = 0.0;
			}
		}
	}
	

	public Double[] propagate(int[] pattern) {
		for (int i = 0; i < getInputLayer().size(); i++) {
			avtivity[i] = (double) pattern[i];
		}
		
		for (int i = getInputLayer().size(); i < getAllNeurons().size(); i++) {
			avtivity[i] = getAllNeurons().get(i).activation();
		}
		System.out.print("(p)");
		
		return avtivity;
	}
	
	public void printArray(Double[][] array){
		for (int i = 0; i < anzNeurons; i++) {
			for (int j = 0; j < anzNeurons; j++) {
				System.out.print(array[i][j] + " ");
			}
			System.out.println();
		}
	}

	public Double[][] back_propagate(double target) {
		Double[] delta = new Double[getAllNeurons().size()];
		for (int i = 0; i < getAllNeurons().size(); i++) {
			delta[i] = getAllNeurons().get(i).flaw(target);
		}
			
		for (int i = 0; i < getAllNeurons().size(); i++) {
			for (int j = 0; j < getAllNeurons().size(); j++) {
				if(!getAllNeurons().get(i).getPreNeurons().containsKey(getAllNeurons().get(j))){
					continue;
				}
				System.out.println(i+"/"+j);
				System.out.println("["+i+"]["+ j+"]" +"="+avtivity[i] +"*"+ delta[j]);
				deltaWeight[i][j] += avtivity[i] * delta[j];
			}
		}
//		printArray(deltaWeight);
		System.out.print("(b)");
		return deltaWeight;
	}
	
	public void update_weight(double eta, double alpha) {
		System.out.println("update_weight");
		for (int i = 0; i < getAllNeurons().size(); i++) {
			for (int j = 0; j < getAllNeurons().size(); j++) {
				if (deltaWeight[i][j] == 0) {
					continue;
				}
				System.out.print("\n u");
				double update = -1 * eta * deltaWeight[i][j];
				update += alpha * oldUpdate[i][j];
				Neuron currentNeuron = getAllNeurons().get(i);
				Neuron preNeuron = getAllNeurons().get(j);
				System.out.print("u");
				double newWeight = currentNeuron.getPreNeurons().get(preNeuron)
						+ update;
				System.out.print(newWeight + " = "+currentNeuron.getPreNeurons().get(preNeuron) +" + "+ update );
				currentNeuron.putPreNeuron(preNeuron, newWeight);

				oldUpdate[i][j] = update;
//				deltaWeight[i][j] = 0.0;
			}
		}
	}

	private void reset_delta() {
		for (int i = 0; i < getAllNeurons().size(); i++) {
			for (int j = 0; j < getAllNeurons().size(); j++) {
				deltaWeight[i][j] = 0.0;
			}
		}
	}

	// learnStep_eta, momentum_alpha, pattern, max_error, batch_update
	public void simulation(double eta, double alpha, int[][] pattern,
			boolean batch_update) {
		System.out.println("----simulation----");

		int iterations = 10;
		for (int i = 1; i <= iterations; i++) {
			System.out.println("#");
			for (int[] p : pattern) {
				propagate(p);
				back_propagate(p[2]);
				if (!batch_update) {
					System.out.println("Singel");
					update_weight(eta, alpha);
					reset_delta();
				}
			}
			System.out.println("#");
			if (batch_update) {
				System.out.println("Batch");
				update_weight(eta, alpha);
				reset_delta();
			}
			double error = 0.0;
			System.out.println("#");
			for (Neuron output : getOutputLayer()) {
				for (int[] p : pattern) {
					for (int j = 0; j < getInputLayer().size(); j++) {
						getInputLayer().get(j).setValue(p[j]);
					}
					error += Math.pow((p[2] - output.activation()), 2);
				}
			}
			System.out.println("#");
			System.out.println(i + ".Step error= " + error);
		}
	}
}
