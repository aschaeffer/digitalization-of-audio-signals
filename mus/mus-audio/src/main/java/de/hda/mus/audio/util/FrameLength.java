package de.hda.mus.audio.util;

public class FrameLength {

	public final static Integer getFrameLength(final Float sampleRate, final Integer milliseconds) {
		return (int) Math.ceil(milliseconds * sampleRate / 1000);
	}

}
