package mnistfromscratch.net.layers;

import java.util.Random;

public class DenseLayer extends StackedLayer1D
{
	public static final int bias = 1;

	protected final float[][] weights;
	protected final float[] outputs;

	public DenseLayer(int numNodes, Layer1D lastLayer) // TODO: Accept activation function
	{
		super(numNodes, lastLayer);
		this.outputs = new float[numNodes];
		this.weights = new float[numNodes][lastLayer.getSize()];
		initializeWeights();
	}

	@Override
	public float[] calcOutputs()
	{
		calcRawOutputs();
		applyActivation();
		return outputs;
	}

	protected void calcRawOutputs()
	{
		float[] inputVals = lastLayer.calcOutputs();

		for (int node = 0; node < this.size; node++)
		{
			outputs[node] = 0;
			for (int inputNode = 0; inputNode < lastLayer.getSize(); inputNode++)
			{
				outputs[node] += inputVals[inputNode] * weights[node][inputNode];
			}
		}
	}

	protected void applyActivation()
	{
		for (int node = 0; node < this.size; node++)
		{
			outputs[node] = sigmoid(outputs[node]) + bias;
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
		double r = 4 * Math.sqrt(6 / (lastLayer.getSize() + this.size));
		Random rand = new Random();
		for (int node = 0; node < this.size; node++)
		{
			for (int weight = 0; weight < weights[node].length; weight++)
			{
				weights[node][weight] = (float) ((2 * rand.nextGaussian() - 1) * r);
			}
		}
	}
}
