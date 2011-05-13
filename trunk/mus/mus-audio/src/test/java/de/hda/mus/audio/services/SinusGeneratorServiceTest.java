package de.hda.mus.audio.services;

import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import org.junit.Test;

import de.hda.mus.audio.domains.AudioContainer;

public class SinusGeneratorServiceTest {

	@Test
	public void testSinusGenerator() throws LineUnavailableException {
/*
		// Sinus Generator
		SinusGeneratorService sinusGenerator = new SinusGeneratorService();
		AudioContainer container = sinusGenerator.generate(new File("demo/sinus.wav"), 200f, 100, 0f, 2000);

		// Output
		System.out.println(container.getTitle());
		System.out.println(container.getAudioContent().audioContentToString());

		AudioPlayerService player2 = new AudioPlayerService();
		player2.setAudioContent(container.getAudioContent());
		SourceDataLine sdl2 = AudioSystem.getSourceDataLine(container.getAudioFormat());
		player2.setOutputLine(sdl2);
		player2.play();
*/
	}

}
