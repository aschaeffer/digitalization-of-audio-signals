package de.hda.mus.audio.domains;

import java.io.File;

import javax.sound.sampled.AudioFormat;

import de.hda.mus.audio.domains.AudioContent;
import de.hda.mus.audio.domains.Peaks;

/**
 * An AudioController represents an open audio file inside the SampleEditor. It
 * contains the audio data (AudioContent) as well as some gui elements like the
 * WaveFormPanel, a ScrollBar, the Zoom object and a Peak object. (The Peak
 * object contains an Array of pre rendered min/max values which are used to
 * draw the waveform in the higher zoom levels).
 * 
 * @author Joerg Stick
 */
public class AudioContainer {
	/**
	 * The title shown on the tab.
	 */
	private String title;
	/**
	 * Represents the physical file on disk. May be null (Generated
	 * audioobjekts)
	 */
	private File file;
	/**
	 * Indicates if the file has been modified or not
	 */
	private boolean modified;
	/**
	 * The audio content.
	 */
	private AudioContent audioContent;
	/**
	 * A Peaks object holding the pre-calculated min/max peaks for a certain
	 * interval.
	 */
	private Peaks peaks;

	/**
	 * Constructs a new AudioController.
	 * 
	 * @param fiel
	 *            The file which represents the physical audio file on harddisc.
	 * @param audioContent
	 *            The audio data.
	 * @param peaks
	 *            The Peak object.
	 */
	public AudioContainer(File file, AudioContent audioContent, Peaks peaks) {
		this.file = file;
		this.audioContent = audioContent;
		this.title = audioContent.getMetaDataValue(AudioContent.FILE_NAME);
		this.peaks = peaks;
		modified = false;
	}

	/**
	 * Get the AudioContent managed by this AudioController.
	 * 
	 * @return The AudioContent this managed by this AudioController.
	 */
	public AudioContent getAudioContent() {
		return audioContent;
	}

	/**
	 * Set the <code>AudioContent</code> for this <code>AudioController</code>
	 * 
	 * @param The
	 *            AudioContent to set.
	 */
	public void setAudioContent(AudioContent audioContent) {
		this.audioContent = audioContent;
	}

	/**
	 * 
	 * @return
	 */
	public AudioFormat getAudioFormat() {
		return audioContent.getAudioFormat();
	}
	
	public void setAudioFormat(AudioFormat audioFormat) {
		audioContent.setAudioFormat(audioFormat);
	}
	
	/**
	 * Get the title of the audio file.
	 * 
	 * @return The Tab representation title as a String.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Set the title of the audio file.
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Get the Peaks object used for drawing the waveform in high zoom levels.
	 * 
	 * @return
	 */
	public Peaks getPeaks() {
		return peaks;
	}

	/**
	 * Set the Peaks object used for drawing the waveform in high zoom levels.
	 * 
	 * @param peaks
	 */
	public void setPeaks(Peaks peaks) {
		this.peaks = peaks;
	}

	/**
	 * Get the File object which represents the physical audiofile on harddisc.
	 * 
	 * @return The File object.
	 */
	public File getFile() {
		return file;
	}

	/**
	 * Set the file object for the AudioController which represents the physical
	 * audiofile on harddisc.
	 * 
	 * @param file
	 *            The new file object to set.
	 */
	public void setFile(File file) {
		this.file = file;
	}

	/**
	 * Get the audio data modified state. Calling the method
	 * 
	 * @return A boolean which indicates if the controllers audiodata has been
	 *         modified or not.
	 */
	public boolean isModified() {
		return modified;
	}

	/**
	 * Set the audio data modified state.
	 * 
	 * @param modified
	 *            Boolean
	 */
	public void setModified(boolean modified) {
		this.modified = modified;
	}

}
