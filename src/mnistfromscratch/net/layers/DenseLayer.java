package mnistfromscratch.net.layers;

public class DenseLayer extends StackedLayer1D
{
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
		return calcOutputsSigmoid();
	}

	private float[] calcOutputsSigmoid()
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

		return outputs;
	}
}
