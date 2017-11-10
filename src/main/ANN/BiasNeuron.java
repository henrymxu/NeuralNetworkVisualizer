package main.ANN;

public class BiasNeuron extends Neuron {
	
	public BiasNeuron (int numberOfWeights) {
		super(numberOfWeights);
	}
	
	@Override
	public double getActivation () {
		return 1;
	}
}
