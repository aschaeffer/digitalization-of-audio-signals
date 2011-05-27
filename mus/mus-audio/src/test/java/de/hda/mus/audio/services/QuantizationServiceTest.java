package de.hda.mus.audio.services;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.junit.Test;

import de.hda.mus.audio.dao.AudioFileDAO;
import de.hda.mus.audio.domains.AudioContainer;

public class QuantizationServiceTest {

	@Test
	public void testQuantization1Bit() throws FileNotFoundException, IOException, LineUnavailableException, InterruptedException, UnsupportedAudioFileException {
		AudioFileDAO audioFileDAO = new AudioFileDAO();
		File sprache_original = new File("target/test-classes/demo/sprache.wav");
		File sprache_quantized = new File("target/test-classes/demo/sprache-quantized-1bit.wav");
		AudioContainer container = audioFileDAO.load(sprache_original);
		QuantizationService quantizationService = new QuantizationService();
		AudioContainer quantizedContainer = quantizationService.quantize(sprache_quantized, container, 1);
		
		// speichern...
		Boolean success = audioFileDAO.save(quantizedContainer, quantizedContainer.getAudioContent().getStartMark(), quantizedContainer.getAudioContent().getEndMark());
		assertTrue(success);
	}

	@Test
	public void testQuantization2Bit() throws FileNotFoundException, IOException, LineUnavailableException, InterruptedException, UnsupportedAudioFileException {
		AudioFileDAO audioFileDAO = new AudioFileDAO();
		File sprache_original = new File("target/test-classes/demo/sprache.wav");
		File sprache_quantized = new File("target/test-classes/demo/sprache-quantized-2bit.wav");
		AudioContainer container = audioFileDAO.load(sprache_original);
		QuantizationService quantizationService = new QuantizationService();
		AudioContainer quantizedContainer = quantizationService.quantize(sprache_quantized, container, 2);
		
		// speichern...
		Boolean success = audioFileDAO.save(quantizedContainer, quantizedContainer.getAudioContent().getStartMark(), quantizedContainer.getAudioContent().getEndMark());
		assertTrue(success);
	}

	@Test
	public void testQuantization8Bit() throws FileNotFoundException, IOException, LineUnavailableException, InterruptedException, UnsupportedAudioFileException {
		AudioFileDAO audioFileDAO = new AudioFileDAO();
		File sprache_original = new File("target/test-classes/demo/sprache.wav");
		File sprache_quantized = new File("target/test-classes/demo/sprache-quantized-8bit.wav");
		AudioContainer container = audioFileDAO.load(sprache_original);
		QuantizationService quantizationService = new QuantizationService();
		AudioContainer quantizedContainer = quantizationService.quantize(sprache_quantized, container, 8);
		
		// speichern...
		Boolean success = audioFileDAO.save(quantizedContainer, quantizedContainer.getAudioContent().getStartMark(), quantizedContainer.getAudioContent().getEndMark());
		assertTrue(success);
	}

	@Test
	public void testQuantization16Bit() throws FileNotFoundException, IOException, LineUnavailableException, InterruptedException, UnsupportedAudioFileException {
		AudioFileDAO audioFileDAO = new AudioFileDAO();
		File sprache_original = new File("target/test-classes/demo/sprache.wav");
		File sprache_quantized = new File("target/test-classes/demo/sprache-quantized-16bit.wav");
		AudioContainer container = audioFileDAO.load(sprache_original);
		QuantizationService quantizationService = new QuantizationService();
		AudioContainer quantizedContainer = quantizationService.quantize(sprache_quantized, container, 16);
		
		// speichern...
		Boolean success = audioFileDAO.save(quantizedContainer, quantizedContainer.getAudioContent().getStartMark(), quantizedContainer.getAudioContent().getEndMark());
		assertTrue(success);
	}

}
