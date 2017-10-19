package main;
import java.util.Random;

public class BasicNeuralNetwork extends NeuralNetwork {
	
	@Override
	protected void forwardPropogate() {
		for (int layer = 0; layer < network.length - 1; layer++) {
			Layer cLayer = network[layer];
			Layer nLayer = network[layer + 1];
			for (int outputNeuronIndex = 0; outputNeuronIndex < nLayer.getSize(); outputNeuronIndex++) {
				nLayer.getNeuronAt(outputNeuronIndex).resetSum();
				for (int neuron = 0; neuron < cLayer.getSize(); neuron++) {
					double axonValue = cLayer.getNeuronAt(neuron).getAxonWeight(outputNeuronIndex) * cLayer.getNeuronAt(neuron).getActivation();
					nLayer.getNeuronAt(outputNeuronIndex).addToSum(axonValue);
				}
				nLayer.getNeuronAt(outputNeuronIndex).determineActivation();
			}	
		}
	}
	
	@Override
	protected void backPropogate(double[] expectedOutputs) {
		double [] previousErrorsWRToutputs = null;
		double [] tempPreviousErrorsWRToutputs = null;
		
		for (int layer = network.length - 1; layer > 0; layer--) {
			Layer cLayer = network [layer];
			Layer pLayer = network [layer - 1];
			
			tempPreviousErrorsWRToutputs = new double [cLayer.getSize()];
			
			for (int neuronIndex = 0; neuronIndex < cLayer.getSize(); neuronIndex++) {
				Neuron neuron = cLayer.getNeuronAt(neuronIndex);
				double errorWRToutput = 0;
				if (previousErrorsWRToutputs == null) {
					errorWRToutput = neuron.getActivation() - expectedOutputs[neuronIndex];
				} else {
					Layer fLayer = network [layer + 1];
					for (int outputs = 0; outputs < fLayer.getSize(); outputs++) {
						errorWRToutput += (previousErrorsWRToutputs [outputs] * neuron.getAxonWeight(outputs)) ;
					}
				}
				double outputWRTnetinput = Neuron.sigmoidDerivative(neuron.getSum());
	
				for (int axonIndex = 0; axonIndex < pLayer.getSize(); axonIndex++) {
					Neuron pNeuron = pLayer.getNeuronAt(axonIndex);
					double netinputWRTweight = pNeuron.getActivation();
					double errorWRTweight = errorWRToutput * outputWRTnetinput * netinputWRTweight;
					pNeuron.setNewAxonWeight(neuronIndex, (pNeuron.getAxonWeight(neuronIndex) - (learningRate * errorWRTweight)));
				}
				tempPreviousErrorsWRToutputs [neuronIndex] = errorWRToutput * outputWRTnetinput;
			}
			previousErrorsWRToutputs = tempPreviousErrorsWRToutputs;
		}
		updateNetworkAfterBackProp();
	}
	
	@Override
	public void trainNetwork() {
		Random r = new Random();
		for (int j = 0; j < 10000; j++) {
			System.out.println("SET : " + j);
		for (int i = 0; i < epoch / (trainingInputs.length); i++) {
			int testCase = r.nextInt(trainingInputs.length);
				setInputs (trainingInputs[testCase]);
				forwardPropogate();
				backPropogate (expectedOutputs[testCase]);
		}
		}
	}
}
