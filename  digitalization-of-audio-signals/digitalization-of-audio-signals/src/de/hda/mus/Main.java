package de.hda.mus;

import javax.sound.sampled.LineUnavailableException;

public class Main {

	/**
	 * @param args
	 * @throws LineUnavailableException 
	 */
	public static void main(String[] args) throws LineUnavailableException {
		// TODO Auto-generated method stub
		// AnnotationA
		SinusGenerator sinusGenerator = new SinusGenerator();
		
		/**
		 * Fequency: 100 Hz
		 * Volume: 100
		 * Phase: 0.0f
		 * Duration: 500 ms
		 */
		sinusGenerator.play(100f, 100, 0.0f, 500);
	}

}
