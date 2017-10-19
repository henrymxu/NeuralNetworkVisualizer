package main;

public class Layer {
	private Neuron[] neurons;
	
	public Layer (int size) {
		neurons = new Neuron [size];
	}
	
	public int getSize () {
		return neurons.length;
	}
	
	public Neuron[] getNeurons () {
		return neurons;
	}

	public Neuron getNeuronAt (int index) {
		return neurons[index];
	}
	
	public void setNeuronAt (int index, Neuron neuron) {
		neurons[index] = neuron;
	}
	
	public void updateNeuronsAfterBackProp () {
		for (Neuron neuron : neurons) {
			neuron.updateAxonWeights();
		}
	}
}
