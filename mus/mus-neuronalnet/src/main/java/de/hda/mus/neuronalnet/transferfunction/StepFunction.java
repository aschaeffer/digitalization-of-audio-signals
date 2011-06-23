package de.hda.mus.neuronalnet.transferfunction;

/**
 * Die Sprungfunktion ist keine mathematische Funktion, da an der Sprungstelle
 * mehrere Werte existieren. Wir tun hier mal so, als ob nicht.
 * 
 * @author aschaeffer
 *
 */
public class StepFunction implements TransferFunction {

	@Override
	public double proceedFunction(double input) {
		if (input < 0) {
			return 0;
		} else {
			return 1;
		}
	}

	@Override
	public double proceedDerivativeFunction(double input) {
		return 0;
	}

}
