package mnistfromscratch.net.layers;

import java.util.Arrays;
import java.util.Random;

public class DenseLayer extends StackedLayer1D
{
	public static final int bias = 0;

	protected final float[][] weights;
	protected final float[] weightErrorSums;

	public DenseLayer(int size, Layer1D lastLayer) // TODO: Accept activation function
	{
		super(size, lastLayer);
		this.weightErrorSums = new float[lastLayer.getSize()];
		this.weights = new float[size][lastLayer.getSize()]; // [node in this layer][weight for incoming node]
		initializeWeights();
	}

	@Override
	public float[] calcOutputsRecursive()
	{
		applyWeights();
		applyActivationFunction();
		return outputs;
	}

	@Override
	public float[] calcWeightErrorSums()
	{
		for (int incomingNodeInd = 0; incomingNodeInd < lastLayer.getSize(); incomingNodeInd++)
		{
			weightErrorSums[incomingNodeInd] = 0;
			for (int nodeInd = 0; nodeInd < this.size; nodeInd++)
			{
				weightErrorSums[incomingNodeInd] += weights[nodeInd][incomingNodeInd] * errorTerms[nodeInd];
			}
		}

		return weightErrorSums;
	}

	@Override
	public void calcLayerErrorTerms(float[] nextLayerWeightErrorSums)
	{
		for (int nodeInd = 0; nodeInd < this.size; nodeInd++)
		{
			errorTerms[nodeInd] = outputs[nodeInd] * (1 - outputs[nodeInd]) * nextLayerWeightErrorSums[nodeInd];
		}
	}

	@Override
	public void calcLayerErrorDeriv(float[] lastLayerOutputs)
	{
		for (int nodeInd = 0; nodeInd < this.size; nodeInd++)
		{
			for (int weightInd = 0; weightInd < lastLayer.getSize(); weightInd++)
			{
				errorDerivs[nodeInd][weightInd] = errorTerms[nodeInd] * lastLayerOutputs[weightInd];
			}
		}
	}

	@Override
	public void updateWeights(float trainingRate)
	{
		for (int i = 0; i < weights.length; i++)
			for (int j = 0; j < weights[0].length; j++)
				weights[i][j] -= averageErrorDerivs[i][j] * trainingRate;
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
	 * https://keras.io/api/layers/initializers/
	 */
	private void initializeWeights()
	{
		double r = Math.sqrt(2.0 / (lastLayer.getSize() + this.size));
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
