package de.hda.mus.audio.dao;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import de.hda.mus.audio.constants.DefaultAudioValues;
import de.hda.mus.audio.domains.AudioContainer;
import de.hda.mus.audio.domains.AudioContent;
import de.hda.mus.audio.services.PeakAnalyzerService;
import de.hda.mus.audio.domains.Peaks;

public class AudioFileDAO {

	private AudioContent audioContent;

	public AudioFileDAO() {

	}

	public AudioContainer load(File file) throws UnsupportedAudioFileException, IOException {
		AudioFileFormat sourceAff;
		AudioInputStream ais;
		AudioFormat internalFormat;
		AudioFileFormat internalAff = null;
		audioContent = null;
		byte[] buffer = new byte[4096];

		sourceAff = AudioSystem.getAudioFileFormat(file);
		int channels = sourceAff.getFormat().getChannels();
		float sampleRate = sourceAff.getFormat().getSampleRate();
		internalFormat = new AudioFormat(sampleRate, DefaultAudioValues.DEFAULT_SAMPLESIZE, channels, true, false);
		ais = AudioSystem.getAudioInputStream(internalFormat, AudioSystem.getAudioInputStream(file));

		// QUESTIONABLE !?!
		// if file is encoded (like mp3) the amount of sample frames
		// is unknown. We have to count the frames ourself by running through
		// the whole AudioInputStream
		if (isEncoded(sourceAff.getFormat().getEncoding())) {
System.out.println("**isEncoded");
			int frameLength = 0;
			int bRead = 0;
			byte buf[] = new byte[4096];

			try {
				while ((bRead = ais.read(buf)) != -1) {
					frameLength += bRead;
				}
				frameLength /= ais.getFormat().getChannels() * ais.getFormat().getSampleSizeInBits() / 8;
System.out.println("**  frameLength: "+frameLength);
				internalAff = new AudioFileFormat(AudioFileFormat.Type.WAVE, internalFormat, frameLength);
				audioContent = new AudioContent(sourceAff, internalAff);
			} catch (IOException ioe) {
				return null;
			}
		} else {
System.out.println("**notEncoded");
System.out.println("**  frameLength: "+sourceAff.getFrameLength());
			internalAff = new AudioFileFormat(AudioFileFormat.Type.WAVE, internalFormat, sourceAff.getFrameLength());
			audioContent = new AudioContent(sourceAff, internalAff);
		}

		AudioInputStream _ais = AudioSystem.getAudioInputStream(internalFormat, AudioSystem.getAudioInputStream(file));

		double[][] samples = audioContent.getSamples();
		int sampleIndex = 0;
		int bytesRead = 0;

		// read the (converted) AudioInputStream and fill our
		// sample array
		while ((bytesRead = _ais.read(buffer)) != -1) {

			for (int bufferSampleIndex = 0; bufferSampleIndex < bytesRead; sampleIndex++) {

				for (int channelIndex = 0; channelIndex < channels; channelIndex++) {
					double sample = (buffer[bufferSampleIndex] & 0xFF) + (buffer[bufferSampleIndex + 1] << 8);// unpack
					sample = sample >= 0 ? sample / Short.MAX_VALUE : -sample / Short.MIN_VALUE; // normalize
					// System.out.println(sampleIndex);
					samples[channelIndex][sampleIndex] = sample;
					bufferSampleIndex += 2;
				}
			}
		}

		audioContent.putMetaDataValue(AudioContent.FILE_NAME, file.getName());
		Peaks peaks = PeakAnalyzerService.createPeaks(internalAff, audioContent.getSamples());
		ais.close();
		_ais.close();
		return new AudioContainer(file, audioContent, peaks);

	}
	/**
	 * Checks if the given audio format is an encoded one.
	 * 
	 * @param encoding
	 *            The encoding of the file.
	 * @return boolean which indicates if the format is encoded or not.
	 */
	private boolean isEncoded(AudioFormat.Encoding encoding) {
		boolean en = !encoding.equals(AudioFormat.Encoding.PCM_UNSIGNED) && !encoding.equals(AudioFormat.Encoding.PCM_SIGNED);
		return en;
	}

}
