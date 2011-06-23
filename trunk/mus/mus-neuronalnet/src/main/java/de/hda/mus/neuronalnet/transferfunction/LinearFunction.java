package de.hda.mus.neuronalnet.transferfunction;

public class LinearFunction implements TransferFunction {

	@Override
	public double proceedFunction(double input) {
		return input;
	}

	@Override
	public double proceedDerivativeFunction(double input) {
		// ableitung von x^1 ist 1
		return 1;
	}

}
