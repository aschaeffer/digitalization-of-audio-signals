package de.hda.mus.audio.services;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;

import org.junit.Test;

import de.hda.mus.audio.dao.AudioFileDAO;
import de.hda.mus.audio.domains.AudioContainer;

public class AbnahmeTest {

	private final static Integer AMPLITUDE = 20000;
	private final static Integer MILLIS = 2000;
	
	AudioFileDAO audioFileDAO = new AudioFileDAO();
	SinusGeneratorService sinusGenerator = new SinusGeneratorService();
	DFTService dftService = new DFTService();

	/**
	 * Freq: 1200Hz
	 * Amp: 20000
	 * Phase: 0
	 */
	@Test
	public void testAbnahme1() throws FileNotFoundException, IOException, LineUnavailableException, InterruptedException {
		float phase = 0f;
		File file = new File("target/test-classes/demo/Testdatei-1200Hz-20000A-0P.wav");
		AudioContainer container = sinusGenerator.generate(file, 1200f, AMPLITUDE, phase, MILLIS);
		Boolean success = audioFileDAO.save(container, container.getAudioContent().getStartMark(), container.getAudioContent().getEndMark());
		assertTrue(success);

		// real
		File fileReX = new File("target/test-classes/demo/Testdatei-1200Hz-20000A-0P-rex.csv");
		double[] ReX = dftService.getReX(container);
		dftService.saveCsv(fileReX, ReX);

		// imaginär
		File fileImX = new File("target/test-classes/demo/Testdatei-1200Hz-20000A-0P-imx.csv");
		double[] ImX = dftService.getImX(container);
		dftService.saveCsv(fileImX, ImX);

		// amplitudenspektrum
		File fileAmplitudeSpectrum = new File("target/test-classes/demo/Testdatei-1200Hz-20000A-0P-amp.csv");
		double[] amplitudeSpectrum = dftService.getAmplitudeSpectrum(ReX, ImX);
		dftService.saveCsv(fileAmplitudeSpectrum, amplitudeSpectrum);

		// phasenspektrum
		File filePhaseSpectrum = new File("target/test-classes/demo/Testdatei-1200Hz-20000A-0P-phase.csv");
		double[] phaseSpectrum = dftService.getPhaseSpectrum(ReX, ImX);
		dftService.saveCsv(filePhaseSpectrum, phaseSpectrum);

	}


	/**
	 * Freq: 1200Hz
	 * Amp: 20000
	 * Phase: pi * 1/2
	 */
	@Test
	public void testAbnahme2() throws FileNotFoundException, IOException, LineUnavailableException, InterruptedException {
		float phase = (float) Math.PI / 2;
		File file = new File("target/test-classes/demo/Testdatei-1200Hz-20000A-pi-2.wav");
		AudioContainer container = sinusGenerator.generate(file, 1200f, AMPLITUDE, phase, MILLIS);
		Boolean success = audioFileDAO.save(container, container.getAudioContent().getStartMark(), container.getAudioContent().getEndMark());
		assertTrue(success);

		// real
		File fileReX = new File("target/test-classes/demo/Testdatei-1200Hz-20000A-pi-2-rex.csv");
		double[] ReX = dftService.getReX(container);
		dftService.saveCsv(fileReX, ReX);

		// imaginär
		File fileImX = new File("target/test-classes/demo/Testdatei-1200Hz-20000A-pi-2-imx.csv");
		double[] ImX = dftService.getImX(container);
		dftService.saveCsv(fileImX, ImX);

		// amplitudenspektrum
		File fileAmplitudeSpectrum = new File("target/test-classes/demo/Testdatei-1200Hz-20000A-pi-2-amp.csv");
		double[] amplitudeSpectrum = dftService.getAmplitudeSpectrum(ReX, ImX);
		dftService.saveCsv(fileAmplitudeSpectrum, amplitudeSpectrum);

		// phasenspektrum
		File filePhaseSpectrum = new File("target/test-classes/demo/Testdatei-1200Hz-20000A-pi-2-phase.csv");
		double[] phaseSpectrum = dftService.getPhaseSpectrum(ReX, ImX);
		dftService.saveCsv(filePhaseSpectrum, phaseSpectrum);

	}

	/**
	 * Freq: 1200Hz
	 * Amp: 20000
	 * Phase: pi * 1/4
	 */
	@Test
	public void testAbnahme3() throws FileNotFoundException, IOException, LineUnavailableException, InterruptedException {
		float phase = (float) Math.PI / 4;
		File fileWav = new File("target/test-classes/demo/Testdatei-1200Hz-20000A-pi-4.wav");
		AudioContainer container = sinusGenerator.generate(fileWav, 1200f, AMPLITUDE, phase, MILLIS);
		Boolean success = audioFileDAO.save(container, container.getAudioContent().getStartMark(), container.getAudioContent().getEndMark());
		assertTrue(success);

		// real
		File fileReX = new File("target/test-classes/demo/Testdatei-1200Hz-20000A-pi-4-rex.csv");
		double[] ReX = dftService.getReX(container);
		dftService.saveCsv(fileReX, ReX);

		// imaginär
		File fileImX = new File("target/test-classes/demo/Testdatei-1200Hz-20000A-pi-4-imx.csv");
		double[] ImX = dftService.getImX(container);
		dftService.saveCsv(fileImX, ImX);

		// amplitudenspektrum
		File fileAmplitudeSpectrum = new File("target/test-classes/demo/Testdatei-1200Hz-20000A-pi-4-amp.csv");
		double[] amplitudeSpectrum = dftService.getAmplitudeSpectrum(ReX, ImX);
		dftService.saveCsv(fileAmplitudeSpectrum, amplitudeSpectrum);

		// phasenspektrum
		File filePhaseSpectrum = new File("target/test-classes/demo/Testdatei-1200Hz-20000A-pi-4-phase.csv");
		double[] phaseSpectrum = dftService.getPhaseSpectrum(ReX, ImX);
		dftService.saveCsv(filePhaseSpectrum, phaseSpectrum);

	}

}
