package de.hda.mus.audio.services;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.junit.Test;

import de.hda.mus.audio.dao.AudioFileDAO;
import de.hda.mus.audio.domains.AudioContainer;

public class AudioPlayerServiceTest {

	@Test
	public void testAudioPlayerService() throws LineUnavailableException, UnsupportedAudioFileException, IOException {
		// WAV Datei abspielen
//		File dir1 = new File (".");
//		System.out.println ("Current dir : " + dir1.getCanonicalPath());
		File file = new File("target/test-classes/demo/sprache.wav");
//		File file = new File("target/test-classes/demo/test.wav");
		AudioFileDAO audioFileDAO = new AudioFileDAO();
		AudioContainer container = audioFileDAO.load(file);
		System.out.println(container.getTitle());
		System.out.println(container.getAudioContent().audioContentToString());

		SourceDataLine outputLine = AudioSystem.getSourceDataLine(container.getAudioFormat());
		AudioPlayerService player = new AudioPlayerService();
		player.setAudioContent(container.getAudioContent());
		player.setOutputLine(outputLine);
		player.play();
	}

}
