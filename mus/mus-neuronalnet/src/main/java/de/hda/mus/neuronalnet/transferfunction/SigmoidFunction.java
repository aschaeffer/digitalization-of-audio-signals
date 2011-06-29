package de.hda.mus.neuronalnet.transferfunction;

public class SigmoidFunction implements TransferFunction {

	@Override
	public double proceedFunction(double input) {
		return 1 / (1 + Math.exp(-1 * input));
	}

	@Override
	public double proceedDerivativeFunction(double input) {
		return input * (1 - input);
	}

}
