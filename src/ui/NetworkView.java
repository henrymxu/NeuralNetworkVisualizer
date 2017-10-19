package ui;

import javax.swing.JPanel;

import presenters.NeuralNetworkPresenter;

public class NetworkView {
	private NeuralNetworkPresenter presenter;
	private LayerView[] layerViews;
	
	public NetworkView () {
		
	}
	
	public void updateNetwork () {
		for (LayerView layer : layerViews) {
			layer.updateNeurons(presenter);
		}
	}
	
	public void setPresenter (NeuralNetworkPresenter presenter) {
		this.presenter = presenter;
	}
	
	public void initView () {
		layerViews = new LayerView [presenter.getNetworkSize()];
		for (int i = 0; i < layerViews.length; i++) {
			layerViews [i] = new LayerView (i);
			layerViews [i].initView(presenter);
		}
	}
}
