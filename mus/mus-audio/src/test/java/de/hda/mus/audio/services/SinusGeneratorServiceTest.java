package de.hda.mus.audio.services;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import org.junit.Test;

import de.hda.mus.audio.dao.AudioFileDAO;
import de.hda.mus.audio.dao.WaveOutputStream;
import de.hda.mus.audio.domains.AudioContainer;

public class SinusGeneratorServiceTest {

//	@Test
//	public void testSinusGenerator() throws LineUnavailableException, InterruptedException {
//		// Sinus Generator
//		SinusGeneratorService sinusGenerator = new SinusGeneratorService();
//		AudioContainer container = sinusGenerator.generate(new File("demo/sinus.wav"), 200f, 100, 0f, 2000);
//
//		// Output
//		System.out.println(container.getTitle());
//		System.out.println(container.getAudioContent().audioContentToString());
//
//		AudioPlayerService player = new AudioPlayerService();
//		player.setAudioContent(container.getAudioContent());
//		SourceDataLine sdl = AudioSystem.getSourceDataLine(container.getAudioFormat());
//		player.setOutputLine(sdl);
//		player.play();
//		player.block();
//	}

	@Test
	public void testSaveSinus() throws FileNotFoundException, IOException {
		File sinus1 = new File("target/test-classes/demo/sinus1.wav");
		SinusGeneratorService sinusGenerator = new SinusGeneratorService();
		AudioContainer container = sinusGenerator.generate(sinus1, 200f, 100, 0f, 2000);
		System.out.println(" *** output FrameSize:"+container.getAudioFormat().getFrameSize());
		// WaveOutputStream outputStream = new WaveOutputStream(container.getFile(), container.getAudioFormat());
		
		AudioFileDAO audioFileDAO = new AudioFileDAO();
		Boolean success = audioFileDAO.save(container, container.getAudioContent().getStartMark(), container.getAudioContent().getEndMark());
		assertTrue(success);
		
	}

}
