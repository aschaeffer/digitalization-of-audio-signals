package de.hda.mus.neuronalnet.transferfunction;

public class StepFunction implements TransferFunction {

	@Override
	public double proceedFunction(double input) {
		if (input < 0) {
			return 0;
		} else {
			return 1;
		}
	}

}
