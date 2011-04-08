package de.hda.mus;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.SourceDataLine;

public class SinusGenerator {
	AudioFormat audioFormat;
	AudioInputStream audioInputStream;
	SourceDataLine sourceDataLine;

	float sampleRate = 16000.0F;
	//Allowable 8000,11025,16000,22050,44100
	int sampleSizeInBits = 16;
	//Allowable 8,16
	int channels = 1;
	//Allowable 1,2
	boolean signed = true;
	//Allowable true,false
	boolean bigEndian = true;
	//Allowable true,false

}
