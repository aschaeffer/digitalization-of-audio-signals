package de.hda.mus.neuronalnet.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

import de.hda.mus.neuronalnet.MLPnachVorlesung;
import de.hda.mus.neuronalnet.transferfunction.SigmoidFunction;

public class MLPnachVorlesunglGUI extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	private MLPnachVorlesung xorMLP;
	int[] size = { 2, 2, 1 };
	double[][] pattern = { { 0, 0, 0 }, { 0, 1, 1 }, { 1, 0, 1 }, { 1, 1, 0 }};
	double[] inputs = { 0, 0 };
	double target = 0;
	double error = 0.0;

	ArrayList<JButton> inputNeuronButtons = new ArrayList<JButton>();
	ArrayList<JButton> hiddenNeuronButtons = new ArrayList<JButton>();
	ArrayList<JButton> outputNeuronButtons = new ArrayList<JButton>();

	JPanel main = new JPanel();
	ArrayList<JPanel> layers = new ArrayList<JPanel>();
	HashMap<JButton, String> controlButtons = new HashMap<JButton, String>();
	HashMap<JTextField, String> controlFields = new HashMap<JTextField, String>();
	Boolean learning = false;
	Timer timer = new Timer(50, this);

	public MLPnachVorlesunglGUI() {
		int maxSize = 1; // bias +1
		for (int i = 0; i < size.length; i++) {
			maxSize += size[i];
		}
		Double[][] weights = new Double[maxSize][maxSize];
		for (int i = 0; i < weights.length; i++) {
			for (int j = 0; j < weights.length; j++) {
				weights[i][j] = 0.0;
			}
		}
		weights[0][3] =  1.166454e-02;
		weights[1][3] =  8.514623e-02;
		weights[2][3] = -4.774473e-02;
		weights[0][4] =  2.170463e-01;
		weights[1][4] =  2.152450e-01;
		weights[2][4] =  1.153351e-01;
		weights[0][5] = -1.844412e-01;
		weights[3][5] = -1.969224e-02;
		weights[4][5] = -3.293430e-02;
		xorMLP = new MLPnachVorlesung(size[0], size[1], size[2], weights, new SigmoidFunction());
		xorMLP.propagate(this.inputs);

		main.setLayout(new BorderLayout());
		main.add(this.createInputLayer(size[0]), BorderLayout.WEST);
		main.add(this.createHiddenLayer(size[1]), BorderLayout.CENTER);
		main.add(this.createOutputLayer(size[2]), BorderLayout.EAST);
		main.add(this.createControlPanel(), BorderLayout.SOUTH);

		getContentPane().add(main, BorderLayout.CENTER);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		timer.setInitialDelay(0);
	}
	
	public Box createControlPanel() {

		JPanel controlButtonsPanel = new JPanel();
		controlButtonsPanel.setAlignmentX(LEFT_ALIGNMENT);
		JButton nextIterationButton = new JButton("Next");
		JButton startButton = new JButton("Start");
		JButton stopButton = new JButton("Stop");
		JButton resetButton = new JButton("Reset");
		nextIterationButton.addActionListener(this);
		startButton.addActionListener(this);
		stopButton.addActionListener(this);
		resetButton.addActionListener(this);
		controlButtons.put(nextIterationButton, "next");
		controlButtons.put(startButton, "start");
		controlButtons.put(stopButton, "stop");
		controlButtons.put(resetButton, "reset");
		controlButtonsPanel.add(nextIterationButton);
		controlButtonsPanel.add(startButton);
		controlButtonsPanel.add(stopButton);
		controlButtonsPanel.add(resetButton);

		JPanel controlFieldsPanel = new JPanel();
		JLabel learnStepEtaLabel = new JLabel("learnStep");
		JTextField learnStepEtaField = new JTextField("0.8");
		JLabel momentumAlphaLabel = new JLabel("momentum");
		JTextField momentumAlphaField = new JTextField("0.9");
		JLabel maxErrorLabel = new JLabel("maxError");
		JTextField maxErrorField = new JTextField("0.01");
		JLabel iterationLabel = new JLabel("iteration");
		JTextField iterationField = new JTextField("0.0");
		JLabel currentErrorLabel = new JLabel("currentError");
		JTextField currentErrorField = new JTextField("0.0");
		controlFields.put(learnStepEtaField, "learnStepEta");
		controlFields.put(momentumAlphaField, "momentumAlpha");
		controlFields.put(maxErrorField, "maxError");
		controlFields.put(iterationField, "iteration");
		controlFields.put(currentErrorField, "error");
		controlFieldsPanel.add(learnStepEtaLabel);
		controlFieldsPanel.add(learnStepEtaField);
		controlFieldsPanel.add(momentumAlphaLabel);
		controlFieldsPanel.add(momentumAlphaField);
		controlFieldsPanel.add(maxErrorLabel);
		controlFieldsPanel.add(maxErrorField);
		controlFieldsPanel.add(iterationLabel);
		controlFieldsPanel.add(iterationField);
		controlFieldsPanel.add(currentErrorLabel);
		controlFieldsPanel.add(currentErrorField);
		
		Box controlPanel = Box.createVerticalBox();
		controlPanel.add(controlButtonsPanel, BorderLayout.CENTER);
		controlPanel.add(controlFieldsPanel, BorderLayout.SOUTH);
		return controlPanel;
	}

	public Box createInputLayer(int size) {
		Box inputLayer = Box.createVerticalBox();
		// JPanel inputLayerPanel = new JPanel();
		for (int i=0; i<size; i++) {
			JButton neuronButton = new JButton("Input " + i);
			inputNeuronButtons.add(neuronButton);
			inputLayer.add(neuronButton);
			neuronButton.addActionListener(this);
		}
		return inputLayer;
	}

	public Box createHiddenLayer(int size) {
		Box hiddenLayer = Box.createVerticalBox();
		// JPanel hiddenLayer = new JPanel();
		for (int i=0; i<size; i++) {
			JButton neuronButton = new JButton("Hidden " + i);
			hiddenNeuronButtons.add(neuronButton);
			hiddenLayer.add(neuronButton);
			neuronButton.addActionListener(this);
		}
		return hiddenLayer;
	}

	public Box createOutputLayer(int size) {
		Box outputLayer = Box.createVerticalBox();
		// JPanel outputLayer = new JPanel();
		for (int i=0; i<size; i++) {
			JButton neuronButton = new JButton("Output " + i);
			outputNeuronButtons.add(neuronButton);
			outputLayer.add(neuronButton);
			neuronButton.addActionListener(this);
		}
		return outputLayer;
	}

	@Override
	public void actionPerformed(ActionEvent event) {

		// input neurons
		for (JButton button: inputNeuronButtons) {
			int pos = inputNeuronButtons.indexOf(button);
			int realpos = 1 + pos;
			String name = "Input " + pos + "(" + realpos + ")";
			if (event.getSource() == button) {
				if (this.inputs[pos] == 0.0) {
					this.inputs[pos] = 1.0;
				} else if (this.inputs[pos] == 1.0) {
					this.inputs[pos] = 0.0;
				}
				xorMLP.propagate(this.inputs);
			}
			double inputValue = this.inputs[pos];
			double activity = xorMLP.activity[realpos];
			button.setText("<html>" + name + "<br> Value: " + inputValue + "<br> Activation: " + activity + "</html>");
		}
		// hidden neurons
		for (JButton button: hiddenNeuronButtons) {
			int pos = hiddenNeuronButtons.indexOf(button);
			int realpos = 1 + size[0] + pos;
			String name = "Hidden " + pos + "(" + realpos + ")";
			double activity = xorMLP.activity[realpos];
			String weights = "Bias 0 (" + xorMLP.weights[0][realpos] + ")<br>";
			for (int i=0; i<this.size[0];i++) {
				int irealpos = 1 + i;
				weights += "Input " + i + " (" + xorMLP.weights[irealpos][realpos] + ")<br>";
			}
			button.setText("<html>" + name + "<br> Activation: " + activity + "<br>" + weights + "</html>");
		}
		// output neurons
		for (JButton button: outputNeuronButtons) {
			int pos = outputNeuronButtons.indexOf(button);
			int realpos = 1 + size[0] + size[1] + pos;
			String name = "Output " + pos + "(" + realpos + ")";
			double activity = xorMLP.activity[realpos];
			String weights = "Bias 0 (" + xorMLP.weights[0][realpos] + ")<br>";
			for (int i=0; i<this.size[0];i++) {
				int hrealpos = 1 + size[0] + i;
				weights += "Hidden " + i + " (" + xorMLP.weights[hrealpos][realpos] + ")<br>";
			}
			button.setText("<html>" + name + "<br> Activation: " + activity + "<br>" + weights + "</html>");
		}
		
		// control buttons
		for (JButton button: controlButtons.keySet()) {
			String buttonAction = controlButtons.get(button);
			if (event.getSource() == button) {
				// Do the action
				if (buttonAction == "next") {
					iterateLearning();
				} else if (buttonAction == "start") {
					this.learning = true;
					timer.start();
				} else if (buttonAction == "stop") {
					this.learning = false;
					timer.stop();
				} else if (buttonAction == "reset") {
					this.learning = false;
					timer.stop();
					// TODO: Reset funktioniert nicht mehr 
					// this.multiLayerPerceptron.reset();
					
				}
			}
		}
		
		if (this.learning) {
			iterateLearning();
		}
	}
	
	public void iterateLearning() {

		// Defaults 
		double learnStep_eta = 0.8;
		double momentum_alpha = 0.9;
		double max_error = 0.01;
		double error = 0.0;
		double iteration = 0.0;
		JTextField iterationField = null;
		JTextField errorField = null;

		// Read the control fields
		for (JTextField textfield: controlFields.keySet()) {
			Double fieldValue = Double.parseDouble(textfield.getText());
			String fieldId = controlFields.get(textfield);
			if (fieldId == "learnStepEta") {
				learnStep_eta = fieldValue;
			} else if (fieldId == "momentumAlpha") {
				momentum_alpha = fieldValue;
			} else if (fieldId == "maxError") {
				max_error = fieldValue;
			} else if (fieldId == "iteration") {
				iteration = fieldValue;
				iterationField = textfield;
			} else if (fieldId == "error") {
				errorField = textfield;
			}
		}
		
		// execute one iteration
		iteration += 1.0;
		for (double[] p : this.pattern) {
			xorMLP.propagate(p);
			xorMLP.back_propagate(p);
		}
		xorMLP.update_weight(learnStep_eta, momentum_alpha);
		xorMLP.reset_delta();
		
		// calculate error
		error = 0.0;
		for (double[] p : pattern) {
			xorMLP.propagate(p);
			error += xorMLP.calculateError(p);
		}

		// update gui
		iterationField.setText(""+iteration);
		errorField.setText(""+error);

		// continue only if error > max_error!
		if (error < max_error) {
			// System.out.println("done.");
			learning = false;
			timer.stop();
		} else {
			timer.restart();
		}
	}

	public static void main(String[] args) {
		MLPnachVorlesunglGUI gui = new MLPnachVorlesunglGUI();
		gui.setLocation(200, 350);
		gui.setSize(800, 350);
		gui.setVisible(true);
	}

}
