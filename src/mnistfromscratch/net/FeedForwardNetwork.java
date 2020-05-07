package mnistfromscratch.net;

import java.util.ArrayList;

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

	public void train(float learningRate, float[] expected)
	{
		if (!isBuilt())
			throw new RuntimeException("Build this network before training it.");

		calcLayerErrors(expected);
	}

	private void calcLayerErrors(float[] expected)
	{
		outputLayer.calcOutputError(expected);
		float[] lastLayerWeightErrorSums = outputLayer.calcWeightErrorSums();
		for (int layerInd = hiddenLayers.size(); layerInd >= 0; layerInd--)
		{
			StackedLayer1D layer = hiddenLayers.get(layerInd);
			layer.calcLayerError(lastLayerWeightErrorSums);
		}
	}

	private Layer1D getLastLayer()
	{
		if (hiddenLayers.size() == 0)
			return inputLayer;
		return hiddenLayers.get(hiddenLayers.size() - 1);
	}
}
