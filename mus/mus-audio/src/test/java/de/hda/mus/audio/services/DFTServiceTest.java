package de.hda.mus.audio.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.junit.Test;

import de.hda.mus.audio.dao.AudioFileDAO;
import de.hda.mus.audio.domains.AudioContainer;

public class DFTServiceTest {

	@Test
	public void testDFT() throws FileNotFoundException, IOException, LineUnavailableException, InterruptedException, UnsupportedAudioFileException {
		File sprache_original = new File("target/test-classes/demo/sprache.wav");

		DFTService dftService = new DFTService();
		AudioFileDAO audioFileDAO = new AudioFileDAO();
		AudioContainer container = audioFileDAO.load(sprache_original);

		File dft_output = new File("target/test-classes/demo/sprache.dft");
		File dft_output_wav = new File("target/test-classes/demo/sprache.dft.wav");
		double[] dft = dftService.getReX(container);
		dftService.saveSimpleArrayFile(dft_output, dft);
		dftService.saveWav(dft_output_wav, dft, container.getAudioFormat().getSampleRate());

		File dft_output_scaled = new File("target/test-classes/demo/sprache_scaled.dft");
		File dft_output_scaled_wav = new File("target/test-classes/demo/sprache_scaled.dft.wav");
		double[] dft_scaled = dftService.getReX(container, container.getAudioContent().getSamples()[0].length / 2);
		dftService.saveSimpleArrayFile(dft_output_scaled, dft_scaled);
		dftService.saveWav(dft_output_scaled_wav, dft_scaled, container.getAudioFormat().getSampleRate());

	}

	@Test
	public void testIDFT() throws FileNotFoundException, IOException, LineUnavailableException, InterruptedException, UnsupportedAudioFileException {
		File sprache_original = new File("target/test-classes/demo/sprache.wav");

		DFTService dftService = new DFTService();
		AudioFileDAO audioFileDAO = new AudioFileDAO();
		AudioContainer container = audioFileDAO.load(sprache_original);

		File idft_output = new File("target/test-classes/demo/sprache.idft");
		File idft_output_wav = new File("target/test-classes/demo/sprache.idft.wav");
		double[] idft = dftService.getImX(container);
		dftService.saveSimpleArrayFile(idft_output, idft);
		dftService.saveWav(idft_output_wav, idft, container.getAudioFormat().getSampleRate());

		File idft_output_scaled = new File("target/test-classes/demo/sprache_scaled.idft");
		File idft_output_scaled_wav = new File("target/test-classes/demo/sprache_scaled.idft.wav");
		double[] idft_scaled = dftService.getImX(container, container.getAudioContent().getSamples()[0].length / 2);
		dftService.saveSimpleArrayFile(idft_output_scaled, idft_scaled);
		dftService.saveWav(idft_output_scaled_wav, idft_scaled, container.getAudioFormat().getSampleRate());
	}

	@Test
	public void testDFTandInverseDFT() throws FileNotFoundException, IOException, LineUnavailableException, InterruptedException, UnsupportedAudioFileException {
		AudioFileDAO audioFileDAO = new AudioFileDAO();
		File sprache_original = new File("target/test-classes/demo/sprache.wav");
		File dft_output = new File("target/test-classes/demo/sprache_both.dft");
		File dft_output_scaled = new File("target/test-classes/demo/sprache_both_scaled.dft");
		File idft_output = new File("target/test-classes/demo/sprache_both.idft");
		File idft_output_scaled = new File("target/test-classes/demo/sprache_both_scaled.idft");
		AudioContainer container = audioFileDAO.load(sprache_original);
		DFTService dftService = new DFTService();
		double[][] dft_and_idft = dftService.getReXandImX(container);
		dftService.saveSimpleArrayFile(dft_output, dft_and_idft[0]);
		dftService.saveSimpleArrayFile(idft_output, dft_and_idft[1]);
		double[][] dft_and_idft_scaled = dftService.getReXandImX(container, container.getAudioContent().getSamples()[0].length / 2);
		dftService.saveSimpleArrayFile(dft_output_scaled, dft_and_idft_scaled[0]);
		dftService.saveSimpleArrayFile(idft_output_scaled, dft_and_idft_scaled[1]);
	}

	@Test
	public void testAmplitudeSpectrum() throws UnsupportedAudioFileException, IOException {

		File sprache_original = new File("target/test-classes/demo/sprache.wav");

		DFTService dftService = new DFTService();
		AudioFileDAO audioFileDAO = new AudioFileDAO();
		AudioContainer container = audioFileDAO.load(sprache_original);

		File ampSpectrum_output = new File("target/test-classes/demo/sprache.amp");
		File ampSpectrum_output_wav = new File("target/test-classes/demo/sprache.amp.wav");
		double[][] dft_and_idft = dftService.getReXandImX(container);
		double[] amplitudeSpectrum = dftService.getAmplitudeSpectrum(dft_and_idft[0], dft_and_idft[1]);
		dftService.saveSimpleArrayFile(ampSpectrum_output, amplitudeSpectrum);
		dftService.saveWav(ampSpectrum_output_wav, amplitudeSpectrum, container.getAudioFormat().getSampleRate());

		File ampSpectrum_output_scaled = new File("target/test-classes/demo/sprache_scaled.amp");
		File ampSpectrum_output_scaled_wav = new File("target/test-classes/demo/sprache_scaled.amp.wav");
		double[][] dft_and_idft_scaled = dftService.getReXandImX(container, container.getAudioContent().getSamples()[0].length / 2);
		double[] amplitudeSpectrum_scaled = dftService.getAmplitudeSpectrum(dft_and_idft_scaled[0], dft_and_idft_scaled[1]);
		dftService.saveSimpleArrayFile(ampSpectrum_output_scaled, amplitudeSpectrum_scaled);
		dftService.saveWav(ampSpectrum_output_scaled_wav, amplitudeSpectrum_scaled, container.getAudioFormat().getSampleRate());
		
	}

	@Test
	public void testPhaseSpectrum() throws UnsupportedAudioFileException, IOException {

		File sprache_original = new File("target/test-classes/demo/sprache.wav");

		DFTService dftService = new DFTService();
		AudioFileDAO audioFileDAO = new AudioFileDAO();
		AudioContainer container = audioFileDAO.load(sprache_original);

		File phaseSpectrum_output = new File("target/test-classes/demo/sprache.phase");
		File phaseSpectrum_output_wav = new File("target/test-classes/demo/sprache.phase.wav");
		double[][] dft_and_idft = dftService.getReXandImX(container);
		double[] phaseSpectrum = dftService.getPhaseSpectrum(dft_and_idft[0], dft_and_idft[1]);
		dftService.saveSimpleArrayFile(phaseSpectrum_output, phaseSpectrum);
		dftService.saveWav(phaseSpectrum_output_wav, phaseSpectrum, container.getAudioFormat().getSampleRate());

		File phaseSpectrum_output_scaled = new File("target/test-classes/demo/sprache_scaled.phase");
		File phaseSpectrum_output_scaled_wav = new File("target/test-classes/demo/sprache_scaled.phase.wav");
		double[][] dft_and_idft_scaled = dftService.getReXandImX(container);
		double[] phaseSpectrum_scaled = dftService.getPhaseSpectrum(dft_and_idft_scaled[0], dft_and_idft_scaled[1]);
		dftService.saveSimpleArrayFile(phaseSpectrum_output_scaled, phaseSpectrum_scaled);
		dftService.saveWav(phaseSpectrum_output_scaled_wav, phaseSpectrum_scaled, container.getAudioFormat().getSampleRate());
	}

	@Test
	public void testSNR() throws FileNotFoundException, IOException, LineUnavailableException, InterruptedException, UnsupportedAudioFileException {
		File dft_input = new File("target/test-classes/demo/sprache.dft");
		File idft_input = new File("target/test-classes/demo/sprache.idft");
		DFTService dftService = new DFTService();
		double snr = dftService.getSNR(dft_input, idft_input);
		System.out.println("SNR dft/idft: " + snr); 
	}

}
