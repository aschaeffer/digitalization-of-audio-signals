package de.hda.mus.audio.services;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.UnsupportedAudioFileException;

import org.junit.Before;
import org.junit.Test;

import de.hda.mus.audio.dao.AudioFileDAO;
import de.hda.mus.audio.domains.AudioContainer;

public class StatisticServiceTest {

	StatisticService statisticService;
	AudioFileDAO audioFileDAO;
	
	File spracheOriginal;
	File sprache1bitQuantized;
	File sprache2bitQuantized;
	File sprache8bitQuantized;
	File sprache16bitQuantized;

	AudioContainer containerOriginal;
	AudioContainer container1bitQuantized;
	AudioContainer container2bitQuantized;
	AudioContainer container8bitQuantized;
	AudioContainer container16bitQuantized;

	@Before
	public void init() throws UnsupportedAudioFileException, IOException {
		statisticService = new StatisticService();
		audioFileDAO = new AudioFileDAO();
		spracheOriginal = new File("target/test-classes/demo/sprache.wav");
		sprache1bitQuantized = new File("target/test-classes/demo/sprache-quantized-1bit.wav");
		sprache2bitQuantized = new File("target/test-classes/demo/sprache-quantized-2bit.wav");
		sprache8bitQuantized = new File("target/test-classes/demo/sprache-quantized-8bit.wav");
		sprache16bitQuantized = new File("target/test-classes/demo/sprache-quantized-16bit.wav");
		containerOriginal = audioFileDAO.load(spracheOriginal);
		container1bitQuantized = audioFileDAO.load(sprache1bitQuantized);
		container2bitQuantized = audioFileDAO.load(sprache2bitQuantized);
		container8bitQuantized = audioFileDAO.load(sprache8bitQuantized);
		container16bitQuantized = audioFileDAO.load(sprache16bitQuantized);
	}

	@Test
	public void getMeanTest() throws UnsupportedAudioFileException, IOException {
		System.out.println("Mean original: " + statisticService.getMean(containerOriginal));
		System.out.println("Mean 1 bit quantized: " + statisticService.getMean(container1bitQuantized));
		System.out.println("Mean 2 bit quantized: " + statisticService.getMean(container2bitQuantized));
		System.out.println("Mean 8 bit quantized: " + statisticService.getMean(container8bitQuantized));
		System.out.println("Mean 16 bit quantized: " + statisticService.getMean(container16bitQuantized));
	}

	@Test
	public void getVarianceTest() throws UnsupportedAudioFileException, IOException {
		System.out.println("Variance original: " + statisticService.getVariance(containerOriginal));
		System.out.println("Variance 1 bit quantized: " + statisticService.getVariance(container1bitQuantized));
		System.out.println("Variance 2 bit quantized: " + statisticService.getVariance(container2bitQuantized));
		System.out.println("Variance 8 bit quantized: " + statisticService.getVariance(container8bitQuantized));
		System.out.println("Variance 16 bit quantized: " + statisticService.getVariance(container16bitQuantized));
	}

	@Test
	public void getStandardDeviationTest() throws UnsupportedAudioFileException, IOException {
		System.out.println("Standard deviation original: " + statisticService.getStandardDeviation(containerOriginal));
		System.out.println("Standard deviation 1 bit quantized: " + statisticService.getStandardDeviation(container1bitQuantized));
		System.out.println("Standard deviation 2 bit quantized: " + statisticService.getStandardDeviation(container2bitQuantized));
		System.out.println("Standard deviation 8 bit quantized: " + statisticService.getStandardDeviation(container8bitQuantized));
		System.out.println("Standard deviation 16 bit quantized: " + statisticService.getStandardDeviation(container16bitQuantized));
	}

}
