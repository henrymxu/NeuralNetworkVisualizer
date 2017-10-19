package main;

import java.io.IOException;

import mnsit.FileReader;

public class main {
	public static double [][] XORinputs = new double[][]{{0, 0}, {0, 1}, {1, 0}, {1, 1}};
	public static double [][] XORoutputs = new double[][]{{0}, {1}, {1}, {0}};
	
	public static double [][] ANDinputs = new double[][]{{0, 0}, {0, 1}, {1, 0}, {1, 1}};
	public static double [][] ANDoutputs = new double[][]{{0}, {0}, {0}, {1}};
	
	public static double [][] threeXORinputs = new double [][] {{0, 0, 0}, {0, 0, 1}, {0, 1, 0}, {0, 1, 1}, {1, 0, 0}, {1, 0, 1}, {1, 1, 0}, {1, 1, 1}};
	public static double [][] threeXORoutputs = new double [][] {{0}, {1}, {1}, {0}, {1}, {0}, {0}, {1}};
	
	static int numberOfInputs = 784;
	static int numberOfOutputs = 10;
	
	static int numberOfHiddenLayers = 2;
	static int numberOfHiddenNodes = 16;
	
	static double learningRate = 0.5;
	static int epoch = 2000000000;
	
	public static void main (String args[]) {
		FileReader fReader = new FileReader ("C:\\Users\\Henry\\Documents\\Workspace\\NeuralNetworkVisualizer\\src\\mnsit\\train-labels.idx1-ubyte", "C:\\Users\\Henry\\Documents\\Workspace\\NeuralNetworkVisualizer\\src\\mnsit\\train-images.idx3-ubyte");
		try {
			fReader.loadDigitImages();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		NeuralNetworkBuilder networkBuilder = new NeuralNetworkBuilder();
		//networkBuilder.setBiasNode(1);
		networkBuilder.setNumberOfInputs(numberOfInputs);
		networkBuilder.setNumberOfOutputs(numberOfOutputs);
		networkBuilder.setHiddenLayers(numberOfHiddenLayers, numberOfHiddenNodes);
		networkBuilder.setLearningRate(learningRate);
		networkBuilder.setEpoch(epoch);
		
		NeuralNetwork neuralNetwork = networkBuilder.build();
		neuralNetwork.setTrainingSamples(fReader.getInputData());
		neuralNetwork.setExpectedOutputs(fReader.getOutputData());
		
		neuralNetwork.trainNetwork();
		neuralNetwork.verifyOutputs();
		
		neuralNetwork.classifyInput(fReader.getInputData()[1]);
	}
}
