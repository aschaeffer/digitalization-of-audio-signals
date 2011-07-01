package de.hda.mus.audio.services;

import de.hda.mus.audio.domains.AudioContainer;

/**
 * Fehler durch die Quantisierung der Amplitude kann durch
 * den Signal-Rausch-Abstand (SNR) bewertet werden.
 * 
 *        Nutzsignalleisung
 * SNR =  --------------------
 *        Rauschsignalleistung
 * 
 * (Wikipedia): Da aber die Signalleistung bei vielen technischen Anwendungen um
 * mehrere Größenordnungen größer ist als die Rauschleistung, wird das
 * Signal-Rausch-Verhältnis oft im logarithmischen Maßstab dargestellt. Man benutzt
 * dazu die Pseudoeinheit Dezibel (dB):
 * 
 *                (  Nutzsignalleisung   )
 * SNR =  10 * lg ( -------------------- )
 *                ( Rauschsignalleistung )
 * 
 * @author aschaeffer
 *
 */
public class SNRService {

	/**
	 * Wir beachten hier erstmal nur Mono-Signale (Spur 0).
	 * 
	 * @param container1
	 * @param container2
	 * @return
	 */
	public double getSNR(AudioContainer container1, AudioContainer container2) {
		double[] samples1 = container1.getAudioContent().getSamples()[0];
		double[] samples2 = container2.getAudioContent().getSamples()[0];
		double nutzsignalleistung = sumPow2OfArray(samples1);
		double rauschsignalleistung = sumPow2DiffOfArray(samples1, samples2);
		System.out.println(" + div: " + nutzsignalleistung / rauschsignalleistung + "  log: " + Math.log(nutzsignalleistung / rauschsignalleistung) + "  10*log: " + 10d * Math.log(nutzsignalleistung / rauschsignalleistung));
		return 10d * Math.log(nutzsignalleistung / rauschsignalleistung);
//		if (rauschsignalleistung == 0.0) {
//			return 0;
//		} else {
//			return 10d * Math.log(nutzsignalleistung / rauschsignalleistung);
//		}
	}

	/**
	 * Wir beachten hier erstmal nur Mono-Signale (Spur 0).
	 * 
	 * @param container1
	 * @param container2
	 * @return
	 */
	public double getSNR(double[] samples1, double[] samples2) {
		double nutzsignalleistung = sumPow2OfArray(samples1);
		double rauschsignalleistung = sumPow2DiffOfArray(samples1, samples2);
		System.out.println(" + div: " + nutzsignalleistung / rauschsignalleistung + "  log: " + Math.log(nutzsignalleistung / rauschsignalleistung) + "  10*log: " + 10d * Math.log(nutzsignalleistung / rauschsignalleistung));
		return 10d * Math.log(nutzsignalleistung / rauschsignalleistung);
	}

	private double sumPow2OfArray(double[] a) {
		double s = 0;
		for (int x=0; x<a.length; x++) {
			s+=Math.pow(a[x], 2);
		}
		System.out.println(" + sumPow2OfArray: " + s);
		return s;
	}
	
	private double sumPow2DiffOfArray(double[] a, double[] b) {
		double s = 0;
		double l = a.length;
		if (b.length < a.length) l = b.length;
		for (int x=0; x<l; x++) {
			// System.out.println(" (b) " + x + " " + s + " --- " + Math.abs(b[x]) + " " + Math.abs(a[x]));
			s+=Math.pow(b[x]-a[x], 2);
		}
		System.out.println(" + sumPow2DiffOfArray: " + s);
		return s;
	}

}


























