package mnistfromscratch.net.layers;

import java.util.Arrays;

public abstract class StackedLayer1D extends Layer1D
{
	protected final Layer1D lastLayer;
	protected final float[] errorTerms;
	protected final float[][] errorDerivs;
	protected final float[][] averageErrorDerivs;

	public StackedLayer1D(final int size, Layer1D lastLayer)
	{
		super(size);
		this.lastLayer = lastLayer;
		this.errorTerms = new float[size];
		this.errorDerivs = new float[size][lastLayer.getSize()];
		this.averageErrorDerivs = new float[size][lastLayer.getSize()];
	}

	public void resetAverageErrorDerivs()
	{
		for (int i = 0; i < averageErrorDerivs.length; i++)
			Arrays.fill(averageErrorDerivs[i], 0);
	}

	public void updateAverageErrorDerivs()
	{
		for (int i = 0; i < averageErrorDerivs.length; i++)
			for (int j = 0; j < averageErrorDerivs[0].length; j++)
				averageErrorDerivs[i][j] += errorDerivs[i][j];
	}

	public void divideAverageErrorDerivs(int N)
	{
		for (int i = 0; i < averageErrorDerivs.length; i++)
			for (int j = 0; j < averageErrorDerivs[0].length; j++)
				averageErrorDerivs[i][j] /= N;
	}

	public abstract float[] calcWeightErrorSums();

	public abstract void calcLayerErrorTerms(float[] nextLayerWeightErrorSums);

	public abstract void calcLayerErrorDeriv(float[] lastLayerOutputs);

	public abstract void updateWeights(float trainingRate);
}
