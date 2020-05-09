package mnistfromscratch.net;

import java.util.ArrayList;
import java.util.Arrays;

import mnistfromscratch.net.layers.DenseLayer;
import mnistfromscratch.net.layers.InputLayer;
import mnistfromscratch.net.layers.Layer1D;
import mnistfromscratch.net.layers.OutputLayer;
import mnistfromscratch.net.layers.StackedLayer1D;

public class FeedForwardNetwork
{
	private final InputLayer inputLayer;
	private final ArrayList<StackedLayer1D> hiddenLayers;
	private OutputLayer outputLayer;

	public FeedForwardNetwork(int inputSize)
	{
		this.inputLayer = new InputLayer(inputSize);
		this.hiddenLayers = new ArrayList<StackedLayer1D>();
	}

	public boolean isBuilt()
	{
		return outputLayer != null;
	}

	public void addDenseLayer(int size)
	{
		this.outputLayer = null;
		hiddenLayers.add(new DenseLayer(size, getLastLayer()));
	}

	public void build(int outputSize)
	{
		this.outputLayer = new OutputLayer(outputSize, getLastLayer());
	}

	public float testAccuracy(float[][] testData, float[][] testLabels)
	{
		int correct = 0;
		for (int sample = 0; sample < testData.length; sample++)
		{
			// System.out.print("Sample " + sample + "\r");
			if (testPrediction(testData[sample], testLabels[sample]))
				correct++;
		}

		return ((float) correct / testData.length);
	}

	public boolean testPrediction(float[] input, float[] expected)
	{
		inputLayer.setInputData(input);
		float[] output = outputLayer.calcOutputsRecursive();
		boolean correct = true;
		for (int i = 0; i < output.length; i++)
		{
			if (Math.round(output[i]) != expected[i])
			{
				correct = false;
				break;
			}
		}

		// System.out.println(
		// "Expected: " + Arrays.toString(expected) + " Output: " + Arrays.toString(output) + " (" + result + ")");
		return correct;
	}

	public void train(int epochs, int batchSize, float learningRate, float[][] trainData, float[][] trainLabels)
	{
		if (!isBuilt())
			throw new RuntimeException("Build this network before training it.");

		for (int epoch = 0; epoch < epochs; epoch++)
		{
			System.out.println("Epoch: " + epoch + " Accuracy: " + testAccuracy(trainData, trainLabels));
			trainEpoch(batchSize, learningRate, trainData, trainLabels);
		}
	}

	private void trainEpoch(int batchSize, float learningRate, float[][] trainData, float[][] trainLabels)
	{
		int currentBatch = 0;
		for (int batchStart = 0; batchStart < trainData.length - batchSize; batchStart += batchSize)
		{
			System.out.print("Batch: " + currentBatch++ + " (" + (currentBatch * batchSize) + "/" + trainData.length
			        + ")" + "\r");
			int batchEnd = batchStart + batchSize;
			for (int sample = batchStart; sample < batchEnd; sample++)
			{
				// System.out.print("Sample " + sample + "\r");
				inputLayer.setInputData(trainData[sample]);
				outputLayer.calcOutputsRecursive();

				calcLayerErrors(trainLabels[sample]);
			}
			outputLayer.divideAverageErrorDerivs(batchSize);
			hiddenLayers.get(0).divideAverageErrorDerivs(batchSize);

			outputLayer.updateWeights(learningRate);
			hiddenLayers.get(0).updateWeights(learningRate);

			outputLayer.resetAverageErrorDerivs();
			hiddenLayers.get(0).resetAverageErrorDerivs();
		}
	}

	private void calcLayerErrors(float[] expected)
	{
		outputLayer.calcOutputErrorTerms(expected);
		float[] lastLayerWeightErrorSums = outputLayer.calcWeightErrorSums();
		/*for (int layerInd = hiddenLayers.size() - 1; layerInd >= 0; layerInd--)
		{
			StackedLayer1D layer = hiddenLayers.get(layerInd);
			layer.calcLayerErrorTerms(lastLayerWeightErrorSums);
			if (layerInd != 0)
				lastLayerWeightErrorSums = layer.calcWeightErrorSums();
		}*/
		StackedLayer1D hidden = hiddenLayers.get(0);
		hidden.calcLayerErrorTerms(lastLayerWeightErrorSums);

		outputLayer.calcLayerErrorDeriv(hidden.getOutputs());
		hidden.calcLayerErrorDeriv(inputLayer.getOutputs());

		outputLayer.updateAverageErrorDerivs();
		hidden.updateAverageErrorDerivs();
	}

	private Layer1D getLastLayer()
	{
		if (hiddenLayers.size() == 0)
			return inputLayer;
		return hiddenLayers.get(hiddenLayers.size() - 1);
	}
}
