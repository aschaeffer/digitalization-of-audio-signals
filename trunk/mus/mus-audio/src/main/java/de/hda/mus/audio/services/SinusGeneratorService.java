package de.hda.mus.audio.services;

import java.io.File;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.SourceDataLine;

import de.hda.mus.audio.constants.DefaultAudioValues;
import de.hda.mus.audio.domains.AudioContainer;
import de.hda.mus.audio.util.FrameLength;
import de.hda.mus.audio.domains.Peaks;
import de.hda.mus.audio.domains.AudioContent;
import de.hda.mus.audio.services.PeakAnalyzerService;

/**
 * Bitte implementieren Sie einen Signalgenerator, der eine Sinus-Schwingung mit wählbarer
 * Frequenz, Amplitude und Phase mit einer Abtastrate von 16 kHz erzeugt. Legen Sie die Dateien
 * bitte in einem von Ihnen abspielbaren Audio-Format ab.
 * 
 * Erzeugen Sie zwei Dateien, die jeweils ein Sinussignal mit einer Frequenz von 400 Hz
 * enthalten. Die Dateien sollen aber unterschiedliche Amplituden oder Phasen haben. Im
 * Folgenden wird auf diese Dateien mit der Bezeichnung „Testdatei 400 Hz 1“ bzw.
 * „Testdatei 400 Hz 2“ Bezug genommen. Superponieren Sie die beiden Dateien zu einer Datei
 * mit der Bezeichnung „Testdatei 400 Hz superponiert“.
 * 
 * Erzeugen Sie zwei Dateien, die jeweils ein Sinussignal mit einer Frequenz von 3 kHz
 * enthalten. Die Dateien sollen aber unterschiedliche Amplituden oder Phasen haben. Im
 * Folgenden wird auf diese Dateien mit der Bezeichnung „Testdatei 3 kHz 1“ bzw.
 * „Testdatei 3 kHz 2“ Bezug genommen. Superponieren Sie die beiden Dateien zu einer Datei
 * mit der Bezeichnung „Testdatei 3 kHz superponiert“.
 * 
 * @author aschaeffer
 *
 */
public class SinusGeneratorService {

	AudioFormat audioFormat;
	AudioInputStream audioInputStream;
	SourceDataLine sourceDataLine;

	public AudioContainer generate(File file, Float herz, Integer amplitude, Float phase, Integer milliseconds) {
		Integer frameLength = FrameLength.getFrameLength(DefaultAudioValues.DEFAULT_SAMPLERATE, milliseconds);
		AudioFormat internalFormat = new AudioFormat(DefaultAudioValues.DEFAULT_SAMPLERATE, DefaultAudioValues.DEFAULT_SAMPLESIZE, DefaultAudioValues.DEFAULT_CHANNELS, true, false);
		AudioContent audioContent = _generate(herz, amplitude, phase, milliseconds);
		AudioFileFormat internalAff = new AudioFileFormat(AudioFileFormat.Type.WAVE, internalFormat, frameLength);
		Peaks peaks = PeakAnalyzerService.createPeaks(internalAff, audioContent.getSamples());
		AudioContainer container = new AudioContainer(file, audioContent, peaks);
		container.setTitle("Sinus wave (Frequency: " + herz + "Hz Amplitude: " + amplitude + " Phase: " + phase + " Length: " + milliseconds + " ms)");
		return container;
	}

	private AudioContent _generate(Float herz, Integer amplitude, Float phase, Integer milliseconds) {
		AudioFileFormat iaff = new AudioFileFormat(DefaultAudioValues.DEFAULT_AUDIOFILEFORMAT, DefaultAudioValues.DEFAULT_AUDIOFORMAT, milliseconds / 1000 * (int)(DefaultAudioValues.DEFAULT_SAMPLERATE));
		AudioFileFormat daff = new AudioFileFormat(DefaultAudioValues.DEFAULT_AUDIOFILEFORMAT, DefaultAudioValues.DEFAULT_AUDIOFORMAT, milliseconds / 1000 * (int)(DefaultAudioValues.DEFAULT_SAMPLERATE));
		int frameLength = (int) FrameLength.getFrameLength(DefaultAudioValues.DEFAULT_SAMPLERATE, milliseconds);
		double[][] samples = new double[1][frameLength];
		System.out.println("sampleLength: " + frameLength);
		for (int i = 0; i < frameLength; i++) {
			double angle = (i / (DefaultAudioValues.DEFAULT_SAMPLERATE / herz) * 2.0 * Math.PI) + phase;
			// System.out.println("i:"+i+" a:"+angle+" p:"+phase);
			samples[0][i] = (Math.sin(angle) * amplitude);
		}
		AudioContent audioContent = new AudioContent(daff, iaff, samples);
		return audioContent;
	}

}
