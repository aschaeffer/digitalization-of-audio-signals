package de.hda.mus.audio.services;

import java.io.File;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;

import de.hda.mus.audio.domains.AudioContainer;
import de.hda.mus.audio.domains.AudioContent;
import de.hda.mus.audio.domains.Peaks;

public class QuantizationService {

	public AudioContainer quantize(File file, AudioContainer container, Integer resolution) {
		int sampleSize = container.getAudioFormat().getSampleSizeInBits();
		System.out.println("old sampleSize "+sampleSize);
		
		AudioFormat internalFormat = new AudioFormat(container.getAudioFormat().getSampleRate(), container.getAudioFormat().getSampleSizeInBits(), container.getAudioFormat().getChannels(), true, false);
		AudioFileFormat internalAff = new AudioFileFormat(AudioFileFormat.Type.WAVE, internalFormat, container.getAudioContent().getAudioFormat().getFrameSize());
		double[][] samples = container.getAudioContent().getSamples();
		double[][] quantizedSamples = new double[samples.length][samples[0].length];

		// Quantisierungsstufen
		int levels = 1 << resolution;
		double[] qValues = new double[levels];
		double highestValue = getHighestValue(samples);
		double lowestValue = getLowestValue(samples);
		System.out.println("lowestValue "+lowestValue);
		System.out.println("highestValue "+highestValue);
		if (Math.abs(highestValue) > Math.abs(lowestValue)) {
			double totalRangeValue = 2*Math.abs(highestValue);
			double singleRangeValue = totalRangeValue / (levels - 1);
			for (int i=levels-1; i>=0; i--) {
				qValues[i] = highestValue - (i*singleRangeValue);
				System.out.println(" level "+i+": "+qValues[i]);
			}
		} else {
			double totalRangeValue = 2*Math.abs(lowestValue);
			double singleRangeValue = totalRangeValue / (levels - 1);
			for (int i=0; i<levels; i++) {
				qValues[i] = lowestValue + (i*singleRangeValue);
				System.out.println(" level "+i+": "+qValues[i]);
			}
		}

		// Quantisierung 
		for (int x=0; x<samples.length; x++) {
			for (int y=0; y<samples[x].length; y++) {
				int nearestLevel = 0;
				for (int z=1; z<levels; z++) {
					if ((Math.abs(samples[x][y] - qValues[z])) < (Math.abs(samples[x][y] - qValues[nearestLevel])) ) {
						nearestLevel = z;
					}
				}
				// System.out.println(" quantizedSamples["+x+"]["+y+"] = "+qValues[nearestLevel]+" (nearest: "+nearestLevel+", samples["+x+"]["+y+"]: "+samples[x][y]+")");
				quantizedSamples[x][y] =  qValues[nearestLevel];
			}
		}
		AudioContent quantizedContent = new AudioContent(internalAff, internalAff, quantizedSamples);
		Peaks quantizedPeaks = PeakAnalyzerService.createPeaks(internalAff, quantizedContent.getSamples());
		AudioContainer quantizedContainer = new AudioContainer(file, quantizedContent, quantizedPeaks);
		return quantizedContainer;
	}
	
	private double getHighestValue(double[][] samples) {
		double highestValue = 0d;
		for (int x=0; x<samples.length; x++) {
			for (int y=0; y<samples[x].length; y++) {
				if (samples[x][y] > highestValue) {
					highestValue = samples[x][y];
				}
			}
		}
		return highestValue;
	}

	private double getLowestValue(double[][] samples) {
		double lowestValue = 0d;
		for (int x=0; x<samples.length; x++) {
			for (int y=0; y<samples[x].length; y++) {
				if (samples[x][y] < lowestValue) {
					lowestValue = samples[x][y];
				}
			}
		}
		return lowestValue;
	}

}
