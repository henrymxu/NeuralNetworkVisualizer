package main;

import java.io.IOException;

import main.ANN.ArtificialNeuralNetwork;
import main.CNN.BasicConvolutionalNeuralNetwork;
import main.CNN.ConvolutionalNeuralNetwork;
import mnsit.FileReader;

public class main {
	public static double [][] XORinputs = new double[][]{{0, 0}, {0, 1}, {1, 0}, {1, 1}};
	public static double [][] XORoutputs = new double[][]{{0}, {1}, {1}, {0}};
	
	public static double [][] ANDinputs = new double[][]{{0, 0}, {0, 1}, {1, 0}, {1, 1}};
	public static double [][] ANDoutputs = new double[][]{{0}, {0}, {0}, {1}};
	
	public static double [][] threeXORinputs = new double [][] {{0, 0, 0}, {0, 0, 1}, {0, 1, 0}, {0, 1, 1}, {1, 0, 0}, {1, 0, 1}, {1, 1, 0}, {1, 1, 1}};
	public static double [][] threeXORoutputs = new double [][] {{0}, {1}, {1}, {0}, {1}, {0}, {0}, {1}};
	
	static int numberOfInputs = 2;
	static int numberOfOutputs = 1;
	
	static int numberOfHiddenLayers = 2;
	static int numberOfHiddenNodes = 3;
	
	static double learningRate = 0.5;
	static int epoch = 1000000;
	
	public static void main (String args[]) {
		FileReader fReader = new FileReader ("C:\\Users\\Henry\\Documents\\Workspace\\NeuralNetworkVisualizer\\src\\mnsit\\train-labels.idx1-ubyte", "C:\\Users\\Henry\\Documents\\Workspace\\NeuralNetworkVisualizer\\src\\mnsit\\train-images.idx3-ubyte");
		try {
			fReader.loadDigitImages();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//outputMNIST(fReader.getInputData());
		
		/*NeuralNetworkBuilder networkBuilder = new NeuralNetworkBuilder();
		networkBuilder.setBiasNode(1);
		networkBuilder.setNumberOfInputs(numberOfInputs);
		networkBuilder.setNumberOfOutputs(numberOfOutputs);
		networkBuilder.setHiddenLayers(numberOfHiddenLayers, numberOfHiddenNodes);
		networkBuilder.setLearningRate(learningRate);
		networkBuilder.setEpoch(epoch);
		
		ArtificialNeuralNetwork neuralNetwork = networkBuilder.build();
		neuralNetwork.setTrainingSamples(XORinputs);
		neuralNetwork.setExpectedOutputs(XORoutputs);
		
		neuralNetwork.trainNetwork();
		neuralNetwork.verifyOutputs();
		
		neuralNetwork.classifyInput(XORinputs[1]);*/
		
		ConvolutionalNeuralNetwork network = new BasicConvolutionalNeuralNetwork ();
		network.setTrainingSamples(fReader.getInputData());
		network.setExpectedOutputs(fReader.getOutputData());
		network.setNumberOfOutputs(10);
		network.trainNetwork();
	}
	
	public static void outputMNIST (double[][][][] inputs) {
		for (int i = 0; i < inputs[0][0].length; i++) {
			for (int j = 0; j < inputs[0][0][0].length; j++) {
				System.out.print(inputs[0][0][i][j] + "\t");
			}
			System.out.println ("");
		}
	}
}
