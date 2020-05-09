package mnistfromscratch.net.layers;

public class OutputLayer extends DenseLayer
{

	public OutputLayer(int classes, Layer1D lastLayer)
	{
		super(classes, lastLayer);
	}

	public void calcOutputErrorTerms(float[] expected)
	{
		for (int i = 0; i < this.size; i++)
		{
			this.errorTerms[i] = this.outputs[i] - expected[i];
		}
	}

	@Override
	public void calcLayerErrorTerms(float[] nextLayerWeightErrorSums)
	{
		throw new UnsupportedOperationException("Use calcOutputError on OutputLayer"); // TODO: Separate DenseLayer and OutputLayer
	}

	/*
	 * Softmax Function
	 * https://cs231n.github.io/linear-classify/#softmax
	 * Could cache the results of Math.exp but probably just as fast to calculate it twice
	 */
	/*@Override
	protected void applyActivationFunction()
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
	}*/

	/*
	 * Pass-through
	 */
	@Override
	protected void applyActivationFunction()
	{
		return;
	}
}
