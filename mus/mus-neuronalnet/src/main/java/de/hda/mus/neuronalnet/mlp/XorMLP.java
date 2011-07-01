package de.hda.mus.neuronalnet.mlp;

import de.hda.mus.backup.MLP;
import de.hda.mus.neuronalnet.neuron.HiddenNeuron;
import de.hda.mus.neuronalnet.neuron.InputNeuron;
import de.hda.mus.neuronalnet.neuron.OutputNeuron;
import de.hda.mus.neuronalnet.transferfunction.SigmoidFunction;

public class XorMLP extends MLP {

	protected int[][] xorPattern = { { 0, 0, 1 }, { 1, 1, 1 }, { 1, 0, 0 }, { 0, 1, 0 } };

	protected InputNeuron neuron1;
	protected InputNeuron neuron2;
	protected HiddenNeuron neuron3;
	protected HiddenNeuron neuron4;
	protected OutputNeuron neuron5;

	public XorMLP() {
		super();
		setPattern(xorPattern);
		setDefaultTransferFunction(new SigmoidFunction());
		neuron1 = addInputNeuron("neuron 1", 0);
		neuron2 = addInputNeuron("neuron 2", 0);
		neuron3 = addHiddenNeuron("neuron 3");
		neuron4 = addHiddenNeuron("neuron 4");
		neuron5 = addOutputNeuron("neuron 5");
		reset();
	}

	public void reset() {
		neuron3.putPreNeuron(getBiasNeuron(), 1.166454e-02); // set threshold
		neuron3.putPreNeuron(neuron1, 8.514623e-02); // set weight
		neuron3.putPreNeuron(neuron2, -4.774473e-02); // set weight
		neuron4.putPreNeuron(getBiasNeuron(), 2.170463e-01); // set threshold
		neuron4.putPreNeuron(neuron1, 2.152450e-01); // set weight
		neuron4.putPreNeuron(neuron2, 1.153351e-01); // set weight
		neuron5.putPreNeuron(getBiasNeuron(), -1.844412e-01); // set threshold
		neuron5.putPreNeuron(neuron3, -1.969224e-02); // set weight
		neuron5.putPreNeuron(neuron4, -3.293430e-02); // set weight
	}

}
