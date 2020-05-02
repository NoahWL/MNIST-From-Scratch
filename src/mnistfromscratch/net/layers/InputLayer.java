package mnistfromscratch.net.layers;

public class InputLayer extends Layer1D
{
	private float[] inputData;

	public InputLayer(int inputDataSize)
	{
		super(inputDataSize);
	}

	public void setInputData(float[] inputData)
	{
		this.inputData = inputData;
	}

	@Override
	public float[] calcOutputs()
	{
		return this.inputData;
	}
}
