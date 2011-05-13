package de.hda.mus.audio.domains;

import java.io.Serializable;

/**
 * This class contains an mulitdimensional array holding pre-rendered min/max
 * values used for drawing the waveform in high zoom levels. The class is used
 * by the PeakAnalyzer which creates the peaks.
 * 
 * @author Joerg Stick
 */
public class Peaks implements Serializable {

	/**
	 * The serial.
	 */
	private static final long serialVersionUID = 3109558522949562527L;

	/**
	 * Die Berechnung der Min/Max-Werte aus dem Ausgangsmaterial erfolg anhand
	 * eines berechneten Intervalls. D. h. ein Min und Max Wert steht f�r die
	 * Smaplewerte eines Intervalls. snapShotIntervall ist die Intervalll�nge.
	 */
	private double snapShotIntervall = 1D;
	/**
	 * array containing min/max-Values
	 */
	private double[][] peaks;

	/**
	 * Contstructs a new Peak object with the given snapShotIntervall, channels
	 * and frameLength. The Peak-Array with the proper size will be created.
	 * 
	 * @param channels
	 *            The audiodatas channels
	 * @param frameLength
	 *            The length of the audiodata
	 */
	public Peaks(double snapShotIntervall, int channels, long frameLength) {
		this.snapShotIntervall = snapShotIntervall;
		int size = (int) ((frameLength / this.snapShotIntervall) * 2);
		peaks = new double[channels][size];
	}

	/**
	 * Get peak data.
	 * 
	 * @return peaks The array containig min/max-values
	 */
	public double[][] getPeakData() {
		return peaks;
	}

	/**
	 * Get the intevallsize used for creating peaks.
	 * 
	 * @return The intervallsize used for creating peaks.
	 */
	public double getSnapShotIntervall() {
		return snapShotIntervall;
	}

}
