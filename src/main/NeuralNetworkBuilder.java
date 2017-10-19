package main;

public class NeuralNetworkBuilder {
	private int numberOfInputs;
	private int numberOfOutputs;
	
	private double learningRate;
	
	private int numberOfHiddenLayers;
	private int numberOfHiddenNodes;
	
	private int epoch;
	
	private int biasState = 0;
	
	public void setBiasNode (int state) {
		biasState = state;
	}
	
	public void setNumberOfInputs (int numberOfInputs) {
		this.numberOfInputs = numberOfInputs;
	}
	
	public void setNumberOfOutputs (int numberOfOutputs) {
		this.numberOfOutputs = numberOfOutputs;
	}
	
	public void setLearningRate (double learningRate) {
		this.learningRate = learningRate;
	}
	
	public void setHiddenLayers (int numberOfHiddenLayers, int numberOfHiddenNodes) {
		this.numberOfHiddenLayers = numberOfHiddenLayers;
		this.numberOfHiddenNodes = numberOfHiddenNodes;
	}
	
	public void setEpoch (int epoch) {
		this.epoch = epoch;
	}
	
	public NeuralNetwork build () {
		NeuralNetwork neuralNetwork = new BasicNeuralNetwork ();
		
		neuralNetwork.setBiasState(biasState);
		neuralNetwork.setNumberOfInputs (numberOfInputs);
		neuralNetwork.setNumberOfOutputs (numberOfOutputs);
		neuralNetwork.setLearningRate(learningRate);
		neuralNetwork.setHiddenLayers(numberOfHiddenLayers, numberOfHiddenNodes);
		neuralNetwork.setEpoch(epoch);
		neuralNetwork.initNetwork();
		
		return neuralNetwork;
	}
}
