package main.CNN;

import main.ANN.BasicArtificialNeuralNetwork;
import main.ANN.Layer;
import main.ANN.Neuron;

public class FullyConnectedLayer extends BasicArtificialNeuralNetwork {
	public void feedForward (double[] inputs) {
		this.setInputs(inputs);
		forwardPropagate();
	}
	
	public double[] backPropagateFCLayer (double[] expectedOutput) {
		backPropagate(expectedOutput);
		backPropagateInput();
		updateNetworkAfterBackProp();
		for (int i = 0; i < expectedOutput.length; i++) {
			if (expectedOutput[i] != 0) {
				System.out.print(expectedOutput [i] + " : " + getOutputs()[i]);
				System.out.println("");
			}
		}
		return previousErrorsWRToutputs;
	}
	
	private void backPropagateInput () {
		double [] tempPreviousErrorsWRToutputs = null;
		tempPreviousErrorsWRToutputs = new double[network[0].getSize()];
		for (int input = 0; input < network[0].getSize(); input++) {
			for (int axon = 0; axon < network[1].getSize(); axon++) {
				tempPreviousErrorsWRToutputs[input] += previousErrorsWRToutputs[axon] * network[0].getNeuronAt(input).getAxonWeight(axon); 
			}
			double outputWRTnetinput = Neuron.sigmoidDerivative(network[0].getNeuronAt(input).getSum());
			tempPreviousErrorsWRToutputs[input] *= outputWRTnetinput;
		}
		previousErrorsWRToutputs = tempPreviousErrorsWRToutputs;
	}
	
	private static double inverseSigmoidFunction (double sigmoid) {
		return Math.log(sigmoid/(1-sigmoid));
	}
}
