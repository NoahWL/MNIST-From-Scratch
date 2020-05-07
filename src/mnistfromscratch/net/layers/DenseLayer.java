package mnistfromscratch.net.layers;

import java.util.Arrays;
import java.util.Random;

public class DenseLayer extends StackedLayer1D
{
	public static final int bias = 1;

	protected final float[][] weights;
	protected final float[] weightErrorSums;

	public DenseLayer(int size, Layer1D lastLayer) // TODO: Accept activation function
	{
		super(size, lastLayer);
		this.weightErrorSums = new float[this.size];
		this.weights = new float[size][lastLayer.getSize()];
		initializeWeights();
	}

	@Override
	public float[] calcOutputsRecursive()
	{
		applyWeights();
		applyActivationFunction();
		return outputs;
	}

	public float[] calcWeightErrorSums()
	{
		for (int nodeInd = 0; nodeInd < weights.length; nodeInd++)
		{
			weightErrorSums[nodeInd] = 0;
			for (int weightInd = 0; weightInd < weights[nodeInd].length; weightInd++)
			{
				weightErrorSums[nodeInd] += weights[nodeInd][weightInd] * error[nodeInd];
			}
		}

		return weightErrorSums;
	}

	@Override
	public void calcLayerError(float[] nextLayerWeightErrorSums)
	{
		for (int nodeInd = 0; nodeInd < this.size; nodeInd++)
		{
			error[nodeInd] = outputs[nodeInd] * (1 - outputs[nodeInd]) * nextLayerWeightErrorSums[nodeInd];
		}
	}

	protected void applyWeights() // activation "a"
	{
		float[] inputVals = lastLayer.calcOutputsRecursive();

		for (int node = 0; node < this.size; node++)
		{
			outputs[node] = bias;
			for (int inputNode = 0; inputNode < lastLayer.getSize(); inputNode++)
			{
				outputs[node] += inputVals[inputNode] * weights[node][inputNode];
			}
		}
	}

	protected void applyActivationFunction() // output "o"
	{
		for (int node = 0; node < this.size; node++)
		{
			outputs[node] = sigmoid(outputs[node]);
		}
	}

	/*
	 * https://brilliant.org/wiki/artificial-neural-network/#the-sigmoid-function
	 * f(x) = 1 / (1 + e^-x)
	 */
	private float sigmoid(float x)
	{
		return 1f / (1f + (float) Math.exp(-x));
	}

	/*
	 * TODO: Check/improve.  I don't understand the math behind choosing weights so just implementing something that's simple to me right now.
	 * https://stats.stackexchange.com/a/186351
	 */
	private void initializeWeights()
	{
		double r = 4 * Math.sqrt(6.0 / (lastLayer.getSize() + this.size));
		Random rand = new Random();
		for (int node = 0; node < this.size; node++)
		{
			for (int weight = 0; weight < weights[node].length; weight++)
			{
				weights[node][weight] = (float) (rand.nextGaussian() * r);
			}
		}
		// System.out.println("Weights: " + Arrays.toString(weights[0]));
	}
}
