package de.hda.mus;

import javax.sound.sampled.LineUnavailableException;

public class Main {

	/**
	 * @param args
	 * @throws LineUnavailableException 
	 */
	public static void main(String[] args) throws LineUnavailableException {
		SinusGenerator sinusGenerator = new SinusGenerator();
		sinusGenerator.setSampleRate(16000f);
		
		/**
		 * Fequency: 100 Hz
		 * Volume: 100
		 * Phase: 0.0f
		 * Duration: 500 ms
		 */
		for (Float i=1f;i<5f;i++) {
			sinusGenerator.play(10000f, 150, i*250f, 500);
		}
	}

}
