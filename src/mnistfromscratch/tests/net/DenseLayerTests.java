package mnistfromscratch.tests.net;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import mnistfromscratch.Preprocessor;
import mnistfromscratch.net.layers.InputLayer;

class DenseLayerTests
{
	@BeforeAll
	static void setUp() throws Exception
	{
	}

	@Test
	void showWeights()
	{
		System.out.println("Test");
		byte[] inputs = new byte[15];
		for (byte i = 0; i < inputs.length; i++)
			inputs[i] = (byte) (i - (inputs.length / 2));
		System.out.println("Inputs: " + Arrays.toString(inputs));
		float[] inputsNorm = Preprocessor.normalize(inputs);
		System.out.println("Normed inputs: " + Arrays.toString(inputsNorm));

		InputLayer input = new InputLayer(15);
		input.setInputData(inputsNorm);
	}

}
