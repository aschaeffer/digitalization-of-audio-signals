package de.hda.mus.VaR;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class CourseCalculator {

	private String[] companies = { "Daimler", "Deutsche Bank",
			"Fresenius Medical Care", "RWE", "Solarworld" };
	
	private Random random = new Random();

	private HashMap<String, List<Double>> companyValues;
	private int[] depoSize = { 200000, 150000, 250000, 300000, 200000 };

	public CourseCalculator() {
		setCompanyValues(new HashMap<String, List<Double>>());
		for (String c : companies) {
			companyValues.put(c, new ArrayList<Double>());
		}

		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream("src/main/resources/Kurse_SS11.csv")));

			String line;

			while ((line = br.readLine()) != null) {
				if (line.startsWith("0") || line.startsWith("1")
						|| line.startsWith("2") || line.startsWith("3")) {
					String[] values = line.split(";");
					int i = 1;
					for (String c : companies) {
						Double value = Double.valueOf(values[i].replace(",",
								"."));
						companyValues.get(c).add(value);
						i++;
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Parametrisierung: First Date is 2010-04-19
	 * 
	 * @param holdingPeriod
	 * @param companyKey
	 * @return course changes of a company. The first value is from day 04-19
	 *         plus the holdingPeriod. If the holdingPeriod is 1, the first
	 *         course change for day 04-20 based on 04-19 value.
	 */
	public List<Double> getCourseChangesForHoldingPeriod(int holdingPeriod,
			String companyKey) {
		ArrayList<Double> changes = new ArrayList<Double>();
		List<Double> course = companyValues.get(companyKey);
		for (int i = holdingPeriod; i < course.size(); i++) {
			Double old = course.get(i - holdingPeriod);
			Double current = course.get(i);
			changes.add((current - old) / old);
		}
		return changes;
	}

	public Double averageValue(List<Double> courceChanges) {
		Double gesamt = 0.0;
		for (Double c : courceChanges) {
			gesamt += c;
		}
		return gesamt / courceChanges.size();
	}

	private Double covariance(List<Double> companyI, double avgI,
			List<Double> companyJ, double avgJ) {
		double covariance = 0;
		for (int i = 0; i < companyI.size(); i++) {
			covariance += (companyI.get(i) - avgI) * (companyJ.get(i) - avgJ)
					/ (companyI.size() - 1);
		}
		return covariance;
	}

	private Double[][] getCovMatrix(
			HashMap<String, List<Double>> courseChangeMatrix,
			HashMap<String, Double> averageValueMatrix) {

		Double[][] covMatrix = new Double[companyValues.size()][courseChangeMatrix
				.get(companies[0]).size()];

		for (int i = 0; i < companies.length; i++) {
			String companyKeyI = companies[i];
			for (int j = 0; j < companies.length; j++) {
				String companyKeyJ = companies[j];
				List<Double> companyI = courseChangeMatrix.get(companyKeyI);
				double avgI = averageValueMatrix.get(companyKeyI);
				List<Double> companyJ = courseChangeMatrix.get(companyKeyJ);
				double avgJ = averageValueMatrix.get(companyKeyJ);

				covMatrix[i][j] = covariance(companyI, avgI, companyJ, avgJ);
			}
		}
		return covMatrix;
	}

	private Double[][] getCorrelationMatrix(Double[][] covMatrix) {
		Double[][] corrMatrix = new Double[covMatrix.length][covMatrix.length];

		for (int i = 0; i < covMatrix.length; i++) {
			for (int j = 0; j < covMatrix.length; j++) {
				corrMatrix[i][j] = covMatrix[i][j]
						/ (Math.sqrt(covMatrix[i][i]) * Math
								.sqrt(covMatrix[j][j]));
			}
		}
		return corrMatrix;
	}

	private Double[][] getCholeskyMatrix(Double[][] b) {
		Double[][] d = new Double[b.length][b.length];

		for (int i = 0; i < b.length; i++) {
			for (int j = 0; j < b.length; j++) {
				if (j > i) {
					d[i][j] = 0.0;
				} else if (j == i) {
					double sumDik = 0.0;
					for (int k = 0; k < i; k++) {
						sumDik += d[i][k] * d[i][k];
					}
					d[i][j] = Math.sqrt(b[i][j] - sumDik);
				} else {
					double sumDik = 0.0;
					for (int k = 0; k < j; k++) {
						sumDik += d[i][k] * d[j][k];
					}
					d[i][j] = (1 / d[j][j]) * (b[i][j] - sumDik);
				}
			}
		}
		return d;
	}

	public static void main(String[] args) throws IOException {
		CourseCalculator cal = new CourseCalculator();

		HashMap<String, List<Double>> courseChangeMatrix = new HashMap<String, List<Double>>();

		HashMap<String, Double> averageValueMatrix = new HashMap<String, Double>();
		int[] holdingDays = { 1, 10 };

		for (int holdingDay : holdingDays) {
			System.out
					.println("--------------\n  b) course changes and average value - "
							+ holdingDay
							+ " day"
							+ (holdingDay > 1 ? "s" : "")
							+ "\n--------------");
			for (String company : cal.getCompanies()) {
				//
				// a) Bitte parametrisieren Sie die Aktienkurse des letzen
				// Jahres (aus
				// der Datei Kurse_SS11.csv) indem Sie die relativen
				// Kurs�nderungen f�r
				// eine Haltedauer von einem Handelstag und f�r eine Haltedauer
				// von zehn
				// Handelstagen berechnen.
				List<Double> holding = cal.getCourseChangesForHoldingPeriod(
						holdingDay, company);
				courseChangeMatrix.put(company, holding);

				// b) Bitte berechnen Sie f�r die
				// parametrisierten Daten die Mittelwertvektoren f�r beide
				// Halte-dauern.
				Double averageValue = cal.averageValue(holding);
				averageValueMatrix.put(company, averageValue);
				System.out.println(averageValue + " averageValue for "
						+ company);

			}
			System.out
					.println("--------------\n  c) covariance and correlation - "
							+ holdingDay
							+ " day"
							+ (holdingDay > 1 ? "s" : "")
							+ "\n--------------");
			// c) Berechnen Sie bitte die Kovarianzmatrizen und die
			// Korrelationen
			// f�r beide Haltedauern. Bitte interpretieren Sie Ihr Ergebnis.
			System.out.println("--CovMatrix");
			Double[][] covMatrix = cal.getCovMatrix(courseChangeMatrix,
					averageValueMatrix);
			printMatrix(covMatrix);
			System.out.println("--CorrelationMatrix");
			Double[][] corrMatrix = cal.getCorrelationMatrix(covMatrix);
			printMatrix(corrMatrix);

			// 3a) Berechnen Sie die VaR-Werte f�r beide Haltedauern mit Hilfe
			// von historischen Simulatio-nen.
			System.out.println("--HistoricalSimulation");
			double quantil = 0.01;
			double var = cal.historicalSimulation(courseChangeMatrix, quantil);
			System.out.println("HistoricalSim var("+(1-quantil)*100+"%)=" + var);

			// 3b) Berechnen Sie die VaR-Werte f�r beide Haltedauern mit
			// Hilfe von Monte Carlo Simulationen. Bitte erzeugen Sie f�r die
			// Simulationen jeweils 10.000 Zufallsvektoren. Bitte vergleichen
			// Sie die Kovarianzmatrizen und die Mittelwertvektoren Ihrer
			// Simulationen mit den in Aufgabe 2 f�r die Aktien berechneten
			// statistischen Kenngr��en.
			System.out.println("--CholeskyMatrix");
			Double[][] choleskyMatrix = cal.getCholeskyMatrix(covMatrix);
			printMatrix(choleskyMatrix);
			System.out.println("--Monte Carlo Simulation");
			
			var = cal.monteCarloSimulation(choleskyMatrix,averageValueMatrix, quantil);
			System.out.println("MonteCarloSim var("+(1-quantil)*100+"%)=" + var);

		}
	}
	
	private Double historicalSimulation(HashMap<String, List<Double>> courseChangeMatrix, Double quantil) {
		Double var = 0.0;
		int simDuration = courseChangeMatrix.get(companies[0]).size();
		Double[] historicalSimulationValues = new Double[simDuration];
		for (int z = 0; z < simDuration; z++) {
			double value = 0.0;
			for (int i = 0; i < depoSize.length; i++) {
				List<Double> comValues = companyValues.get(companies[i]);
				Double stockValue = comValues.get(comValues.size() - 1);
				value += stockValue * depoSize[i] * courseChangeMatrix.get(companies[i]).get(z);
				
			}
			historicalSimulationValues[z] = value;
			
		}
		
		Arrays.sort(historicalSimulationValues);
		int index = (int) (simDuration * quantil)-1;
		var = historicalSimulationValues[index];
		
		System.out.println(index+"/"+simDuration);
		return var;
	}

	private Double monteCarloSimulation(Double[][] choleskyMatrix,
			HashMap<String, Double> averageValueMatrix, Double quantil) {
		Double var = 0.0;
		int simDuration = 10000;
		Double[] monteCarloValues = new Double[simDuration];
		for (int z = 0; z < simDuration; z++) {
			double value = 0.0;
			Double[] randomValues = randomValues(choleskyMatrix,
					averageValueMatrix);
			for (int i = 0; i < depoSize.length; i++) {
				List<Double> comValues = companyValues.get(companies[i]);
				Double stockValue = comValues.get(comValues.size() - 1);
				value += stockValue * depoSize[i] * randomValues[i];
				// System.out.println(value+" += "+stockValue+"*"+depoSize[i]+"*"+randomValues[i]+"");
			}
			monteCarloValues[z] = value;
		}

		Arrays.sort(monteCarloValues);
		int index = (int) (simDuration * quantil)-1;
		System.out.println("index= " + index);
		var = monteCarloValues[index];

		return var;
	}

	private Double[] randomValues(Double[][] choleskyMatrix,
			HashMap<String, Double> averageValueMatrix) {
		Double[] y = new Double[choleskyMatrix.length];
		Double[] randomVector = new Double[choleskyMatrix.length];

		for (int i = 0; i < choleskyMatrix.length; i++) {
			randomVector[i] = random.nextGaussian();
		}

		for (int i = 0; i < choleskyMatrix.length; i++) {
			double avg = averageValueMatrix.get(companies[i]);

			y[i] = 0.0;
			for (int j = 0; j < choleskyMatrix.length; j++) {
				y[i] += choleskyMatrix[i][j] * randomVector[i];
			}
			y[i] += avg;
			// System.out.println("y["+companies[i]+"]=" + y[i]);
		}
		return y;
	}

	public static void printMatrix(Double[][] matrix) {
		DecimalFormat format = new DecimalFormat("0.000000E00");
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix.length; j++) {
				Double v = matrix[i][j];
				System.out.print((v > 0 ? " " : "") + format.format(v) + " ||");
			}
			System.out.println();
		}
	}

	public void setCompanyValues(HashMap<String, List<Double>> companyValues) {
		this.companyValues = companyValues;
	}

	public HashMap<String, List<Double>> getCompanyValues() {
		return companyValues;
	}

	public void setCompanies(String[] companies) {
		this.companies = companies;
	}

	public String[] getCompanies() {
		return companies;
	}

}
