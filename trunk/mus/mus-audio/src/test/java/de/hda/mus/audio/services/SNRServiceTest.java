package de.hda.mus.audio.services;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.UnsupportedAudioFileException;

import org.junit.Test;

import de.hda.mus.audio.dao.AudioFileDAO;
import de.hda.mus.audio.domains.AudioContainer;

public class SNRServiceTest {

	@Test
	public void getSNRTest() throws UnsupportedAudioFileException, IOException {
		SNRService snrService = new SNRService();
		AudioFileDAO audioFileDAO = new AudioFileDAO();

		File spracheOriginal = new File("target/test-classes/demo/sprache.wav");
		File sprache1bitQuantized = new File("target/test-classes/demo/sprache-quantized-1bit.wav");
		File sprache2bitQuantized = new File("target/test-classes/demo/sprache-quantized-2bit.wav");
		File sprache8bitQuantized = new File("target/test-classes/demo/sprache-quantized-8bit.wav");
		File sprache16bitQuantized = new File("target/test-classes/demo/sprache-quantized-16bit.wav");

		AudioContainer containerOriginal = audioFileDAO.load(spracheOriginal);
		AudioContainer container1bitQuantized = audioFileDAO.load(sprache1bitQuantized);
		AudioContainer container2bitQuantized = audioFileDAO.load(sprache2bitQuantized);
		AudioContainer container8bitQuantized = audioFileDAO.load(sprache8bitQuantized);
		AudioContainer container16bitQuantized = audioFileDAO.load(sprache16bitQuantized);

		System.out.println("SNR original/original: " + snrService.getSNR(containerOriginal, containerOriginal) + " dB");
		System.out.println("SNR original/1 bit quantized: " + snrService.getSNR(containerOriginal, container1bitQuantized) + " dB");
		System.out.println("SNR original/2 bit quantized: " + snrService.getSNR(containerOriginal, container2bitQuantized) + " dB");
		System.out.println("SNR original/8 bit quantized: " + snrService.getSNR(containerOriginal, container8bitQuantized) + " dB");
		System.out.println("SNR original/16 bit quantized: " + snrService.getSNR(containerOriginal, container16bitQuantized) + " dB");

	}

}
