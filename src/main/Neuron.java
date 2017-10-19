package main;

public class Neuron {
	private double sum = 0;
	private double activationValue;
	private Axon[] axons;
	private double[] tempWeights;
	
	public Neuron () {
		
	}
	
	public Neuron (int numberOfWeights) {
		axons = new Axon[numberOfWeights];
		tempWeights = new double[numberOfWeights];
	}
	
	public Axon[] getAxons () {
		return axons;
	}
	
	public int getNumberOfAxons () {
		if (axons == null) {
			return 0;
		}
		return axons.length;
	}
	
	public double getAxonWeight (int targetAxon) {
		return axons[targetAxon].getWeight();
	}
	
	public void setAxonWeight (int targetAxon, double weight) {
		if (axons[targetAxon] == null) {
			axons[targetAxon] = new Axon();
		}
		axons[targetAxon].setWeight(weight);
	}
	
	public void setNewAxonWeight (int targetAxon, double weight) {
		tempWeights[targetAxon] = weight;
	}
	
	public void updateAxonWeights () {
		if (tempWeights == null) {
			return;
		}
		for (int i = 0; i < axons.length; i++) {
			if (tempWeights[i] == Double.MAX_VALUE) {
				return;
			}
			axons[i].setWeight(tempWeights[i]);
			tempWeights[i] = Double.MAX_VALUE;
		}
	}
	
	public double getSum () {
		return sum;
	}
	
	public double getActivation () {
		return activationValue;
	}
	
	public void addToSum (double sum) {
		this.sum += sum;
	}
	
	public void resetSum () {
		sum = 0;
	}
	public void setActivation (double activation) {
		this.activationValue = activation;
	}
	
	public void determineActivation () {
		activationValue = sigmoidFunction(sum);
	}
	
	public static double sigmoidFunction (double value) {
		return (1 / (1 + Math.exp(-value)));
	}
	
	public static double sigmoidDerivative (double value) {
		return (sigmoidFunction(value) * (1 - sigmoidFunction(value)));
	}
}
