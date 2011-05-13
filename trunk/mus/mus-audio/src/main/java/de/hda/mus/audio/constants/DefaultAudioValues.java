package de.hda.mus.audio.constants;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;

public class DefaultAudioValues {

	/**
	 * The default samplerate
	 */
	public static final float DEFAULT_SAMPLERATE = 44100f;

	/**
	 * The internal used default sample size in bits
	 */
	public static final int DEFAULT_SAMPLESIZE = 16;

	/**
	 * The default channels = mono
	 */
	public static final int DEFAULT_CHANNELS = 1;

	/**
	 * The default audio file format
	 */
	public static final AudioFileFormat.Type DEFAULT_AUDIOFILEFORMAT = AudioFileFormat.Type.WAVE;

	/**
	 * The default audio file format
	 */
	public static final AudioFormat DEFAULT_AUDIOFORMAT = new AudioFormat(DEFAULT_SAMPLERATE, DEFAULT_SAMPLESIZE, DEFAULT_CHANNELS, true, false);

}
