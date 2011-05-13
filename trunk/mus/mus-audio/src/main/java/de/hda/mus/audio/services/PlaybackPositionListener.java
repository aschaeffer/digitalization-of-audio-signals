package de.hda.mus.audio.services;

/**
 * @author Joerg Stick
 *
 */
public interface PlaybackPositionListener
{
	/**
	 * @param framePosition The current framePosition.
	 */
	public void playbackPositionChanged(long framePosition, int sampleRate);
}
