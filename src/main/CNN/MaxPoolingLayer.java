package main.CNN;

public class MaxPoolingLayer extends Layer {
	private int poolingSize;
	private int poolingStride;
	
	public MaxPoolingLayer (int poolingSize, int poolingStride) {
		this.poolingSize = poolingSize;
		this.poolingStride = poolingStride;
	}
	
	@Override
	public void feedForward(double[][][] input) {
		this.input = input;
		int resultSize = ((input[0].length - poolingSize) / poolingStride)  + 1;
		double[][][] result = new double [input.length][resultSize][resultSize];
		
		for (int depth = 0; depth < input.length; depth++) {
			for (int row = 0; row < input[0].length; row += poolingStride) {
				for (int column = 0; column < input[0][0].length; column += poolingStride) {
					double max = 0;
					for (int poolingRow = 0; poolingRow < poolingSize; poolingRow++) {
						for (int poolingCol = 0; poolingCol < poolingSize; poolingCol++) {
							if (input[depth][row + poolingRow][column + poolingCol] > max) {
								max = input[depth][row + poolingRow][column + poolingCol];
							}
						}
					}
					result[depth][row / poolingStride][column / poolingStride] = max;
				}
			}
		}
		output = result;
	}

	@Override
	public void backPropagate(double[][][] gradientInput) {
		double [][][] result = new double [gradientInput.length][input[0].length][input[0].length];
		for (int depth = 0; depth < input.length; depth++) {
			for (int row = 0; row < input[0].length; row += poolingStride) {
				for (int column = 0; column < input[0][0].length; column += poolingStride) {

					for (int poolingRow = 0; poolingRow < poolingSize; poolingRow++) {
						for (int poolingCol = 0; poolingCol < poolingSize; poolingCol++) {
							if (input[depth][row + poolingRow][column + poolingCol] == output[depth][row / poolingStride][column / poolingStride]) {
								result[depth][row + poolingRow][column + poolingCol] = gradientInput[depth][row / poolingStride][column / poolingStride] * input[depth][row + poolingRow][column + poolingCol];
							} else {
								result[depth][row + poolingRow][column + poolingCol] = 0;
							}
						}
					}
				}
			}
		}
		output = result;
	}

}
