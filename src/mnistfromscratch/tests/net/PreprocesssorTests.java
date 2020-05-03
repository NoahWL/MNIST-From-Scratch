package mnistfromscratch.tests.net;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import mnistfromscratch.Preprocessor;

class PreprocesssorTests
{

	@Test
	void testUnsignedByteToFloat()
	{
		float[] expected = new float[256];
		for (int i = 0; i < expected.length; i++)
			expected[i] = i;
		// System.out.println(Arrays.toString(expected));

		float[] convertedBytes = new float[256];
		for (int i = 0; i < convertedBytes.length; i++)
			convertedBytes[i] = Preprocessor.unsignedByteToFloat((byte) i);
		// System.out.println(Arrays.toString(convertedBytes));
		assertArrayEquals(expected, convertedBytes);
	}

	@Test
	void testShowNormalize()
	{
		byte[] testData = new byte[255];
		for (int i = 0; i < testData.length; i++)
			testData[i] = (byte) i;
		System.out.println(Arrays.toString(testData));
	}

}
