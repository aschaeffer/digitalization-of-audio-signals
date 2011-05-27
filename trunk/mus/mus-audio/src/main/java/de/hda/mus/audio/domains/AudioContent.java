package de.hda.mus.audio.domains;

import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;

/**
 * This class represents an audioobject. An instance of
 * <code>AudioContent</code> contains the sampledata of an open audiofile in
 * boundaries of [-1 / +1]. Sampledata is stored in a multidimensional array of
 * the data type <code>double</code>. The dimensions of the sample array
 * represent the different channels of the audio material.<br>
 * <br>
 * 
 * == Selection<br>
 * <code>An AudioContent-Object</code> contains a selection. The
 * <code>selection</code> -object represents a user selection (selection within
 * the waveform)<br>
 * <br>
 * == Marker (startMark / endMark)<br>
 * Both markers do <b>not</b> represent a user selection. The markers are used
 * for audioplayback.
 * 
 * @author Joerg Stick
 */
public class AudioContent {

	public static final String FILE_NAME = "FILE_NAME";

	/**
	 * The multidimensional array for storing sample data in boundaries of [-1 /
	 * +1].
	 */
	private double[][] samples;

	/**
	 * The source audio format
	 */
	private AudioFileFormat defaultAudioFileFormat;

	/**
	 * The internally used audio format
	 */
	private AudioFileFormat internalAudioFileFormat;

	/**
	 * A Map object for storing meta data
	 */
	private Map<String, String> metaData;

	/**
	 * The start marker used for audioplayback
	 */
	private int startMark;

	/**
	 * The end marker used for audioplayback
	 */
	private int endMark;

	/**
	 * Contstructs a new <code>AudioContent</code> object with the given default
	 * <code>AudioFileFormat</code> which must be the 'original' format of the
	 * source audio file and the internal <code>AudioFileFormat</code> which is
	 * the format the file was converted into.
	 * 
	 * @param defaultAudioFileFormat
	 *            The <code>AudioFileFormat</code> of the source file.
	 * @param internalAudioFileFormat
	 *            The <code>AudioFileFormat</code> for internal use.
	 */
	public AudioContent(AudioFileFormat defaultAudioFileFormat, AudioFileFormat internalAudioFileFormat) {
		this.defaultAudioFileFormat = defaultAudioFileFormat;
		this.internalAudioFileFormat = internalAudioFileFormat;
		metaData = new HashMap<String, String>();
		samples = new double[getAudioFormat().getChannels()][this.internalAudioFileFormat.getFrameLength()];
		startMark = 0;
		endMark = samples[0].length;
	}

	public AudioContent(AudioFileFormat defaultAudioFileFormat, AudioFileFormat internalAudioFileFormat, double[][] samples) {
		this.defaultAudioFileFormat = defaultAudioFileFormat;
		this.internalAudioFileFormat = internalAudioFileFormat;
		metaData = new HashMap<String, String>();
		this.samples = samples;
		startMark = 0;
		endMark = samples[0].length - 1;
	}

	/**
	 * Get the current end mark.
	 * 
	 * @return the endMark
	 */
	public int getEndMark() {
		return endMark;
	}

	/**
	 * Set a new end mark.
	 * 
	 * @param endMark
	 *            the endMark to set
	 */
	public void setEndMark(int endMark) {
		this.endMark = endMark;
	}

	/**
	 * Resets the end mark to the last available sampleposition.
	 */
	public void resetEndMark() {
		this.endMark = this.samples[0].length;
	}

	/**
	 * Get the current start mark.
	 * 
	 * @return the startMark
	 */
	public int getStartMark() {
		return startMark;
	}

	/**
	 * Set a new start mark.
	 * 
	 * @param startMark
	 *            the startMark to set
	 */
	public void setStartMark(int startMark) {
		this.startMark = startMark;
	}

	/**
	 * Get the meta data object.
	 * 
	 * @return The meta data object.
	 */
	public Map<String, String> getMetaData() {
		return metaData;
	}

	/**
	 * Set the meta data object.
	 * 
	 * @param metaData
	 *            The meta data object to set.
	 */
	public void setMetaData(Map<String, String> metaData) {
		this.metaData = metaData;
	}

	/**
	 * Set the meta data value associated with the specified key.
	 * 
	 * @param key
	 *            The String that identifies the stored object.
	 * @param value
	 *            The meta data value as a String.
	 */
	public void putMetaDataValue(String key, String value) {
		metaData.put(key, value);
	}

	/**
	 * Get the meta data value associated with the specified key.
	 * 
	 * @param key
	 *            The String that identifies the stored object.
	 * @return The meta data value associated with the specified key; returns
	 *         null if the key is not available.
	 */
	public String getMetaDataValue(String key) {
		return metaData.get(key);
	}

	/**
	 * Get the Samples in [-1/+1] boundaries.
	 * 
	 * @return The Samples in [-1/+1] boundaries.
	 */
	public double[][] getSamples() {
		return samples;
	}

	/**
	 * Set the samples.
	 * 
	 * @param samples
	 *            the samples to set
	 */
	public void setSamples(double[][] samples) {
		this.samples = samples;
	}

	/**
	 * Get the AudioFormat of the source file.
	 * 
	 * @return The AudioFormat of the source file.
	 */
	public AudioFormat getAudioFormat() {
		return this.internalAudioFileFormat.getFormat();
	}
	
	/**
	 * Modify the AudioFormat.
	 * @param audioFormat
	 */
	public void setAudioFormat(AudioFormat audioFormat) {
		this.internalAudioFileFormat = new AudioFileFormat(this.internalAudioFileFormat.getType(), audioFormat, this.internalAudioFileFormat.getFrameLength(), this.internalAudioFileFormat.properties());
	}

	/**
	 * Get the 'original' <code>AudioFileFormat</code> of the source audio file.
	 * 
	 * @return The 'original' <code>AudioFileFormat</code> of the source audio
	 *         file.
	 */
	public AudioFileFormat getDefaultAudioFileFormat() {
		return defaultAudioFileFormat;
	}

	/**
	 * Set the default <code>AudioFileFormat</code> which is the orignal format
	 * of the source audio file. Changing the default format may happen on
	 * something like saveAs...
	 * 
	 * @param defaultAudioFileFormat
	 *            The new default <code>AudioFileFormat</code> to set.
	 */
	public void setDefaultAudioFileFormat(AudioFileFormat defaultAudioFileFormat) {
		this.defaultAudioFileFormat = defaultAudioFileFormat;
	}

	/**
	 * Get the <code>AudioFileFormat</code> used for internal representation.
	 * Every loaded audio file will be (if possible) converted to an internal
	 * Format: Encoding: Will be converted to PCM_SIGNED. SampleRate: The sample
	 * rate will not be converted!<br>
	 * SampleSizeInBits: Will be converted to 16 bit.<br>
	 * Endianess: Will be converted to LITTLE_ENDIAN.<br>
	 * FrameSize: Will be converted to 4.<br>
	 * Channels: The channels will not be converted!
	 * 
	 * @return The internal <code>AudioFileFormat</code>
	 */
	public AudioFileFormat getInternalAudioFileFormat() {
		return internalAudioFileFormat;
	}

	/**
	 * Set a new internal audio file format.
	 * 
	 * @param internalAudioFileFormat
	 *            The internal audio format to set.
	 */
	public void setInternalAudioFileFormat(AudioFileFormat internalAudioFileFormat) {
		this.internalAudioFileFormat = internalAudioFileFormat;
	}

	/**
	 * Get an array of max sample values for each channel within the given
	 * length starting at a given offset.
	 * 
	 * @param offset
	 *            The start offset.
	 * @param length
	 *            The length.
	 * @return An array containing the max sample values for each channel of the
	 *         given length starting at offset <code>offset</code>.
	 */
	public double[] getMaxValues(int offset, int length) {

		double[] max = new double[samples.length];

		for (int i = offset; i < offset + length; i++) {
			for (int j = 0; j < samples.length; j++) {
				double sample = Math.abs(samples[j][i]);
				max[j] = max[j] < sample ? sample : max[j];
			}
		}
		return max;
	}

	/**
	 * Get a deep copy from the complete <code>AudioContent</code>. Deep copy
	 * means that a new AudioContent object will be returned by copying the
	 * samples via System.arraycopy().
	 * 
	 * @return A deep copy of the whole <code>AudioContent</code> object.
	 */
	public AudioContent getDeepCopy() {
		AudioFormat iaf = new AudioFormat(getAudioFormat().getSampleRate(), getAudioFormat().getSampleSizeInBits(), getAudioFormat().getChannels(), true, false);
		AudioFileFormat iaff = new AudioFileFormat(AudioFileFormat.Type.WAVE, iaf, getInternalAudioFileFormat().getFrameLength());

		AudioFormat xdaf = getDefaultAudioFileFormat().getFormat();
		AudioFormat daf = new AudioFormat(xdaf.getEncoding(), xdaf.getSampleRate(), xdaf.getSampleSizeInBits(), xdaf.getChannels(), xdaf.getFrameSize(), xdaf
				.getFrameRate(), xdaf.isBigEndian());
		AudioFileFormat daff = new AudioFileFormat(getDefaultAudioFileFormat().getType(), daf, getDefaultAudioFileFormat().getFrameLength());
		AudioContent result = new AudioContent(daff, iaff);
		result.setStartMark(this.getStartMark());
		result.setEndMark(this.getEndMark());
		result.putMetaDataValue(AudioContent.FILE_NAME, "Copy_of_" + this.getMetaDataValue(AudioContent.FILE_NAME));
		int channels = this.getSamples().length;
		int length = this.getSamples()[0].length;
		for (int i = 0; i < channels; i++) {
			System.arraycopy(this.getSamples()[i], 0, result.getSamples()[i], 0, length);
		}
		return result;
	}

	/**
	 * Get a deep copy from a region of the <code>AudioContent</code> specified
	 * by the <code>offset</code> where the copy should start and the <code>
	 * length</code>
	 * to copy. Throws an IllegalArgumentException if:<br>
	 * <code>offset</code> is larger than the length of the
	 * <code>AudioContent</code>s sample array or smaller than 0.<br>
	 * The <code>length</code> to copy is smaller than 1 or larger than the
	 * <code>AudioContents</code> sample array size.<br>
	 * Markers for start and end will be set to 0 and sample length.
	 * 
	 * @param offset
	 *            Sample frame where the copy should beginn.
	 * @param length
	 *            The length to copy.
	 * @return A deep copy of requested region of the <code>AudioContent</code>
	 *         object.
	 * @throws IllegalArgumentException
	 */
	public AudioContent getDeepCopy(int offset, int length) throws IllegalArgumentException {
		int arrayLength = this.samples[0].length;
		if (offset > arrayLength - 1 || offset < 0)
			throw new IllegalArgumentException("offset out of bounds: " + offset);
		if (length < 1 || length > arrayLength)
			throw new IllegalArgumentException("length out of bounds: " + length);

		AudioFormat iaf = new AudioFormat(getAudioFormat().getSampleRate(), getAudioFormat().getSampleSizeInBits(), getAudioFormat().getChannels(), true, false);
		AudioFileFormat iaff = new AudioFileFormat(AudioFileFormat.Type.WAVE, iaf, length);

		AudioFormat xdaf = getDefaultAudioFileFormat().getFormat();
		AudioFormat daf = new AudioFormat(xdaf.getEncoding(), xdaf.getSampleRate(), xdaf.getSampleSizeInBits(), xdaf.getChannels(), xdaf.getFrameSize(), xdaf
				.getFrameRate(), xdaf.isBigEndian());
		AudioFileFormat daff = new AudioFileFormat(getDefaultAudioFileFormat().getType(), daf, length);
		AudioContent result = new AudioContent(daff, iaff);
		result.putMetaDataValue(AudioContent.FILE_NAME, this.getMetaDataValue(AudioContent.FILE_NAME) + " Copy");

		int channels = this.getSamples().length;
		for (int i = 0; i < channels; i++) {
			System.arraycopy(this.getSamples()[i], offset, result.getSamples()[i], 0, length);
		}
		return result;
	}

	// DEBUG
	public String audioContentToString() {
		StringBuffer sBuffer = new StringBuffer();
		// sBuffer.append("zoomLevel: "+zoomLevel+"\n");
		sBuffer.append("endMark: " + endMark + "\n");
		sBuffer.append("startMark: " + startMark + "\n");
		sBuffer.append("frameLength: " + samples[0].length + "\n");
		sBuffer.append("channels: " + (samples.length + 1) + "\n");
		sBuffer.append("internalFormat: " + internalAudioFileFormat + "\n");
		sBuffer.append("srcFormat: " + defaultAudioFileFormat + "\n");
		return sBuffer.toString();
	}

	// DEBUG
	public void printSampleRange(int start, int end) {
		System.out.println("CHANNELCOUNT: " + samples.length);
		/*
		 * for (int i = 0; i < samples.length; i++) {
		 * System.out.println("CHANNEL: "+i); for (int j = start; j < end; j++)
		 * { System.out.print(samples[i][j]+","); } System.out.println(); }
		 */
	}

}
