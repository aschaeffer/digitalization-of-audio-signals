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

	private final static String PATH_PREFIX = "target/test-classes/demo/";
	private final static String FILENAME_PREFIX = "Testdatei-";
	private final static float FREQ = 1200f;
	private final static Integer AMPLITUDE = 20000;
	private final static Integer MILLIS = 500;
	
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
		System.out.println("TEST SINUS WAVE:\n FREQ:"+FREQ+"Hz\n AMP:"+AMPLITUDE+"\n PHA:"+phase);
		
		File fileWav = new File(PATH_PREFIX+FILENAME_PREFIX+FREQ+"Hz-"+AMPLITUDE+"A-"+phase+"P.wav");
		AudioContainer container = sinusGenerator.generate(fileWav, FREQ, AMPLITUDE, phase, MILLIS);
		Boolean success = audioFileDAO.save(container, container.getAudioContent().getStartMark(), container.getAudioContent().getEndMark());
		assertTrue(success);
		System.out.println(fileWav.getName()+" saved.");

		// real
		File fileReX = new File(PATH_PREFIX+FILENAME_PREFIX+FREQ+"Hz-"+AMPLITUDE+"A-"+phase+"P-rex.csv");
		double[] ReX = dftService.getReX(container);
		dftService.saveCsv(fileReX, ReX);
		System.out.println(fileReX.getName()+" saved.");

		// imaginär
		File fileImX = new File(PATH_PREFIX+FILENAME_PREFIX+FREQ+"Hz-"+AMPLITUDE+"A-"+phase+"P-imx.csv");
		double[] ImX = dftService.getImX(container);
		dftService.saveCsv(fileImX, ImX);
		System.out.println(fileImX.getName()+" saved.");

		// amplitudenspektrum
		File fileAmplitudeSpectrum = new File(PATH_PREFIX+FILENAME_PREFIX+FREQ+"Hz-"+AMPLITUDE+"A-"+phase+"P-amp.csv");
		double[] amplitudeSpectrum = dftService.getAmplitudeSpectrum(ReX, ImX);
		dftService.saveCsv(fileAmplitudeSpectrum, amplitudeSpectrum);
		System.out.println(fileAmplitudeSpectrum.getName()+" saved.");

		// phasenspektrum
		File filePhaseSpectrum = new File(PATH_PREFIX+FILENAME_PREFIX+FREQ+"Hz-"+AMPLITUDE+"A-"+phase+"P-phase.csv");
		double[] phaseSpectrum = dftService.getPhaseSpectrum(ReX, ImX);
		dftService.saveCsv(filePhaseSpectrum, phaseSpectrum);
		System.out.println(filePhaseSpectrum.getName()+" saved.");

	}


	/**
	 * Freq: 1200Hz
	 * Amp: 20000
	 * Phase: pi * 1/2
	 */
	@Test
	public void testAbnahme2() throws FileNotFoundException, IOException, LineUnavailableException, InterruptedException {
		float phase = (float) Math.PI / 2;
		System.out.println("TEST SINUS WAVE:\n FREQ:"+FREQ+"Hz\n AMP:"+AMPLITUDE+"\n PHA:"+phase);
		
		File fileWav = new File(PATH_PREFIX+FILENAME_PREFIX+FREQ+"Hz-"+AMPLITUDE+"A-pi-halbe.wav");
		AudioContainer container = sinusGenerator.generate(fileWav, FREQ, AMPLITUDE, phase, MILLIS);
		Boolean success = audioFileDAO.save(container, container.getAudioContent().getStartMark(), container.getAudioContent().getEndMark());
		assertTrue(success);
		System.out.println(fileWav.getName()+" saved.");

		// real
		File fileReX = new File(PATH_PREFIX+FILENAME_PREFIX+FREQ+"Hz-"+AMPLITUDE+"A-pi-halbe-rex.csv");
		double[] ReX = dftService.getReX(container);
		dftService.saveCsv(fileReX, ReX);
		System.out.println(fileReX.getName()+" saved.");

		// imaginär
		File fileImX = new File(PATH_PREFIX+FILENAME_PREFIX+FREQ+"Hz-"+AMPLITUDE+"A-pi-halbe-imx.csv");
		double[] ImX = dftService.getImX(container);
		dftService.saveCsv(fileImX, ImX);
		System.out.println(fileImX.getName()+" saved.");

		// amplitudenspektrum
		File fileAmplitudeSpectrum = new File(PATH_PREFIX+FILENAME_PREFIX+FREQ+"Hz-"+AMPLITUDE+"A-pi-halbe-amp.csv");
		double[] amplitudeSpectrum = dftService.getAmplitudeSpectrum(ReX, ImX);
		dftService.saveCsv(fileAmplitudeSpectrum, amplitudeSpectrum);
		System.out.println(fileAmplitudeSpectrum.getName()+" saved.");

		// phasenspektrum
		File filePhaseSpectrum = new File(PATH_PREFIX+FILENAME_PREFIX+FREQ+"Hz-"+AMPLITUDE+"A-pi-halbe-phase.csv");
		double[] phaseSpectrum = dftService.getPhaseSpectrum(ReX, ImX);
		dftService.saveCsv(filePhaseSpectrum, phaseSpectrum);
		System.out.println(filePhaseSpectrum.getName()+" saved.");

	}

	/**
	 * Freq: 1200
	 * Amp: 20000
	 * Phase: pi * 1/4
	 */
	@Test
	public void testAbnahme3() throws FileNotFoundException, IOException, LineUnavailableException, InterruptedException {
		float phase = (float) Math.PI / 4;
		System.out.println("TEST SINUS WAVE:\n FREQ:"+FREQ+"Hz\n AMP:"+AMPLITUDE+"\n PHA:"+phase);

		File fileWav = new File(PATH_PREFIX+FILENAME_PREFIX+FREQ+"Hz-"+AMPLITUDE+"A-pi-viertel.wav");
		AudioContainer container = sinusGenerator.generate(fileWav, FREQ, AMPLITUDE, phase, MILLIS);
		Boolean success = audioFileDAO.save(container, container.getAudioContent().getStartMark(), container.getAudioContent().getEndMark());
		assertTrue(success);
		System.out.println(fileWav.getName()+" saved.");

		// real
		File fileReX = new File(PATH_PREFIX+FILENAME_PREFIX+FREQ+"Hz-"+AMPLITUDE+"A-pi-viertel-rex.csv");
		double[] ReX = dftService.getReX(container);
		dftService.saveCsv(fileReX, ReX);
		System.out.println(fileReX.getName()+" saved.");

		// imaginär
		File fileImX = new File(PATH_PREFIX+FILENAME_PREFIX+FREQ+"Hz-"+AMPLITUDE+"A-pi-viertel-imx.csv");
		double[] ImX = dftService.getImX(container);
		dftService.saveCsv(fileImX, ImX);
		System.out.println(fileImX.getName()+" saved.");

		// amplitudenspektrum
		File fileAmplitudeSpectrum = new File(PATH_PREFIX+FILENAME_PREFIX+FREQ+"Hz-"+AMPLITUDE+"A-pi-viertel-amp.csv");
		double[] amplitudeSpectrum = dftService.getAmplitudeSpectrum(ReX, ImX);
		dftService.saveCsv(fileAmplitudeSpectrum, amplitudeSpectrum);
		System.out.println(fileAmplitudeSpectrum.getName()+" saved.");

		// phasenspektrum
		File filePhaseSpectrum = new File(PATH_PREFIX+FILENAME_PREFIX+FREQ+"Hz-"+AMPLITUDE+"A-pi-viertel-phase.csv");
		double[] phaseSpectrum = dftService.getPhaseSpectrum(ReX, ImX);
		dftService.saveCsv(filePhaseSpectrum, phaseSpectrum);
		System.out.println(filePhaseSpectrum.getName()+" saved.");

	}

}
