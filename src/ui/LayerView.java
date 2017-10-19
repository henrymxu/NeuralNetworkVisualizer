package ui;

import javax.swing.JPanel;

import presenters.NeuralNetworkPresenter;

public class LayerView extends JPanel {
	private NeuronView[] neuronViews;
	private int layerIndex;
	public LayerView (int layerIndex) {
		super();
		this.layerIndex = layerIndex;
	}
	
	public void initView (NeuralNetworkPresenter presenter) {
		neuronViews = new NeuronView [presenter.getLayerSize(layerIndex)];
		for (int i = 0; i < neuronViews.length; i++) {
			neuronViews [i] = new NeuronView (layerIndex, i);
			neuronViews [i].initView(presenter);
		}
	}
	
	public void updateNeurons (NeuralNetworkPresenter presenter) {
		for (int i = 0; i < neuronViews.length; i++) {
			neuronViews[i].updateNeuronActivation(presenter);
		}
	}
}
