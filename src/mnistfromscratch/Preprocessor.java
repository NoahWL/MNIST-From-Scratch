package mnistfromscratch;

/**
 * 
 * @author Noah
 *
 */
public class Preprocessor
{
	public static float[][] preprocessImages(byte[][][] imageBytes)
	{
		byte[][] imageBytesFlat = convertImagesToFlat(imageBytes);
		float[][] imageBytesNormalized = normalizeImages(imageBytesFlat);

		return imageBytesNormalized;
	}

	public static float[][] oneHot(byte[] labels, int maxDataSize)
	{
		float[][] oneHotted = new float[labels.length][maxDataSize + 1];
		for (int i = 0; i < labels.length; i++)
		{
			int val = labels[i];
			if (val > maxDataSize)
				throw new RuntimeException("OneHot Failed: Label data exceeds specified maximum size.  (Got: " + val
				        + " Max: " + maxDataSize);
			oneHotted[i][val] = 1;
		}

		return oneHotted;
	}

	private static byte[][] convertImagesToFlat(byte[][][] imageBytes)
	{
		int imageWidth = imageBytes[0][0].length;
		int imageHeight = imageBytes[0].length;
		byte[][] imagesFlat = new byte[imageBytes.length][imageWidth * imageHeight];
		for (int imageInd = 0; imageInd < imageBytes.length; imageInd++)
		{
			int rowOffset = 0;
			for (int row = 0; row < imageHeight; row++)
			{
				System.arraycopy(imageBytes[imageInd][row], 0, imagesFlat[imageInd], rowOffset, imageWidth);
				rowOffset += imageWidth;
			}
		}

		return imagesFlat;
	}

	private static float[][] normalizeImages(byte[][] imageBytesFlat)
	{
		float[][] normalized = new float[imageBytesFlat.length][];

		for (int imageInd = 0; imageInd < imageBytesFlat.length; imageInd++)
		{
			normalized[imageInd] = normalize(imageBytesFlat[imageInd]);
		}

		return normalized;
	}

	public static float[] normalize(byte[] arr)
	{
		float[] normalized = new float[arr.length];
		for (int i = 0; i < arr.length; i++)
		{
			float data = unsignedByteToFloat(arr[i]);
			normalized[i] = data / 255f;
		}

		return normalized;
	}

	public static float unsignedByteToFloat(byte signedByte)
	{
		float val = signedByte;
		if (val < 0)
			val = val + 256;
		return val;

	}
}
