package de.hda.mus.audio.services;

import javax.sound.sampled.AudioFileFormat;

import de.hda.mus.audio.domains.Peaks;

/**
 * Berechnet Min/Max-Werte aus dem übergebenen audiodaten. Die Berechneten Werte
 * werden zur Darstellung der Wellenfornm in großen Zoomstufen verwendet.<br>
 * Momentan wird eine feste Array-Größe verwendet. Das ist eventuell der Grund
 * dafür, beim Wechsel von den Peak- auf die Originaldaten keinen fließender
 * Übergang besteht (bei großen files)
 * 
 * Muss unbedingt verbessert werden!
 * 
 * @author Joerg Stick
 * 
 */
public class PeakAnalyzerService {

	public static final double PEAK_ARRAY_LENGTH = 40000D;

	public static Peaks createPeaks(AudioFileFormat internalFormat, double[][] samples) {

		double minMaxInterval = internalFormat.getFrameLength() / PEAK_ARRAY_LENGTH;

		// int channels = internalFormat.getFormat().getChannels();
		int channels = samples.length;

		Peaks peaks = new Peaks(minMaxInterval, channels, internalFormat.getFrameLength());
		double[][] minMax = new double[channels][2];

		// int size = (int)((internalFormat.getFrameLength() /
		// minMaxInterval)*2);
		// System.out.println("PEAK ANALYZER: size: "+size);
		double[][] pks = peaks.getPeakData();

		int pos = 0;
		int lastpos = 0;
		for (int i = 0; i < channels; i++) {
			minMax[i][0] = -1; // max
			minMax[i][1] = 1; // min
		}

		int i = 0;

		try {

			int length = samples[0].length;
			for (; i < length; i++) {
				pos = (int) (i / minMaxInterval * 2);
				for (int j = 0; j < channels; j++) {
					double sample = samples[j][i];
					if (sample > minMax[j][0])
						minMax[j][0] = sample;
					if (sample < minMax[j][1])
						minMax[j][1] = sample;

					// if(i % interval == 0 || i == length-1)
					if (pos > lastpos) {
						pks[j][lastpos] = minMax[j][0];
						pks[j][pos] = minMax[j][1];

						minMax[j][0] = -1; // max
						minMax[j][1] = 1; // min
					}
				}
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("PeakAnalyzer: ArrayIndexOutOfBoundsException; pos: " + pos + " i: " + i);
		}

		return peaks;
	}

}
