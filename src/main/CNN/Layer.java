package main.CNN;

public abstract class Layer {
	protected double[][][] input;
	protected double[][][] output;
	
	public double[][][] getInput () {
		return input;
	}
	
	public double[][][] getOutput () {
		return output;
	}
	
	public abstract void feedForward (double[][][] input);
	public abstract void backPropagate (double[][][] gradientInput);
	
	public static double[] getMatrixAsVector (double[][][] input) {
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
	
	public static double[][][] getVectorAsMatrix (double[] flattenedInput, double[][][] input) {
		for (int i = 0; i < input.length; i++) {
			for (int j = 0; j < input[0].length; j++) {
				for (int k = 0; k < input[0][0].length; k++) {
					input[i][j][k] = flattenedInput[(i * input[0].length * input[0][0].length) + (j * input[0][0].length) + k];
				}
			}
		}
		return input;
	}
}
