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

	// private AudioContent audioContent;

	public AudioFileDAO() {

	}

	public AudioContainer load(File file) throws UnsupportedAudioFileException, IOException {
		AudioFileFormat sourceAff;
		AudioInputStream ais;
		AudioFormat internalFormat;
		AudioFileFormat internalAff = null;
		AudioContent audioContent = null;
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
				System.out.println("**  frameLength: " + frameLength);
				internalAff = new AudioFileFormat(AudioFileFormat.Type.WAVE, internalFormat, frameLength);
				audioContent = new AudioContent(sourceAff, internalAff);
			} catch (IOException ioe) {
				return null;
			}
		} else {
			System.out.println("**notEncoded");
			System.out.println("**  frameLength: " + sourceAff.getFrameLength());
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
	 * This method is capable to write a portion of samples from a given
	 * <code>AudioContent</code> object to a WAV file. <b>WARNING:</b> The
	 * internally used AudioFormat (stored in the AudioContent object) will be
	 * used for writing! The method may fail if the internal format is one that
	 * isn't writable! In other words: <b>Do not use this method at all!</b><br>
	 * The method is used internally by the AudioClipboard to simplify the task
	 * of storing its audio content. Because the AudioFormat has been checked
	 * and converted if necessary on loading, the AudioClipboard doesn't need to
	 * care about conversion.
	 * 
	 * @param container
	 *            The AudioContainer to write.
	 * @param offset
	 *            The sample where writing should start.
	 * @param length
	 *            The length to write.
	 * @return A boolean which indicates if writing was successfull or not.
	 */
	public boolean save(AudioContainer container, int offset, int length) {
		File target = container.getFile();
		AudioContent audioContent = container.getAudioContent();
		
		boolean result = false;
		try {
			int bytesRead = 0;
			int usedBufferLength = 0;
			byte[] buffer = new byte[4096];
			int remainingSamples = length;// audioContent.getInternalAudioFileFormat().getFrameLength();
			int frameSize = audioContent.getAudioFormat().getFrameSize();
			int sampleIndex = offset;
			double[][] samples = audioContent.getSamples();
			int channels = audioContent.getAudioFormat().getChannels();

			WaveOutputStream output = new WaveOutputStream(target, audioContent.getAudioFormat());

			while (remainingSamples > 0) {
				usedBufferLength = buffer.length;
				int readLength = remainingSamples * frameSize > usedBufferLength ? usedBufferLength : remainingSamples * frameSize;

				bytesRead = 0;

				for (int bufferIndex = 0; bufferIndex < readLength; sampleIndex++, remainingSamples--) {
					for (int channelIndex = 0; channelIndex < channels; channelIndex++) {
						double sample = samples[channelIndex][sampleIndex];
						sample = sample >= 0 ? sample * Short.MAX_VALUE : -sample * Short.MIN_VALUE;// denormalize
						final short value = (short) (sample + 0.5);// round
						buffer[bufferIndex++] = (byte) (value >>> 0);// pack LSB
						buffer[bufferIndex++] = (byte) (value >>> 8);// pack HSB
						bytesRead += 2;
					}
				}
				output.write(buffer, 0, bytesRead);
			}
			output.close();
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}
		return result;
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
