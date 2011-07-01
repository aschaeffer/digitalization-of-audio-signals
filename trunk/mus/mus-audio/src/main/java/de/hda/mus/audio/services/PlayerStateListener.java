package de.hda.mus.audio.services;

/**
 * Instances of classes implementing this interface can be notified
 * about changes in a players state and loop state.
 * of AudioPlayer's status or to its loop state
 * 
 * @author Joerg Stick
 * @version 1.0
 *
 */

public interface PlayerStateListener 
{
	
	/**
	 * Notification about changes in player state.
	 * @param state The type of change (PLAYING, STOPPED, PAUSED...)
	 */
	public void playerStateChanged(int state);
	
	
	/**
	 * Notification about changes in loop state.
	 * @param loop The new loop state
	 */
	public void loopStateChanged(boolean loop);

}
