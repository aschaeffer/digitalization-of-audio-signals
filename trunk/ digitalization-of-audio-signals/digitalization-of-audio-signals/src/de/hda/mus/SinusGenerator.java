package de.hda.mus;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

/**
 * Bitte implementieren Sie einen Signalgenerator, der eine Sinus-Schwingung mit wählbarer
 * Frequenz, Amplitude und Phase mit einer Abtastrate von 16 kHz erzeugt. Legen Sie die Dateien
 * bitte in einem von Ihnen abspielbaren Audio-Format ab.
 * 
 * Erzeugen Sie zwei Dateien, die jeweils ein Sinussignal mit einer Frequenz von 400 Hz
 * enthalten. Die Dateien sollen aber unterschiedliche Amplituden oder Phasen haben. Im
 * Folgenden wird auf diese Dateien mit der Bezeichnung „Testdatei 400 Hz 1“ bzw.
 * „Testdatei 400 Hz 2“ Bezug genommen. Superponieren Sie die beiden Dateien zu einer Datei
 * mit der Bezeichnung „Testdatei 400 Hz superponiert“.
 * 
 * Erzeugen Sie zwei Dateien, die jeweils ein Sinussignal mit einer Frequenz von 3 kHz
 * enthalten. Die Dateien sollen aber unterschiedliche Amplituden oder Phasen haben. Im
 * Folgenden wird auf diese Dateien mit der Bezeichnung „Testdatei 3 kHz 1“ bzw.
 * „Testdatei 3 kHz 2“ Bezug genommen. Superponieren Sie die beiden Dateien zu einer Datei
 * mit der Bezeichnung „Testdatei 3 kHz superponiert“.
 * 
 * @author aschaeffer
 *
 */
public class SinusGenerator {
	AudioFormat audioFormat;
	AudioInputStream audioInputStream;
	SourceDataLine sourceDataLine;

	float sampleRate = 44100f;
	//Allowable 8000,11025,16000,22050,44100
	int sampleSizeInBits = 16;
	//Allowable 8,16
	int channels = 1;
	//Allowable 1,2
	boolean signed = true;
	//Allowable true,false
	boolean bigEndian = true;
	//Allowable true,false
	
	public void play(Float herz, Integer amplitude, Float phase, Integer milliseconds) throws LineUnavailableException {
		Float frequency = sampleRate;
		Boolean addHarmonic = true;
		byte[] buf;
		AudioFormat af;
		if (addHarmonic) {
			buf = new byte[2];
			af = new AudioFormat(frequency, 8, 2, true, false);
		} else {
			buf = new byte[1];
			af = new AudioFormat(frequency, 8, 1, true, false);
		}
		SourceDataLine sdl = AudioSystem.getSourceDataLine(af);
		sdl.open(af);
		sdl.start();
		for (int i = 0; i < milliseconds * frequency / 1000; i++) {
			double angle = i / (frequency / herz) * 2.0 * Math.PI;
			buf[0] = (byte) (Math.sin(angle) * amplitude);

			if (addHarmonic) {
				double angle2 = ((i) / (frequency / herz) * 2.0 * Math.PI) + phase;
				buf[1] = (byte) (Math.sin(2 * angle2) * amplitude * 0.6);
				sdl.write(buf, 0, 2);
			} else {
				sdl.write(buf, 0, 1);
			}
		}
		sdl.drain();
		sdl.stop();
		sdl.close();
	}

	public float getSampleRate() {
		return sampleRate;
	}

	public void setSampleRate(float sampleRate) {
		this.sampleRate = sampleRate;
	}

}
