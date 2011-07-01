package de.hda.mus.audio.services;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.junit.Test;

import de.hda.mus.audio.dao.AudioFileDAO;
import de.hda.mus.audio.domains.AudioContainer;

public class QuantizationServiceTest {

	/**
	 * Erstellen Sie bitte ein Programm mit dessen Hilfe Sie die Signale der Testdateien quantisieren
	 * können. Realisieren Sie das Programm bitte so, dass Sie die Genauigkeit der Quantisierung
	 * frei wählen können. Beschreiben Sie den Höreindruck bei einer Quantisierung mit 1 bit für die
	 * „Testdatei Sprache“. Hinweis: Die manipulierten Zahlenwerte sollen weiterhin mit 2 Byte abge-
	 * legt werden. Die Quantisierung resultiert in einer entsprechend reduzierten Anzahl möglicher
	 * Zahlenwerte.
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws LineUnavailableException
	 * @throws InterruptedException
	 * @throws UnsupportedAudioFileException
	 */
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

	/**
	 * Just for fun: wie hört es sich bei 2 Bit an?
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws LineUnavailableException
	 * @throws InterruptedException
	 * @throws UnsupportedAudioFileException
	 */
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

	/**
	 * Just for fun: wie hört es sich bei 8 Bit an?
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws LineUnavailableException
	 * @throws InterruptedException
	 * @throws UnsupportedAudioFileException
	 */
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
