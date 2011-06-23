package de.hda.mus.neuronalnet.transferfunction;

public class LinearFunction implements TransferFunction {

	@Override
	public double proceedFunction(double input) {
		return input;
	}

	@Override
	public double proceedDerivativeFunction(double input) {
		// TODO Auto-generated method stub
		return 0;
	}

}
