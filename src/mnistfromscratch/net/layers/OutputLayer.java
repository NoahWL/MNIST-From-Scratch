package mnistfromscratch.net.layers;

public class OutputLayer extends StackedLayer1D
{

	public OutputLayer(int size, Layer1D lastLayer)
	{
		super(size, lastLayer);
	}

	@Override
	public float[] calcOutputs()
	{
		return calcOutputsSoftmax();
	}

	private float[] calcOutputsSoftmax()
	{
		return null;
	}
}
