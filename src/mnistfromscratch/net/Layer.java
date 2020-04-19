package mnistfromscratch.net;

import java.util.Arrays;

public class Layer
{
	private final float layerArr[];
	private final int dimensions[]; // shape

	public Layer(int... dimensions)
	{
		this.dimensions = Arrays.copyOf(dimensions, dimensions.length);
		int flattenedLength = 1; // TODO: check input for invalid dimension sizes
		for (int i = 0; i < this.dimensions.length; i++)
			flattenedLength *= this.dimensions[i];
		this.layerArr = new float[flattenedLength];
	}

	public void set(int... indicies)
	{
	}
}
