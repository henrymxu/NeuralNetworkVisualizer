package main.CNN;

import main.ANN.ArtificialNeuralNetwork;
import main.ANN.BasicArtificialNeuralNetwork;

public abstract class ConvolutionalNeuralNetwork {
	protected double [][] expectedOutputs;
	protected double [][][][] trainingInputs;
	
	protected int numberOfInputs;
	protected int numberOfHiddenLayers = 2;
	protected int numberOfHiddenNodes = 16;
	protected int numberOfOutputs;
	protected double learningRate;
	protected int epoch;
	
	protected FullyConnectedLayer fcNetwork;
	
	public ConvolutionalNeuralNetwork() {
		super();
	}
	
	public void setNumberOfInputs(int numberOfInputs) {
		this.numberOfInputs = numberOfInputs;
	}

	public void setNumberOfOutputs(int numberOfOutputs) {
		this.numberOfOutputs = numberOfOutputs;
	}

	public void setTrainingSamples(double[][][][] trainingInputs) {
		this.trainingInputs = trainingInputs;
	}

	public void setExpectedOutputs(double[][] expectedOutputs) {
		this.expectedOutputs = expectedOutputs;
	}

	public void setLearningRate(double learningRate) {
		this.learningRate = learningRate;
	}

	public void setEpoch(int epoch) {
		this.epoch = epoch;
	}

	public void initNetwork() {
		fcNetwork = new FullyConnectedLayer();
		fcNetwork.setNumberOfOutputs(numberOfOutputs);
		fcNetwork.setExpectedOutputs(expectedOutputs);
		fcNetwork.setLearningRate(learningRate);
		fcNetwork.setHiddenLayers(numberOfHiddenLayers, numberOfHiddenNodes);
		fcNetwork.setEpoch(epoch);
		//fcNetwork.initNetwork();
	}

	protected void setInputs(double[][][] inputs) {
		
	}
	
	protected abstract void forwardPropagate();
	
	protected abstract void backPropagate(double[] expectedOutputs);
	
	public abstract void trainNetwork();

	public void verifyOutputs() {
		for (int i = 0; i < trainingInputs.length; i++) {
			setInputs (trainingInputs[i]);
			forwardPropagate();
			//System.out.println (this.toString());
		}
	}

	public double[] classifyInput(double[][][] input) {
		setInputs (input);
		forwardPropagate();
		return this.getOutputs();
	}
	
	private double[] getOutputs() {
		double[] output = new double[numberOfOutputs];
		return output;
	}

	public String toString() {
		return null;
	}
}
