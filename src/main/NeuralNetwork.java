package main;
import java.util.Random;

public abstract class NeuralNetwork {
	protected double [][] expectedOutputs;
	protected double [][] trainingInputs;
	protected Layer[] network;
	protected int numberOfInputs;
	protected int numberOfHiddenLayers;
	protected int numberOfHiddenNodes;
	protected int numberOfOutputs;
	protected double learningRate;
	protected int epoch;
	protected int biasState;

	public NeuralNetwork() {
		super();
	}
	
	public int getSize () {
		return network.length;
	}
	
	public Layer getLayerAt (int layerIndex) {
		return network[layerIndex];
	}
	
	public void setBiasState (int biasState) {
		this.biasState = biasState;
	}
	
	public void setNumberOfInputs(int numberOfInputs) {
		this.numberOfInputs = numberOfInputs;
	}

	public void setNumberOfOutputs(int numberOfOutputs) {
		this.numberOfOutputs = numberOfOutputs;
	}

	public void setTrainingSamples(double[][] trainingInputs) {
		this.trainingInputs = trainingInputs;
	}

	public void setExpectedOutputs(double[][] expectedOutputs) {
		this.expectedOutputs = expectedOutputs;
	}

	public void setLearningRate(double learningRate) {
		this.learningRate = learningRate;
	}

	public void setHiddenLayers(int numberOfHiddenLayers, int numberOfHiddenNodes) {
		this.numberOfHiddenLayers = numberOfHiddenLayers;
		this.numberOfHiddenNodes = numberOfHiddenNodes;
	}

	public void setEpoch(int epoch) {
		this.epoch = epoch;
	}

	private void initLayers() {
		network = new Layer [2 + numberOfHiddenLayers];
		for (int i = 0; i < network.length; i++) {
			int numberOfNodes = 0;
			if (i == 0) {
				numberOfNodes = numberOfInputs;
			}
			else if (i == (network.length - 1)) {
				numberOfNodes = numberOfOutputs;
			}
			else {
				numberOfNodes = numberOfHiddenNodes;
			}
			network[i] = new Layer (numberOfNodes);
		}
	}

	private void initNeurons() {
		for (int i = 0; i < network.length; i++) {
			for (int j = 0; j < network[i].getSize(); j++) {
				if (i == (network.length - 1)) {
					network[i].setNeuronAt(j, new Neuron());
				} else {
					network[i].setNeuronAt(j, new Neuron(network[i + 1].getSize()));
				}
			}
		}
	}

	private void initAxons() {
		Random r = new Random();
		for (Layer layer : network) {
			for (Neuron neuron : layer.getNeurons()) {
				for (int i = 0; i < neuron.getNumberOfAxons(); i++) {
					int random = r.nextInt(100);
					neuron.setAxonWeight(i, random/100.00);
				}
			}
		}
	}

	public void initNetwork() {
		initLayers();
		initNeurons();
		initAxons();
	}

	protected void setInputs(double[] inputs) {
		for (int i = 0; i < network[0].getSize(); i++) {
			network[0].getNeuronAt(i).setActivation(inputs[i]);
		}
	}
	
	protected abstract void forwardPropogate();
	
	protected abstract void backPropogate(double[] expectedOutputs);
	
	public abstract void trainNetwork();
	
	protected void updateNetworkAfterBackProp() {
		for (Layer layer : network) {
			layer.updateNeuronsAfterBackProp();
		}
	}

	public void verifyOutputs() {
		for (int i = 0; i < trainingInputs.length; i++) {
			setInputs (trainingInputs[i]);
			forwardPropogate();
			//System.out.println (this.toString());
		}
	}

	public double[] classifyInput(double[] input) {
		setInputs (input);
		forwardPropogate();
		return this.getOutputs();
	}
	
	private double[] getOutputs() {
		double[] output = new double[numberOfOutputs];
		for (int i = 0; i < numberOfOutputs; i++) {
			output [i] = network[network.length - 1].getNeuronAt(i).getActivation();
			System.out.println(output[i]);
		}
		return output;
	}

	public String toString() {
		String output = new String();
		for (int i = 0; i < network.length; i++) {
			output += "Layer: " + i + "\n";
			if (i == (network.length -1)) {
				output += "Output: | ";
				for (int j = 0; j < numberOfOutputs; j++) {
					output += network[i].getNeuronAt(j).getActivation() + " | ";
					output += "\n__________________________________________";
				}
				output += "\n";
				continue;
			}
			for (int j = 0; j < network[i].getSize(); j++) {
				output += "Activation value: " + network[i].getNeuronAt(j).getActivation() + " Weights : | ";
				for (int k = 0; k < network[i].getNeuronAt(j).getNumberOfAxons(); k++) {
					output += network[i].getNeuronAt(j).getAxonWeight(k) + " | ";
				}
				output += "\n";
			}
		}
		return output;
	}

}