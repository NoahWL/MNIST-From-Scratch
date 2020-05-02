package mnistfromscratch.net.layers;

public abstract class Layer1D
{
	protected final int size;

	public Layer1D(final int size)
	{
		this.size = size;
	}

	public int getSize()
	{
		return this.size;
	}

	public abstract float[] calcOutputs();
}
