package main.CNN;

public class ReLULayer extends Layer {
	@Override
	public void feedForward(double[][][] input) {
		this.input = input;
		double[][][] result = new double[input.length][input[0].length][input[0][0].length];
		
		for (int i = 0; i < input.length; i++) {
			for (int j = 0; j < input[0].length; j++) {
				for (int k = 0; k < input[0][0].length; k++) {
					result[i][j][k] = ReLU(input[i][j][k]);
				}
			}
		}
		output = result;
	}
	
	@Override
	public void backPropagate(double[][][] gradientInput) {
		double[][][] result = new double[gradientInput.length][gradientInput[0].length][gradientInput[0][0].length];
		for (int i = 0; i < gradientInput.length; i++) {
			for (int j = 0; j < gradientInput[0].length; j++) {
				for (int k = 0; k < gradientInput[0][0].length; k++) {
					result[i][j][k] = inverseReLU(this.input[i][j][k], gradientInput[i][j][k]);
				}
			}
		}
		output = result;
	}
	
	private static double ReLU (double value) {
		return Math.max(0, value);
	}
	
	private static double inverseReLU (double activatedValue, double value) {
		return activatedValue < 0 ? 0 : value;
	}
}
