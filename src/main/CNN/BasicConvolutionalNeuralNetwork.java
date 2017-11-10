package main.CNN;

import java.util.Arrays;

public class BasicConvolutionalNeuralNetwork extends ConvolutionalNeuralNetwork {
	private static int filterSize = 5;
	private static int filterStride = 1;
	
	private static int poolingStride = 2;
	private static int poolingSize = 2;
			
	private static int fcInputSize = 54;
	private static int output = 10;
	
	private Filter[][] filters; //[Filter Layer][Number of Filters] = [row][index]
	
	private double[][][] input;
	private double[] expectedOutput;
	
	private Layer[] network;
	@Override
	protected void forwardPropagate() {
		if (!fcNetwork.isInitialized()) {
			fcNetwork.setNumberOfInputs(fcInputSize);
			fcNetwork.initNetwork();
		}

		for (Layer layer : network) {
			layer.feedForward(input);
			input = layer.getOutput();
		}
		/*for (int i = 0; i < input.length; i++) {
			System.out.print("LAYER NUMBER " + i);
			for (int j = 0; j < input[0].length; j++) {
				System.out.println("");
				for (int k = 0; k < input[0].length; k++) {
					System.out.print(input[i][j][k] + "\t");
				}
			}
			System.out.println("");
		}*/
		fcNetwork.feedForward(Layer.getMatrixAsVector(input));
	}

	@Override
	protected void backPropagate(double[] expectedOutputs) {
		double[] fcErrorsWRToutput = fcNetwork.backPropagateFCLayer(expectedOutput);
		double[][][] restoredInput = Layer.getVectorAsMatrix(fcErrorsWRToutput, input);
		/*for (int i = 0; i < restoredInput.length; i++) {
			System.out.print("LAYER NUMBER " + i);
			for (int j = 0; j < restoredInput[0].length; j++) {
				System.out.println("");
				for (int k = 0; k < restoredInput[0].length; k++) {
					System.out.print(restoredInput[i][j][k] + "\t");
				}
			}
			System.out.println("");
		}*/
		for (int i = network.length - 1; i > 0; i--) {
			network[i].backPropagate(restoredInput);
			restoredInput = network[i].getOutput();
		}
		

	}


	@Override
	public void trainNetwork() {
		initNetwork();
		initializeFilters();
		
		for (int i = 0; i < trainingInputs.length ; i++) {
			input = trainingInputs[i];
			expectedOutput = expectedOutputs[i];
			input = duplicateLayers (input, 6);
		
			network = new Layer[10];
			network[0] = new ConvolutionalLayer (filters[0], filterStride);
			network[1] = new ReLULayer ();
			network[2] = new ConvolutionalLayer (filters[1], filterStride);
			network[3] = new ReLULayer ();
			network[4] = new MaxPoolingLayer (poolingSize, poolingStride);
			network[5] = new ReLULayer ();
			network[6] = new ConvolutionalLayer (filters[2], filterStride);
			network[7] = new ReLULayer ();
			network[8] = new MaxPoolingLayer (poolingSize, poolingStride);
			network[9] = new ReLULayer ();
		
		forwardPropagate();
		backPropagate(expectedOutput);
		}
	}
	
	
	private void initializeFilters () {
		filters = new Filter[3][6];
		for (int i = 0; i < filters.length; i++) {
			for (int j = 0; j < filters[0].length; j++) {
				filters[i][j] = new Filter (filterSize);
			}
		}
	}
	
	private static double[][][] convolutionLayer (double input[][][], Filter[] filters) {
		int resultSize = ((input[0].length - filterSize)/ filterStride) + 1;
		
		double[][][] result = new double[input.length][resultSize][resultSize];
		
		for (int depth = 0; depth < result.length; depth++) {
			double[][] filter = filters[depth].getFilter();
			for (int row = 0; row < result[0].length; row += filterStride) {
				for (int column = 0; column < result[0][0].length; column += filterStride) {
					for (int filterRow = 0; filterRow < filterSize; filterRow++) {
						for (int filterCol = 0; filterCol < filterSize; filterCol++) {
							result[depth][row / filterStride][column / filterStride] += input[depth][row + filterRow][column + filterCol] * filter[filterRow][filterCol];
						}
					}
					
				}
			}
		}
		return result;
	}

	private static double[] flattenLayer (double [][][] input) {
		double[] flattenedArray = new double[input.length * input[0].length * input[0][0].length];
		for (int i = 0; i < input.length; i++) {
			for (int j = 0; j < input[0].length; j++) {
				for (int k = 0; k < input[0][0].length; k++) {
					flattenedArray [i * input[0].length * input[0][0].length + j * input[0][0].length + k] = input[i][j][k];
				}
			}
		}
		return flattenedArray;
	}
	
	private static double[][][] restoreLayer (double[] flattenedInput, double[][][] input) {
		for (int i = 0; i < input.length; i++) {
			for (int j = 0; j < input[0].length; j++) {
				for (int k = 0; k < input[0][0].length; k++) {
					input[i][j][k] = flattenedInput[(i * input[0].length * input[0][0].length) + (j * input[0][0].length) + k];
				}
			}
		}
		return input;
	}
	
	private static double ReLU (double value) {
		return Math.max(0, value);
	}
	
	private static double[] softMax (double[] outputs) {
		double[] squashedOutput = new double[outputs.length];
		double sumOfExponents = 0;
		for (int i = 0; i < outputs.length; i++) {
			sumOfExponents += Math.exp(outputs[i]);
		}
		for (int i = 0; i < outputs.length; i++) {
			squashedOutput[i] = Math.exp(outputs[i])/sumOfExponents;
		}
		return squashedOutput;
	}
	
	private static int classifyOutput (double[] outputs) {
		int maxIndex = 0;
		for (int i = 0; i < outputs.length; i++) {
			maxIndex = outputs[i] > outputs[maxIndex] ? i : maxIndex;
		}
		return maxIndex;
	}
	
	private static double[][][] duplicateLayers (double[][][] input, int layers) {
		double[][][] result = new double [layers][input[0].length][input[0][0].length];
		for (int i = 0; i < layers; i++) {
			for (int j = 0; j < result[0].length; j++) {
				for (int k = 0; k < result[0][0].length; k++) {
					result[i][j][k] = input[0][j][k];
				}
			}
		}
		return result;
	}
}
