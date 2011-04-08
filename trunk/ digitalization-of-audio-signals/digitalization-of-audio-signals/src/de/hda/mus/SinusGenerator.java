package de.hda.mus;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
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
