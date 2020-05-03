package mnistfromscratch.net.layers;

public class OutputLayer extends DenseLayer
{

	public OutputLayer(int size, Layer1D lastLayer) // size = classes (for classification tasks)
	{
		super(size, lastLayer);
	}

	/*
	 * Softmax Function
	 * https://cs231n.github.io/linear-classify/#softmax
	 * Could cache the results of Math.exp but probably just as fast to calculate it twice
	 */
	@Override
	protected void applyActivation()
	{
		float outputExpSum = 0;
		for (int node = 0; node < this.size; node++)
		{
			outputExpSum += Math.exp(outputs[node]);
		}

		for (int node = 0; node < this.size; node++)
		{
			outputs[node] = (float) Math.exp(outputs[node]) / outputExpSum;
		}
	}

}
