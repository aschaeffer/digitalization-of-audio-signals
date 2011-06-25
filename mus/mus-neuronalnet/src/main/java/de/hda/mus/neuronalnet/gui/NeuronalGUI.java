package de.hda.mus.neuronalnet.gui;

import java.awt.BorderLayout;
import java.awt.Component;
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

import de.hda.mus.neuronalnet.MLP;
import de.hda.mus.neuronalnet.neuron.HiddenNeuron;
import de.hda.mus.neuronalnet.neuron.InputNeuron;
import de.hda.mus.neuronalnet.neuron.Neuron;
import de.hda.mus.neuronalnet.neuron.OutputNeuron;
import de.hda.mus.neuronalnet.transferfunction.SigmoidFunction;

public class NeuronalGUI extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	MLP multiLayerPerceptron = new MLP();
	InputNeuron neuron1;
	InputNeuron neuron2;
	HiddenNeuron neuron3;
	HiddenNeuron neuron4;
	OutputNeuron neuron5;

	JPanel main = new JPanel();
	ArrayList<JPanel> layers = new ArrayList<JPanel>();
	HashMap<JButton, InputNeuron> inputNeuronButtons = new HashMap<JButton, InputNeuron>();
	HashMap<JButton, HiddenNeuron> hiddenNeuronButtons = new HashMap<JButton, HiddenNeuron>();
	HashMap<JButton, OutputNeuron> outputNeuronButtons = new HashMap<JButton, OutputNeuron>();
	HashMap<JButton, String> controlButtons = new HashMap<JButton, String>();
	HashMap<JTextField, String> controlFields = new HashMap<JTextField, String>();
	HashMap<OutputNeuron, Double> outputNeuronTargets = new HashMap<OutputNeuron, Double>();
	Boolean learning = false;
	Timer timer = new Timer(50, this);
	int[][] pattern = { { 0, 0, 1 }, { 1, 1, 1 }, { 1, 0, 0 }, { 0, 1, 0 } };

	public NeuronalGUI() {
		this.initMLP(this.multiLayerPerceptron);

		main.setLayout(new BorderLayout());
		main.add(this.createInputLayer(this.multiLayerPerceptron.getInputLayer()), BorderLayout.WEST);
		main.add(this.createHiddenLayer(this.multiLayerPerceptron.getHiddenLayer()), BorderLayout.CENTER);
		main.add(this.createOutputLayer(this.multiLayerPerceptron.getOutputLayer()), BorderLayout.EAST);
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
//		nextIterationButton.setAlignmentX(Component.LEFT_ALIGNMENT);
//		startButton.setAlignmentX(Component.LEFT_ALIGNMENT);
//		stopButton.setAlignmentX(Component.LEFT_ALIGNMENT);
//		resetButton.setAlignmentX(Component.LEFT_ALIGNMENT);
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

	public Box createInputLayer(ArrayList<InputNeuron> neuronLayer) {
		Box inputLayer = Box.createVerticalBox();
		// JPanel inputLayerPanel = new JPanel();
		for (InputNeuron inputNeuron : neuronLayer) {
			JButton neuronButton = new JButton(inputNeuron.getName());
			inputNeuronButtons.put(neuronButton, inputNeuron);
			inputLayer.add(neuronButton);
			neuronButton.addActionListener(this);
		}
		return inputLayer;
	}

	public Box createHiddenLayer(ArrayList<HiddenNeuron> neuronLayer) {
		Box hiddenLayer = Box.createVerticalBox();
		// JPanel hiddenLayer = new JPanel();
		for (HiddenNeuron hiddenNeuron : neuronLayer) {
			JButton neuronButton = new JButton(hiddenNeuron.getName());
			hiddenNeuronButtons.put(neuronButton, hiddenNeuron);
			hiddenLayer.add(neuronButton);
			neuronButton.addActionListener(this);
		}
		return hiddenLayer;
	}

	public Box createOutputLayer(ArrayList<OutputNeuron> neuronLayer) {
		Box outputLayer = Box.createVerticalBox();
		// JPanel outputLayer = new JPanel();
		for (OutputNeuron outputNeuron : neuronLayer) {
			JButton neuronButton = new JButton(outputNeuron.getName());
			outputNeuronButtons.put(neuronButton, outputNeuron);
			outputNeuronTargets.put(outputNeuron, 0.0);
			outputLayer.add(neuronButton);
			neuronButton.addActionListener(this);
		}
		return outputLayer;
	}
	
//	public double getTarget() {
//		int[] values;
//		for (int i=0; i< this.multiLayerPerceptron.getInputLayer().size(); i++) {
//			values[i] = this.multiLayerPerceptron.getInputLayer().get(i).getValue();
//		}
//	}

	@Override
	public void actionPerformed(ActionEvent event) {
		// System.out.println("action");
		// input neurons
		for (JButton button: inputNeuronButtons.keySet()) {
			InputNeuron n = inputNeuronButtons.get(button);
			if (event.getSource() == button) {
				if (n.getValue() == 0.0) {
					n.setValue(1.0);
				} else if (n.getValue() == 1.0) {
					n.setValue(0.0);
				}
			}
			String text = "<html>" + n.getName() + "<br>Value: " + n.getValue() + "<br>Activation: "+ n.activation() + "</html>";
			button.setText(text);
		}
		// hidden neurons
		for (JButton button: hiddenNeuronButtons.keySet()) {
			HiddenNeuron hiddenNeuron = hiddenNeuronButtons.get(button);
			double activation = hiddenNeuron.activation();
			String weights = "";
			for (Neuron inputNeuron : hiddenNeuron.getPreNeurons().keySet()) {
				weights += inputNeuron.getName() + " (" + hiddenNeuron.getPreNeurons().get(inputNeuron) + ")<br>";
			}
			String text = "<html>" + hiddenNeuron.getName() + "<br>Activation: " + activation + "<br>" + weights + "</html>";
			button.setText(text);
		}
		// output neurons
		for (JButton button: outputNeuronButtons.keySet()) {
			OutputNeuron outputNeuron = outputNeuronButtons.get(button);
			if (event.getSource() == button) {
				if (outputNeuronTargets.get(outputNeuron) == 0.0) {
					outputNeuronTargets.put(outputNeuron, 1.0);
				} else {
					outputNeuronTargets.put(outputNeuron, 0.0);
				}
			}
			double activation = outputNeuron.activation();
			double target = outputNeuronTargets.get(outputNeuron);
			double weightedFlaw = outputNeuron.weightedFlaw(target);
			String weights = "";
			for (Neuron hiddenNeuron : outputNeuron.getPreNeurons().keySet()) {
				weights += hiddenNeuron.getName() + " (" + outputNeuron.getPreNeurons().get(hiddenNeuron) + ")<br>";
			}
			String text = "<html>" + outputNeuron.getName() + "<br>Activation: " + activation + "<br>Target: " + target + "<br>WeightedFlaw: " + weightedFlaw + "<br>" + weights + "</html>";
			button.setText(text);
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
					this.resetMLP();
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
		double iteration = 0.0;
		double error = 0.0;
		boolean batch_update = true;
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
				error = fieldValue;
				errorField = textfield;
			}
		}
		
		// execute one iteration
		iteration += 1.0;
		error = multiLayerPerceptron.learn(learnStep_eta, momentum_alpha, pattern, batch_update);

		// update gui
		iterationField.setText(""+iteration);
		errorField.setText(""+error);
		
		// continue only if error > max_error!
		if (error < max_error) {
			System.out.println("done.");
		} else {
			timer.restart();
		}
	}

	public void initMLP(MLP multiLayerPerceptron) {
		this.multiLayerPerceptron = multiLayerPerceptron;
		this.multiLayerPerceptron.setDefaultTransferFunction(new SigmoidFunction());
		neuron1 = multiLayerPerceptron.addInputNeuron("neuron 1", 0);
		neuron2 = multiLayerPerceptron.addInputNeuron("neuron 2", 0);
		neuron3 = multiLayerPerceptron.addHiddenNeuron("neuron 3");
		neuron4 = multiLayerPerceptron.addHiddenNeuron("neuron 4");
		neuron5 = multiLayerPerceptron.addOutputNeuron("neuron 5");
		this.resetMLP();
	}

	public void resetMLP() {
		InputNeuron bias = this.multiLayerPerceptron.getBiasNeuron();
		neuron3.putPreNeuron(bias, 1.166454e-02); // set threshold
		neuron3.putPreNeuron(neuron1, 8.514623e-02); // set weight
		neuron3.putPreNeuron(neuron2, -4.774473e-02); // set weight
		neuron4.putPreNeuron(bias, 2.170463e-01); // set threshold
		neuron4.putPreNeuron(neuron1, 2.152450e-01); // set weight
		neuron4.putPreNeuron(neuron2, 1.153351e-01); // set weight
		neuron5.putPreNeuron(bias, -1.844412e-01); // set threshold
		neuron5.putPreNeuron(neuron3, -1.969224e-02); // set weight
		neuron5.putPreNeuron(neuron4, -3.293430e-02); // set weight
	}
	
	public static void main(String[] args) {
		NeuronalGUI gui = new NeuronalGUI();
		gui.setLocation(200, 350);
		gui.setSize(800, 350);
		gui.setVisible(true);
	}

}
