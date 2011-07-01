package de.hda.mus.audio.services;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;

import de.hda.mus.audio.constants.DefaultAudioValues;
import de.hda.mus.audio.dao.AudioFileDAO;
import de.hda.mus.audio.domains.AudioContainer;
import de.hda.mus.audio.domains.AudioContent;
import de.hda.mus.audio.domains.Peaks;

/**
 * Bitte implementieren Sie DFT und inverse DFT. Die zeitliche Auflösung der
 * Fourier-Transformation soll frei wählbar sein. Die zu analysierende Datei
 * soll ebenso frei wählbar sein.
 * 
 * Legen Sie die Ergebnisse der DFT bitte in einer Datei ab. Überprüfen Sie
 * insbesondere den SNR zwischen den Signalen, die Sie einer Fourier-Transformation
 * unterziehen und den Signalen, die Sie mit Hilfe der inversen DFT berechnen.
 * 
 * --------------------------------------------------------------------------------
 * 
 * Mit Hilfe der Fourier-Transformation kann das Frequenzspektrum eines
 * zeitkontinuierlichen Signals ermittelt werden. Somit kann mit Hilfe dieser
 * Technik eine Aussage über die in einem Signal (zeitkontinuierlichen) vorkommenden
 * Qrequenzen gemacht werden. Sollen Signale digital verarbeitet werden, so benutzt
 * man meist zeitdiskrete Signale. Für diese Klasse von Signalen gibt es die diskrete
 * Fourier-Transformation (DFT). Der Unterschied beider Transformationen besteht darin,
 * dass die DFT eher eine Folge als eine Funktion einer kontinuierlichen Variablen
 * ist. Die DFT entspricht den Abtastwerten der Fourier-Transformatioin des Signals
 * bei äquidistanten Frequenzen.
 * 
 * 
 * Mit Hilfe der Abtastung lässt sich ein kontinuierliches Signal in ein diskretes
 * Signal überführen, wobei das Abtasttheorem möglichst nicht verletzt werden sollte.
 * In der Formael wird x(t) durch seine Abtastwerte x(nT) ersetzt, sowie das Differential
 * durch das Abtastintervall T und das Integral wird durch die Summe approximiert.
 * 
 * Xs(f) = Summe(n,-unendlich,unendlich) von x(nT) * e^(-j * 2 * pi * nT) * T
 * 
 * Aus der unendlichen Anzahl von Abtastwerden schneiden wir eine endliche Anzahl N heraus.
 * Der Faktor T wird weggelassen und wir erhalten das abgetastete und gefensterte Signal.
 * 
 * 
 * Betrags/Phasenspektrum
 *  
 *  Betragsspektrum: Darstellung der resultierenden Amplitude in Abhängigkeit von der Frequenz.
 *  
 *  Phasenspektrum: Darstellung der resultierenden Phase in Abhängigkeit von der Frequenz.
 *  
 *  Die Darstellung mit Betrags- und Phasenspektrum wird als Polardarstellung oder spektrale
 *  Darstellung bezeichnet. Die Darstellung mit den Basisfunktionen cos() und sin() wird als
 *  komplexe Darstellung bezeichnet.
 *  
 * @author aschaeffer
 *
 */
public class DFTService {
	
	private final static int REX = 0;
	private final static int IMX = 1;
	private final static int FAC = 2;

	SNRService snrService = new SNRService();

	public double[] processFourierTransformation(AudioContainer container, int timeScale) {
		double[] monoSamples = container.getAudioContent().getSamples()[0];
        if (timeScale > monoSamples.length) {
            timeScale = monoSamples.length;
        }
        double[] ReX = getReXPart(monoSamples, timeScale);
        double[] ImX = getImXPart(monoSamples, timeScale);
        return this.scale( ReX, ImX, timeScale);
	}

	public double[] getReX(AudioContainer container) throws IOException {
		return getReXPart(container.getAudioContent().getSamples()[0], container.getAudioContent().getSamples()[0].length);
	}

	public double[] getReX(AudioContainer container, Integer timescale) throws IOException {
		return getReXPart(container.getAudioContent().getSamples()[0], timescale);
	}

	public double[] getImX(AudioContainer container) throws IOException {
		return getImXPart(container.getAudioContent().getSamples()[0], container.getAudioContent().getSamples()[0].length);
	}

	public double[] getImX(AudioContainer container, Integer timescale) throws IOException {
		return getImXPart(container.getAudioContent().getSamples()[0], timescale);
	}

	public double[][] getReXandImX(AudioContainer container) throws IOException {
		return getBothParts(container.getAudioContent().getSamples()[0], container.getAudioContent().getSamples()[0].length);
	}

	public double[][] getReXandImX(AudioContainer container, Integer timescale) throws IOException {
		return getBothParts(container.getAudioContent().getSamples()[0], timescale);
	}

	public double getSNR(File dftFile, File idftFile) throws IOException {
		FileInputStream dftIs = new FileInputStream(dftFile);
		FileInputStream idftIs = new FileInputStream(idftFile);
		DataInputStream dftDis = new DataInputStream(dftIs);
		DataInputStream idftDis = new DataInputStream(idftIs);
		int lengthDft = dftDis.readInt();
		int lengthIdft = idftDis.readInt();
		double[] samplesDft = new double[lengthDft];
		double[] samplesIdft = new double[lengthIdft];
		for (int i=0; i<lengthDft; i++) {
			samplesDft[i] = dftDis.readDouble();
		}
		for (int i=0; i<lengthIdft; i++) {
			samplesIdft[i] = idftDis.readDouble();
		}
		return snrService.getSNR(samplesDft, samplesIdft);
	}

	public double[] getAmplitudeSpectrum(double[] ReX, double[] ImX) {
		double[] amplitudeSpectrum = new double[ReX.length];

		for (int i = 0; i < amplitudeSpectrum.length; i++) {
			amplitudeSpectrum[i] = Math.sqrt(Math.pow(ReX[i], 2) + Math.pow(ImX[i], 2));
		}

		return amplitudeSpectrum;
	}

	public double[] getPhaseSpectrum(double[] ReX, double[] ImX) {
		double[] phaseSpectrum = new double[ReX.length];

		for (int i = 0; i < phaseSpectrum.length; i++) {
			phaseSpectrum[i] = Math.atan(ReX[i] / ImX[i]);
		}

		return phaseSpectrum;
	}
	
	public void saveSimpleArrayFile(File dftFile, double[] values) throws IOException {
		FileOutputStream fos = new FileOutputStream(dftFile);
		DataOutputStream dos = new DataOutputStream(fos);
		dos.writeInt(values.length);
		for (int i=0; i<values.length; i++) {
			dos.writeDouble(values[i]);
		}
		dos.close();
	}

	public void saveWav(File dftFile, double[] values, float sampleRate) {
		AudioFileDAO audioFileDAO = new AudioFileDAO();
		AudioFileFormat iaff = new AudioFileFormat(DefaultAudioValues.DEFAULT_AUDIOFILEFORMAT, DefaultAudioValues.DEFAULT_AUDIOFORMAT, values.length);
		AudioFileFormat daff = new AudioFileFormat(DefaultAudioValues.DEFAULT_AUDIOFILEFORMAT, DefaultAudioValues.DEFAULT_AUDIOFORMAT, values.length);
		AudioFormat internalFormat = new AudioFormat(16000f, DefaultAudioValues.DEFAULT_SAMPLESIZE, DefaultAudioValues.DEFAULT_CHANNELS, true, false);
		AudioFileFormat internalAff = new AudioFileFormat(AudioFileFormat.Type.WAVE, internalFormat, values.length);
		double[][] samples = new double[1][values.length];
		samples[0] = values;
		AudioContent dftContent = new AudioContent(daff, iaff, samples);
		Peaks peaks = PeakAnalyzerService.createPeaks(internalAff, samples);
		AudioContainer dftContainer = new AudioContainer(dftFile, dftContent, peaks);
		audioFileDAO.save(dftContainer, dftContent.getStartMark(), dftContent.getEndMark());
	}
	
	public void saveCsv(File dftFile, double[] values) throws IOException {
		FileOutputStream fos = new FileOutputStream(dftFile);
		DataOutputStream dos = new DataOutputStream(fos);
		for (int i=0; i<values.length; i++) {
			dos.writeChars(""+i+";"+values[i]);
		}
		fos.close();	
	}

	private double[] getReXPart(double[] values, int timescale) {
		int numValues = (timescale/FAC)+1;
    	double[] ReX = new double[numValues];
        for(int i = 0; i < numValues; i++) {
            ReX[i] = 0;
            for( int n=0; n < timescale; n++ ) {
                ReX[i] += values[n] * Math.cos(2*Math.PI*i*n/timescale);
            }
        }
        return ReX;
    }

    private double[] getImXPart(double[] values, int timescale) {
    	int numValues = (timescale/FAC)+1;
    	double[] ImX = new double[numValues];
        for(int i = 0; i < numValues; i++) {
        	ImX[i] = 0;
            for( int n=0; n < timescale; n++ ) {
            	ImX[i] -= values[n] * (Math.sin(2*Math.PI*i*n/timescale));
            }
        }
        return ImX;
    }

    private double[][] getBothParts(double[] values, int timescale) {
    	int numValues = (timescale/FAC)+1;
    	double[][] output = new double[2][numValues];
        for(int i = 0; i < numValues; i++) {
        	output[REX][i] = 0;
        	output[IMX][i] = 0;
            for( int n=0; n < timescale; n++ ) {
            	output[REX][i] += values[n] * Math.cos(2*Math.PI*i*n/timescale);
            	output[IMX][i] -= values[n] * (Math.sin(2*Math.PI*i*n/timescale));
            }
        }
        return output;
    }

    private double[] scale(double[] ReX, double[] ImX, int timescale) {
        double[] values = new double[timescale];
        for (int i = 0; i < timescale; i++) {
        	values[i] = 0;
        }
        for (int i = 0; i < ReX.length; i++) {
        	if (i != 0) {
        		if ( i == ReX.length-1) {
        			ReX[i] = ReX[i]/(values.length);
        		}
        		ReX[i] = ReX[i]/(values.length/2);
        	} else {
        		ReX[i] = ReX[i]/values.length;
        	}
        	ImX[i] = -(ImX[i]/(values.length/2));
        }
        return values;
    }



    
    
    /* OLD CODE */
    
    
    public double[] getReXPart_old(AudioContainer container, Integer timescale) throws IOException {
		double[] monoSamples = container.getAudioContent().getSamples()[0];
        if (timescale > monoSamples.length) {
        	timescale = monoSamples.length;
        }
		double[] ReX = new double[monoSamples.length];
		int N = monoSamples.length;
		for(int i=0; i<=N/2; i++) { // Frequenz
			ReX[i] = 0;
			for(int n=0; n<N; n++) { // Abtastwerte
				ReX[i] += monoSamples[n] * Math.cos(2 * Math.PI * n * i / N); // Korrelation
			}
		}
		return ReX;
	}

	public double[] getImXPart_old(AudioContainer container, Integer timescale) throws IOException {
		double[] monoSamples = container.getAudioContent().getSamples()[0];
        if (timescale > monoSamples.length) {
        	timescale = monoSamples.length;
        }
		double[] ImX = new double[monoSamples.length];
		int N = monoSamples.length;
		for(int i=0; i<=N/2; i++) { // Frequenz
			ImX[i] = 0;
			for(int n=0; n<N; n++) { // Abtastwerte
				ImX[i] += monoSamples[n] * Math.sin(2 * Math.PI * n * i / N); // Korrelation
			}
			ImX[i] = 0-ImX[i];
		}
		return ImX;
	}

	public double[][] getReX_and_ImX_old(AudioContainer container, Integer timescale) throws IOException {
		double[] monoSamples = container.getAudioContent().getSamples()[0];
		double[][] output = new double[2][monoSamples.length];
        if (timescale > monoSamples.length) {
        	timescale = monoSamples.length;
        }
		int N = monoSamples.length;
		for(int i=0; i<=N/2; i++) { // Frequenz
			output[REX][i] = 0;
			output[IMX][i] = 0;
			for(int n=0; n<N; n++) { // Abtastwerte
				output[REX][i] += monoSamples[n] * Math.cos(2 * Math.PI * n * i / N); // Korrelation
				output[IMX][i] += monoSamples[n] * Math.sin(2 * Math.PI * n * i / N); // Korrelation
			}
			output[IMX][i] = 0-output[IMX][i];
		}
		return output;
	}


}
