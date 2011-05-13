package de.hda.mus.audio.services;

import java.io.File;

import javax.sound.sampled.AudioFileFormat;

import de.hda.mus.audio.domains.AudioContainer;
import de.hda.mus.audio.domains.AudioContent;
import de.hda.mus.audio.domains.Peaks;

public class SuperponierService {

	public AudioContainer superponieren(AudioContainer container1, AudioContainer container2, File file, Integer milliseconds) throws Exception {
		if (container1.getAudioContent().getInternalAudioFileFormat().getFrameLength() != container2.getAudioContent().getInternalAudioFileFormat().getFrameLength()) {
			System.out.println("FrameLength stimmt nicht überein");
			throw new Exception("FrameLength stimmt nicht überein");
		}
		if (container1.getAudioContent().getInternalAudioFileFormat().getByteLength() != container2.getAudioContent().getInternalAudioFileFormat().getByteLength()) {
			System.out.println("ByteLength stimmt nicht überein");
			throw new Exception("ByteLength stimmt nicht überein");
		}
		if (!container1.getAudioContent().getInternalAudioFileFormat().getType().equals(container2.getAudioContent().getInternalAudioFileFormat().getType())) {
			System.out.println("Type stimmt nicht überein");
			throw new Exception("Type stimmt nicht überein");
		}
		if (container1.getAudioFormat().getSampleRate() !=  container2.getAudioFormat().getSampleRate()) {
			System.out.println("SampleRate stimmt nicht überein");
			throw new Exception("SampleRate stimmt nicht überein");
		}
		if (container1.getAudioFormat().getEncoding() !=  container2.getAudioFormat().getEncoding()) {
			System.out.println("Encoding stimmt nicht überein");
			throw new Exception("Encoding stimmt nicht überein");
		}
		if (container1.getAudioFormat().getChannels() !=  container2.getAudioFormat().getChannels()) {
			System.out.println("Anzahl der Channels stimmt nicht überein");
			throw new Exception("Anzahl der Channels stimmt nicht überein");
		}
		AudioFileFormat defaultAudioFileFormat = container1.getAudioContent().getDefaultAudioFileFormat();
		AudioFileFormat internalAudioFileFormat = container1.getAudioContent().getInternalAudioFileFormat();
		// Float sampleRate = container1.getAudioFormat().getSampleRate();
		Integer channels = container1.getAudioFormat().getChannels();
		// int frameLength = (int) FrameLength.getFrameLength(sampleRate, milliseconds);
		Integer frameLength = container1.getAudioContent().getInternalAudioFileFormat().getFrameLength();
		double[][] samples = new double[1][frameLength];
		for (int j = 0; j < channels; j++) {
			for (int i = 0; i < frameLength; i++) {
				samples[j][i] = container1.getAudioContent().getSamples()[j][i] + container2.getAudioContent().getSamples()[j][i];
				// System.out.println("1:"+container1.getAudioContent().getSamples()[j][i]+ " 2:"+container2.getAudioContent().getSamples()[j][i]+" RES:"+samples[j][i]);
			}
		}
		AudioContent audioContent = new AudioContent(defaultAudioFileFormat, internalAudioFileFormat, samples);
		Peaks peaks = PeakAnalyzerService.createPeaks(internalAudioFileFormat, samples);
		AudioContainer outContainer = new AudioContainer(file, audioContent, peaks);
		return outContainer;
	}

}
