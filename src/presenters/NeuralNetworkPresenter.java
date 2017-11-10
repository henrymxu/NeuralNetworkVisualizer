package presenters;

import main.ANN.ArtificialNeuralNetwork;
import ui.NetworkView;

public class NeuralNetworkPresenter {
	private NetworkView networkView;
	private ArtificialNeuralNetwork neuralNetwork;
	
	public NeuralNetworkPresenter (NetworkView networkView, ArtificialNeuralNetwork neuralNetwork) {
		this.networkView = networkView;
		this.neuralNetwork = neuralNetwork;
	}
	
	public int getNetworkSize () {
		return neuralNetwork.getSize();
	}
	
	public int getLayerSize (int index) {
		return neuralNetwork.getLayerAt(index).getSize();
	}
	
	public int getNeuronSize (int layerIndex, int neuronIndex) {
		return neuralNetwork.getLayerAt(layerIndex).getNeuronAt(neuronIndex).getNumberOfAxons();
	}
	
	public double getActivationAt (int layerIndex, int neuronIndex) {
		return neuralNetwork.getLayerAt(layerIndex).getNeuronAt(neuronIndex).getActivation();
	}
	
	public double getWeightAt (int layerIndex, int neuronIndex, int axonIndex) {
		return neuralNetwork.getLayerAt(layerIndex).getNeuronAt(neuronIndex).getAxonWeight(axonIndex);
	}
	
	public void onNeuralNetworkUpdated (ArtificialNeuralNetwork neuralNetwork) {
		this.neuralNetwork = neuralNetwork;
		networkView.updateNetwork();
	}
}
