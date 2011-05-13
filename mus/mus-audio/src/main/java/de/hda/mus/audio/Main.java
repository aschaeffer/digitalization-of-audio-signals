package de.hda.mus.audio;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import de.hda.mus.audio.dao.AudioFileDAO;
import de.hda.mus.audio.domains.AudioContainer;
import de.hda.mus.audio.services.AudioPlayerService;

public class Main {

	public static void main(String[] args) throws LineUnavailableException {
		// WAV Datei abspielen
		File file = new File("target/test-classes/demo/sprache.wav");
		AudioFileDAO audioFileDAO = new AudioFileDAO();
		try {
			AudioContainer container = audioFileDAO.load(file);
			System.out.println(container.getTitle());
			System.out.println(container.getAudioContent().audioContentToString());

			AudioPlayerService player = new AudioPlayerService();
			player.setAudioContent(container.getAudioContent());
			SourceDataLine outputLine = AudioSystem.getSourceDataLine(container.getAudioFormat());
			player.setOutputLine(outputLine);
			player.play();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}

}
