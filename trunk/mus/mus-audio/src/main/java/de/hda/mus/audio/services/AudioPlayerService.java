package de.hda.mus.audio.services;

import java.util.ArrayList;
import java.util.Iterator;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import de.hda.mus.audio.domains.AudioContent;

/**
 * This class represents an in-memory audio player. In-memory means that the
 * player expects audio data stored in an multidimensional double array
 * [channels][audio data] where audio data must be available in normalized
 * values (-1/+1 boundaries).
 * 
 * @author Joerg Stick
 */
public class AudioPlayerService implements Runnable {
	// The audio content to play
	private AudioContent audioContent;
	// The Thread used for playback
	protected Thread playerThread;
	// The priority of the players thread (1-10)
	protected int threadPriority = 9;
	// The condition for the main loop in method run
	private boolean run = false;
	// The List of registered AudioPlayerListeners
	private ArrayList<PlayerStateListener> listeners = new ArrayList<PlayerStateListener>();
	// The actual loop state
	private boolean loopState = false;
	// The actual player state
	private int currentState = NOT_READY;
	// The SourceDataLine used for playback
	private SourceDataLine outputLine;
	// TEST WAIT MONITOR OBJECT
	private Object monitor;
	// The sample buffer
	private byte[] buffer;
	// The default buffer size
	private int bufferSize = 256;
	// The sample array for playback
	private double[][] samples;

	// The actual length of bytes read
	private int readLength = 0;
	private int sampleIndex = 0;
	private int loopCount = 0;

	// Values for player state changes
	public static final int STOPPED = 0;
	// Values for player state changes
	public static final int STARTED = 1;
	// Values for player state changes
	public static final int PAUSED = 2;
	// Values for player state changes
	public static final int RESUMED = 3;

	// Values for actual player status
	public static final int PLAYING = 100;
	// Values for actual player status
	public static final int PAUSE_PLAY = 101;
	// Values for actual player status
	public static final int READY = 102;
	// Values for actual player status
	public static final int NOT_READY = 105;

	/**
	 * Constructs a new AudioPlayer without AudioContent and SourceDataLine for
	 * playback. Both can be set later by invoking
	 * <code>setAudioContent()</code> and <code>setOutpuLine()</code>.
	 */
	public AudioPlayerService() {
	}

	/**
	 * Set the current <code>AudioContent</code> for playback.
	 * 
	 * @param audioContent
	 *            The actual <code>AudioContent</code> for playback to set.
	 * @uml.property name="audioContent"
	 */
	public void setAudioContent(AudioContent audioContent) {
		if (audioContent != null) {
			if (outputLine != null && outputLine.isActive()) {
				stop();
				System.out.println("STOPPING PLAYER: New AudioContent set");
			}
			currentState = READY;
			notifyListenersOnPlayerStateChanged();
			this.audioContent = audioContent;
			samples = audioContent.getSamples();
		} else {
			if (outputLine != null && outputLine.isActive()) {
				stop();
				System.out.println("STOPPING PLAYER: New AudioContent set");
			}
			samples = null;
			this.audioContent = null;
			currentState = NOT_READY;
			notifyListenersOnPlayerStateChanged();
		}
	}

	/**
	 * Get the <code>AudioContent</code> which is actually used for playback.
	 * 
	 * @return The <code>AudioContent</code> which is a
	 */
	public AudioContent getAudioContent() {
		return audioContent;
	}

	/**
	 * Set the <code>SourceDataLine</code> wich the player should use for
	 * playback audio data. If the player already has a
	 * <code>SourceDataLine</code> object the player will be stoped by invoking
	 * its <code>stop()</code> method.
	 * 
	 * @param outputLine
	 *            The <code>SourceDataLine</code> the player should use for
	 *            playback.
	 */
	public void setOutputLine(SourceDataLine outputLine) {
		run = false;// ?????????????????????????????????????????
		if (outputLine != null) {
			if (outputLine.isActive()) {
				stop();
			} else {
				outputLine.close();
			}
		}

		this.outputLine = outputLine;
	}

	/**
	 * Get the <code>SourceDataLine</code> which the player currently uses
	 * playing back audio data.
	 * 
	 * @return The <code>SourceDataLine</code> object used for playback.
	 */
	public SourceDataLine getOutputLine() {
		return outputLine;
	}

	/**
	 * Get the current state of the player.
	 * 
	 * @return The current state as a int value.
	 */
	public int getCurrentState() {
		return currentState;
	}

	/**
	 * Set the loop state.
	 * 
	 * @param loopState
	 *            The current loop state to set.
	 */
	public void setLoopState(boolean loopState) {
		this.loopState = loopState;
	}

	/**
	 * Get the actual loop state
	 * 
	 * @return loopstate The actual loop state
	 */
	public boolean getLoopState() {
		return this.loopState;
	}

	/**
	 * Switch to the opposite of the current loop state.
	 */
	public void switchLoopState() {
		loopState = !loopState;
		notifyListenersOnLoopStateChanged(loopState);
	}

	/**
	 * @return
	 */
	public double[] getPeakLevels() {
		// find out the amount of loops by the line because
		// the line getFramePosition() returns a value running ad infinitum.
		// --> no reset to 0 on loop situation, because the line will not be
		// closed
		// on loop.
		// 1. get current played frame position from the line
		long linePosition = outputLine.getLongFramePosition();
		// 2. get the range that would be looped
		int startFrame = audioContent.getStartMark();
		int frameLength = audioContent.getEndMark() - startFrame;
		// 3. calculate the amount of loops happend in the line up to now.
		// int lineLoops = (int)(linePosition / sLength);
		int lineLoops = (int) (linePosition / frameLength);
		// 4. substract (the amount of line-loops * the looped length) from the
		// current line
		// frame position and add the start frame position to get an idea of the
		// current play position.
		int readPosition = (int) (linePosition - (lineLoops * frameLength) + startFrame);
		// 5. the amount of samples to calculate the peaks from
		int buf = 1000;
		// 6. use a read length that is not larger than our sample range
		// to avoid an ArrayIndexOutOfBoundsException
		int length = (readPosition + buf > frameLength + startFrame) ? (frameLength - readPosition) : buf;
		// System.out.println("linePos: "+linePosition+" lineLoops: "+lineLoops+
		// "frameLength: "+frameLength+" rp: "+readPosition +" le: "+length);
		return audioContent.getMaxValues(readPosition, length);
	}

	/**
	 * Get the actual played sample frame position.
	 * 
	 * @return The actual played sample frame position.
	 */
	protected long getFramePosition() {
		int realPosition = 0;
		if (outputLine != null && audioContent != null) {
			// find out the amount of loops by the line because
			// the line getFramePosition() returns a value running ad infinitum.
			// --> no reset to 0 on loop situation, because the line is not
			// closed
			// on loop.
			// 1 get current played position from the line
			long linePosition = outputLine.getLongFramePosition();
			// 2 get the range that should be looped
			int loopLength = audioContent.getEndMark() - audioContent.getStartMark();
			// 3 avoid division by zero exception
			loopLength = loopLength == 0 ? 1 : loopLength;
			// 4 calculate the amount of loops happend up to now.
			int lineLoops = (int) (linePosition / loopLength);
			// 5 substract (the amount of line-loops * the looped length) from
			// the
			// current frame position given form the line and add the start
			// marker postion

			realPosition = (int) (linePosition - lineLoops * loopLength + audioContent.getStartMark());
		}
		return realPosition;
	}

	/**
	 * This method returns true if the player is playing or in paused.
	 * 
	 * @return True if the player is 1. playing and 2. in pause mode.
	 */
	public boolean isRunning() {
		return run;
	}

	public boolean isLineActive() {
		if (outputLine != null)
			return outputLine.isRunning();
		else
			return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		monitor = new Object();
		samples = audioContent.getSamples();
		sampleIndex = audioContent.getStartMark();
		buffer = new byte[bufferSize];
		// int channels = audioContent.getAudioFormat().getChannels();
		int channels = samples.length;
		int bytesWritten = 0;
		int usedBufferLength = 0;
		int remainingSamples = audioContent.getEndMark() - audioContent.getStartMark();
		readLength = 0;
		int bytesRead = 0;

		while (run) {
			Thread.yield(); // ???

			// do nothing in PAUSED mode
			if (currentState == PAUSED) {
				doWait();
			} else {
				// ---CALCULATE PROPER LENGTH TO
				// READ-------------------------------------

				// define the used buffer length by taking the size from our
				// buffer array
				usedBufferLength = buffer.length;

				// if player was paused:
				// look at the available buffer size of the line an compare to
				// the size
				// of our buffer array. Take the one that is smaller to avoid
				// line blocking

				if (!outputLine.isActive()) {
					usedBufferLength = Math.min(outputLine.available(), buffer.length);
				}

				// in every player state:
				// TODO:
				// no need to get frame size in every while-loop
				// --> do it somewhere else
				int frameSize = audioContent.getAudioFormat().getFrameSize();

				// take control of how many samples we are allowed to read
				// without running in
				// an IndexOutOfBounds Exception
				readLength = remainingSamples * frameSize > usedBufferLength ? usedBufferLength : remainingSamples * frameSize;

//				System.out.println(readLength);
//				System.out.println("bytesWritten: "+bytesWritten +
//				" bytesRead: "+bytesRead);


				// ---READ-------------------------------------------------------------
				if (bytesWritten == bytesRead) {

					bytesRead = 0;

					for (int bufferIndex = 0; bufferIndex < readLength; sampleIndex++, remainingSamples--) {
						for (int channelIndex = 0; channelIndex < channels; channelIndex++) {
							double sample = samples[channelIndex][sampleIndex];
							sample = sample >= 0 ? sample * Short.MAX_VALUE : -sample * Short.MIN_VALUE;// denormalize
							final short value = (short) (sample + 0.5);// round
							buffer[bufferIndex++] = (byte) (value >>> 0);// pack
																			// LSB
							buffer[bufferIndex++] = (byte) (value >>> 8);// pack
																			// HSB
							bytesRead += 2;
						}
					}
					// if we read a portion of samples we have to 'reset'
					// the bytesWritten value, because of the
					// bytesRead/bytesWritten
					// condition.
					bytesWritten = 0;
				}

				// ---WRITE-------------------------------------------------------------
				if (bytesRead > 0) {
					bytesWritten += outputLine.write(buffer, bytesWritten, bytesRead - bytesWritten);
					/*
					 * if(currentState == RESUMED || currentState == STARTED){
					 * outputLine.start(); }
					 */
				}

				// bytesWritten != bytesRead --> following operations depend on
				// loop
				else {
					// ---LOOP:
					// YES--------------------------------------------------------
					if (loopState) {
						loopCount++;
						// necessary because of the read-condition
						bytesWritten = bytesRead = 0;
						// get the start sample index for playback
						sampleIndex = audioContent.getStartMark();
						// calculate the amount of remaining samples for
						// playback
						// using the audioContents mark positions
						remainingSamples = audioContent.getEndMark() - audioContent.getStartMark();
					}
					// ---LOOP:
					// NO---------------------------------------------------------
					else {
						// let the line play its remaining Samples.
						outputLine.drain();
						// stop the player
						stop();
					}
				}
			}
		}
	}

	// ********************** TEST **********************************

	// TODO: UNBEDINGT ABKL�RKEN WAS BESSER IST!!!!
	// PAUSE MIT WAIT-MONITOR ODER MIT SLEEP

	private void doWait() {
		try {
			synchronized (monitor) {
				monitor.wait();
			}
		} catch (InterruptedException ie) {
		}
	}

	private void wakeup() {
		synchronized (monitor) {
			monitor.notify();
		}
	}

	// ************************************************************

	/**
	 * Start playing
	 */
	public synchronized void play() {
		if (currentState == RESUMED || currentState == PLAYING || currentState == PAUSED || currentState == STARTED) {
			// do nothing here
		} else {
			run = true;
			currentState = STARTED;
			notifyListenersOnPlayerStateChanged();
			try {
				outputLine.open();
				outputLine.start();

				playerThread = new Thread(this);
				playerThread.setName("SamplePlayer");
				playerThread.setPriority(threadPriority);
				playerThread.start();
			} catch (LineUnavailableException e) {
				// DEBUG
				e.printStackTrace();
			}

		}
	}
	
	public void block() throws InterruptedException {
		playerThread.join();
	}

	/**
	 * Stop playing.
	 */
	public synchronized void stop() {
		if (currentState == STOPPED) {
			// do nothing here
		}
		/*
		 * else if(currentState==PAUSED) { currentState=STOPPED;
		 * outputLine.stop(); notifyListenersOnPlayerStateChanged(); }
		 */
		else {
System.out.println("STOPPING");
			currentState = STOPPED;
			run = false;
			sampleIndex = audioContent.getStartMark();
			loopCount = 0;
			notifyListenersOnPlayerStateChanged();
			outputLine.flush();
			outputLine.stop();
			outputLine.close();
			playerThread = null;
		}
	}

	/**
	 * Pause playing.
	 */

	// TODO: cases vereinfachen STARTED,PLAYING und RESUMED sind gleich!!!
	public void pause() {
		switch (currentState) {
		case PAUSED:
			currentState = RESUMED;
			outputLine.start();
			wakeup();
			notifyListenersOnPlayerStateChanged();
			break;
		case STARTED:
			currentState = PAUSED;
			outputLine.stop();
			notifyListenersOnPlayerStateChanged();
			break;
		case PLAYING:
			currentState = PAUSED;
			outputLine.stop();
			notifyListenersOnPlayerStateChanged();
			break;
		case RESUMED:
			currentState = PAUSED;
			outputLine.stop();
			notifyListenersOnPlayerStateChanged();
			break;
		}
	}

	// Listener Methods

	/**
	 * Notify registered listeners about changes in loop state.
	 * 
	 * @param loop
	 *            The new loop state.
	 */
	private void notifyListenersOnLoopStateChanged(boolean loopstate) {
		for (Iterator<PlayerStateListener> iter = listeners.iterator(); iter.hasNext();) {
			(iter.next()).loopStateChanged(loopstate);
		}
	}

	/**
	 * Notify registered listeners about changes in player state.
	 */
	private void notifyListenersOnPlayerStateChanged() {
		for (Iterator<PlayerStateListener> iter = listeners.iterator(); iter.hasNext();) {
			(iter.next()).playerStateChanged(currentState);
		}
	}

	/**
	 * Register a PlayerStateListener.
	 * 
	 * @param listener
	 *            The PlayerStateListener.
	 */
	public void addPlayerStateListener(PlayerStateListener listener) {
		listeners.add(listener);
	}

	/**
	 * Remove a registered PlayerStateListener.
	 * 
	 * @param listener
	 *            The PlayerStateListener.
	 */
	public void removePlayerStateListener(PlayerStateListener listener) {
		listeners.remove(listener);
	}

}
