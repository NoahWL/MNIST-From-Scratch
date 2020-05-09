package mnistfromscratch.net.layers;

public abstract class Layer1D
{
	protected final int size;
	protected final float[] outputs;

	public Layer1D(final int size)
	{
		this.outputs = new float[size];
		
		this.size = size;
	}

	public int getSize()
	{
		return this.size;
	}
	
	public float[] getOutputs()
	{
		return this.outputs;
	}

	public abstract float[] calcOutputsRecursive();
}
