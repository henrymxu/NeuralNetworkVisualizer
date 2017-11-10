package main.CNN;

import java.util.Random;

public class Filter {
	private double [][] filter;
	private double [][] tempFilter;
	public Filter (double[][] filter) {
		this.filter = filter;
		resetTempFilter();
	}
	
	public Filter (int size) {
		initializeFilter(size);
		resetTempFilter();
	}
	
	public int getFilterSize () {
		return filter.length;
	}
	
	public double[][] getFilter () {
		return filter;
	}
	
	public double[][] getTempFilter () {
		return tempFilter;
	}
	
	public void initializeFilter (int size) {
		filter = new double[size][size];
		randomizeWeights ();
	}
	
	public void updateFilterWeights () {
		resetTempFilter();
	}
	
	private void resetTempFilter () {
		tempFilter = new double[filter.length][filter[0].length];
	}
	
	private void randomizeWeights () {
		Random r = new Random ();
		for (int i = 0; i < filter.length; i++) {
			for (int j = 0; j < filter[0].length; j++) {
				filter[i][j] = (r.nextGaussian());
			}
		}
	}
	
	public double[][] getRotatedFilter () {
		double[][] rotatedFilter = new double [filter.length][filter[0].length];
		for (int i = 0; i < filter.length; i++) {
			for (int j = 0; j < filter[0].length; j++) {
				rotatedFilter[i][j] = filter[filter.length - j - 1][i];
			}
		}
		return rotatedFilter;
	}
	
	
}
