package de.hda.mus.audio.services;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.junit.Test;

import de.hda.mus.audio.dao.AudioFileDAO;
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

//	Erzeugen Sie zwei Dateien, die jeweils ein Sinussignal mit einer Frequenz von 400 Hz enthal-
//	ten. Die Dateien sollen aber unterschiedliche Amplituden oder Phasen haben. Im Folgenden
//	wird auf diese Dateien mit der Bezeichnung „Testdatei 400 Hz 1“ bzw. „Testdatei 400 Hz 2“ Be-
//	zug genommen. Superponieren Sie die beiden Dateien zu einer Datei mit der Bezeichnung
//	„Testdatei 400 Hz superponiert“.

	/**
	 * Testdatei-400Hz-1.wav
	 * Frequenz:   400 Hz
	 * Amplitude:  200
	 * Phase:        0
	 * Laenge:    5000 ms
	 * @throws LineUnavailableException 
	 * @throws InterruptedException 
	 */
	@Test
	public void testSaveSinus1() throws FileNotFoundException, IOException, LineUnavailableException, InterruptedException {

		// sinus schwingung generieren
		File sinus1 = new File("target/test-classes/demo/Testdatei-400Hz-1.wav");
		SinusGeneratorService sinusGenerator = new SinusGeneratorService();
		AudioContainer container = sinusGenerator.generate(sinus1, 400f, 200, 0f, 1000);

		// speichern...
		AudioFileDAO audioFileDAO = new AudioFileDAO();
		Boolean success = audioFileDAO.save(container, container.getAudioContent().getStartMark(), container.getAudioContent().getEndMark());
		assertTrue(success);

		// .. oder abspielen
//		AudioPlayerService player = new AudioPlayerService();
//		player.setAudioContent(container.getAudioContent());
//		SourceDataLine outputLine = AudioSystem.getSourceDataLine(container.getAudioFormat());
//		player.setOutputLine(outputLine);
//		player.play();
//		player.block();
	}

	/**
	 * Testdatei-400Hz-2.wav
	 * Frequenz:   400 Hz
	 * Amplitude:  200
	 * Phase:       Pi
	 * Laenge:    5000 ms
	 * @throws LineUnavailableException 
	 * @throws InterruptedException 
	 */
	@Test
	public void testSaveSinus2() throws FileNotFoundException, IOException, LineUnavailableException, InterruptedException {
		
		// sinus schwingung generieren
		File sinus2 = new File("target/test-classes/demo/Testdatei-400Hz-2.wav");
		SinusGeneratorService sinusGenerator = new SinusGeneratorService();
		float pi = (float) Math.PI;
		AudioContainer container = sinusGenerator.generate(sinus2, 400f, 200, pi, 1000);
		
		// speichern...
		AudioFileDAO audioFileDAO = new AudioFileDAO();
		Boolean success = audioFileDAO.save(container, container.getAudioContent().getStartMark(), container.getAudioContent().getEndMark());
		assertTrue(success);

		// .. oder abspielen
//		AudioPlayerService player = new AudioPlayerService();
//		player.setAudioContent(container.getAudioContent());
//		SourceDataLine outputLine = AudioSystem.getSourceDataLine(container.getAudioFormat());
//		player.setOutputLine(outputLine);
//		player.play();
//		player.block();
	}

	/**
	 * Testdatei-3000Hz-1.wav
	 * Frequenz:  3000 Hz
	 * Amplitude:  200
	 * Phase:        0
	 * Laenge:    5000 ms
	 * @throws LineUnavailableException 
	 * @throws InterruptedException 
	 */
	@Test
	public void testSaveSinus3() throws FileNotFoundException, IOException, LineUnavailableException, InterruptedException {

		// sinus schwingung generieren
		File sinus3 = new File("target/test-classes/demo/Testdatei-3000Hz-1.wav");
		SinusGeneratorService sinusGenerator = new SinusGeneratorService();
		AudioContainer container = sinusGenerator.generate(sinus3, 3000f, 200, 0f, 1000);

		// speichern...
		AudioFileDAO audioFileDAO = new AudioFileDAO();
		Boolean success = audioFileDAO.save(container, container.getAudioContent().getStartMark(), container.getAudioContent().getEndMark());
		assertTrue(success);

		// .. oder abspielen
//		AudioPlayerService player = new AudioPlayerService();
//		player.setAudioContent(container.getAudioContent());
//		SourceDataLine outputLine = AudioSystem.getSourceDataLine(container.getAudioFormat());
//		player.setOutputLine(outputLine);
//		player.play();
//		player.block();
	}

	/**
	 * Testdatei-3000Hz-2.wav
	 * Frequenz:  3000 Hz
	 * Amplitude:  200
	 * Phase:        0
	 * Laenge:    5000 ms
	 * @throws LineUnavailableException 
	 * @throws InterruptedException 
	 */
	@Test
	public void testSaveSinus4() throws FileNotFoundException, IOException, LineUnavailableException, InterruptedException {

		// sinus schwingung generieren
		File sinus4 = new File("target/test-classes/demo/Testdatei-3000Hz-2.wav");
		SinusGeneratorService sinusGenerator = new SinusGeneratorService();
		AudioContainer container = sinusGenerator.generate(sinus4, 3000f, 200, 0f, 1000);

		// speichern...
		AudioFileDAO audioFileDAO = new AudioFileDAO();
		Boolean success = audioFileDAO.save(container, container.getAudioContent().getStartMark(), container.getAudioContent().getEndMark());
		assertTrue(success);

		// .. oder abspielen
//		AudioPlayerService player = new AudioPlayerService();
//		player.setAudioContent(container.getAudioContent());
//		SourceDataLine outputLine = AudioSystem.getSourceDataLine(container.getAudioFormat());
//		player.setOutputLine(outputLine);
//		player.play();
//		player.block();
	}

	@Test
	public void testSuperponieren() throws Exception {
		AudioFileDAO audioFileDAO = new AudioFileDAO();
		SuperponierService superponierService = new SuperponierService();
		SinusGeneratorService sinusGenerator = new SinusGeneratorService();
		File sinus3 = new File("target/test-classes/demo/Testdatei-400Hz-1.wav");
		File sinus4 = new File("target/test-classes/demo/Testdatei-400Hz-2.wav");
		File superponiert = new File("target/test-classes/demo/Testdatei-400Hz-2.wav");
		
		// laden der dateien sinus
//		AudioContainer container3 = audioFileDAO.load(sinus3);
//		AudioContainer container4 = audioFileDAO.load(sinus4);

		// ... ueberlagerung der phase ist hoerbar:
		AudioContainer container3 = sinusGenerator.generate(sinus3, 700f, 200, 0f, 1000);
		AudioContainer container4 = sinusGenerator.generate(sinus4, 700f, 200, 0.75f, 1000);

		// ... eleminierend:
		// AudioContainer container3 = sinusGenerator.generate(sinus3, 400f, 200, 0f, 1000);
		// AudioContainer container4 = sinusGenerator.generate(sinus4, 400f, 200, (float) Math.PI, 1000); // eleminierende Phase, har har
		
		// superponieren
		AudioContainer container = superponierService.superponieren(container3, container4, superponiert, 1000);

		// speichern...
//		Boolean success = audioFileDAO.save(superponiertContainer, superponiertContainer.getAudioContent().getStartMark(), superponiertContainer.getAudioContent().getEndMark());
//		assertTrue(success);
		
		// ... oder abspielen
		AudioPlayerService player = new AudioPlayerService();
		player.setAudioContent(container.getAudioContent());
		SourceDataLine outputLine = AudioSystem.getSourceDataLine(container.getAudioFormat());
		player.setOutputLine(outputLine);
		player.play();
		player.block();
	}
}
