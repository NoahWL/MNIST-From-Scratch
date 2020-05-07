package mnistfromscratch.net.layers;

public class InputLayer extends Layer1D
{

	public InputLayer(int inputDataSize)
	{
		super(inputDataSize);
	}

	public void setInputData(float[] inputData)
	{
		System.arraycopy(inputData, 0, this.outputs, 0, size);
	}

	@Override
	public float[] calcOutputsRecursive()
	{
		return this.outputs;
	}
}
