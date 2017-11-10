package main.CNN;

public class ConvolutionalLayer extends Layer{
	private Filter[] filters;
	private int filterStride;
	
	public ConvolutionalLayer (Filter[] filters, int filterStride) {
		this.filters = filters;
		this.filterStride = filterStride;
	}
	
	@Override
	public void feedForward(double[][][] input) {
		this.input = input;
		
		int resultSize = ((input[0].length - filters[0].getFilterSize())/ filterStride) + 1;
		double[][][] result = new double[input.length][resultSize][resultSize];
		for (int depth = 0; depth < result.length; depth++) {
			double[][] filter = filters[depth].getFilter();
			for (int row = 0; row < result[0].length; row += filterStride) {
				for (int column = 0; column < result[0][0].length; column += filterStride) {
					
					for (int filterRow = 0; filterRow < filters[0].getFilterSize(); filterRow++) {
						for (int filterCol = 0; filterCol < filters[0].getFilterSize(); filterCol++) {
							result[depth][row / filterStride][column / filterStride] += input[depth][row + filterRow][column + filterCol] * filter[filterRow][filterCol];
						}
					}
				}
			}
		}
		this.output = result;
	}

	@Override
	public void backPropagate(double[][][] gradientInput) {
		int gradientSize = gradientInput[0].length;
		gradientInput = expandInput (gradientInput, input[0].length);
		double[][][] result = new double[input.length][input[0].length][input[0][0].length];
		for (int depth = 0; depth < result.length; depth++) {
			double[][] filter = filters[depth].getRotatedFilter();
			double[][] delta = filters[depth].getTempFilter();
			for (int row = 0; row < gradientSize; row += filterStride) {
				for (int column = 0; column < gradientSize; column += filterStride) { 
					
					for (int filterRow = 0; filterRow < filters[0].getFilterSize(); filterRow++) {
						for (int filterCol = 0; filterCol < filters[0].getFilterSize(); filterCol++) {
							result[depth][row / filterStride][column / filterStride] += gradientInput[depth][row + filterRow][column + filterCol] * filter[filterRow][filterCol];
							delta[filterRow][filterCol] += result[depth][row / filterStride ][column / filterStride];
						}
					}
				}
			}
			
			filter = filters[depth].getFilter();
			for (int filterRow = 0; filterRow < filters[0].getFilterSize(); filterRow++) {
				for (int filterCol = 0; filterCol < filters[0].getFilterSize(); filterCol++) {
					filter[filterRow][filterCol] = filter[filterRow][filterCol] - delta[filterRow][filterCol];
				}
			}
			filters[depth].updateFilterWeights();
		}
		this.output = result;
	}

	private static double[][][] expandInput (double [][][] input, int targetSize) {
		double [][][] expandedInput = new double [input.length][targetSize][targetSize];
		int padding = (targetSize - input[0].length) / 2;
	
		for (int i = 0; i < input.length; i++) {
			for (int j = padding; j < targetSize - padding; j++) {
				for (int k = padding; k < targetSize - padding; k++) {
					expandedInput[i][j][k] = input[i][j - padding][k - padding];
				}
			}
		}
		return expandedInput;
	}
}
