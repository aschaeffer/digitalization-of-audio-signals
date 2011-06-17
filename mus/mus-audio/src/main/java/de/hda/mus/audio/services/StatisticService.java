package de.hda.mus.audio.services;

import de.hda.mus.audio.domains.AudioContainer;

/**
 * Bitte berechnen Sie für die Testdateien jeweils den Mittelwert, die Varianz und
 * die Standardabweichung der Amplitudenwerte.
 * 
 * @author aschaeffer
 *
 */
public class StatisticService {

	/**
	 * Mittelwert
	 * 
	 * Wir beachten hier erstmal nur Mono-Signale (Spur 0).
	 * 
	 * @param container
	 * @return
	 */
	public double getMean(AudioContainer container) {
		double[] samples = container.getAudioContent().getSamples()[0];
		double s = 0;
		for (int i=0; i<samples.length; i++) {
			s += samples[i];
		}
        return s/samples.length;
	}
	
	/**
	 * Varianz
	 * 
	 * Wir beachten hier erstmal nur Mono-Signale (Spur 0).
	 * 
	 * @param container
	 * @return
	 */
	public double getVariance(AudioContainer container) {
		double[] samples = container.getAudioContent().getSamples()[0];
		double mean = getMean(container);
		double s = 0;
		for (int i=0; i<samples.length; i++) {
			s += Math.pow(samples[i] - mean, 2);
		}
        return s/samples.length;
	}
	
	/**
	 * Standardabweichung
	 * 
	 * Wir beachten hier erstmal nur Mono-Signale (Spur 0).
	 * 
	 * @param container
	 * @return
	 */
	public double getStandardDeviation(AudioContainer container) {
		return Math.sqrt(getVariance(container));
	}

}
