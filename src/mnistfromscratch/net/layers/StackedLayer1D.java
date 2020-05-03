package mnistfromscratch.net.layers;

public abstract class StackedLayer1D extends Layer1D
{
	protected final Layer1D lastLayer;

	public StackedLayer1D(final int size, Layer1D lastLayer)
	{
		super(size);
		this.lastLayer = lastLayer;
	}
}