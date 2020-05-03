package mnistfromscratch.net.layers;

public class DenseLayer extends StackedLayer1D
{
	public static final int bias = 1;
	
	protected final float[][] weights;
	protected final float[] outputs;

	public DenseLayer(int numNodes, Layer1D lastLayer) // TODO: Accept activation function
	{
		super(numNodes, lastLayer);
		this.weights = new float[numNodes][lastLayer.getSize()]; // TODO: Initialize randomly
		this.outputs = new float[numNodes];
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
}
