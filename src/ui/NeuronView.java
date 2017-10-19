package ui;

import javax.swing.JPanel;
import javax.swing.JTextField;

import presenters.NeuralNetworkPresenter;

public class NeuronView extends JPanel {
	private JTextField activationView;
	private AxonView[] axonViews;
	
	private int layerIndex;
	private int neuronIndex;
	
	public NeuronView (int layerIndex, int neuronIndex) {
		super();
		this.layerIndex = layerIndex;
		this.neuronIndex = neuronIndex;
		
	}
	
	public void initView (NeuralNetworkPresenter presenter) {
		axonViews = new AxonView[presenter.getNeuronSize(layerIndex, neuronIndex)];
		for (int i = 0; i < axonViews.length; i++) {
			axonViews[i] = new AxonView ();
		}
		activationView = new JTextField ();
		activationView.setEnabled(false);
		updateNeuronActivation (presenter);
	}
	
	public void updateNeuronActivation (NeuralNetworkPresenter presenter) {
		activationView.setText(String.valueOf(presenter.getActivationAt(layerIndex, neuronIndex)));
	}
}
