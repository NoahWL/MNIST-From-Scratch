package mnistfromscratch;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.util.Arrays;

import mnistfromscratch.net.layers.DenseLayer;
import mnistfromscratch.net.layers.InputLayer;
import mnistfromscratch.net.layers.OutputLayer;

public class Main
{
	public Main()
	{
		float[][] trainLabels = Preprocessor.oneHot(readIDXLabels("./dataset/train-labels.idx1-ubyte"), 9);
		float[][] trainImages = Preprocessor.preprocessImages(readIDXImages("./dataset/train-images.idx3-ubyte"));

		InputLayer inputLayer = new InputLayer(trainImages[0].length);
		DenseLayer hidden1 = new DenseLayer(trainImages[0].length, inputLayer);
		OutputLayer outputLayer = new OutputLayer(10, hidden1);

		inputLayer.setInputData(trainImages[0]);
		System.out.println("Input: " + Arrays.toString(trainImages[0]));
		float[] output = outputLayer.calcOutputsRecursive();
		System.out.println("Output: " + Arrays.toString(output));
	}

	public byte[][][] readIDXImages(String filePath)
	{
		ByteBuffer buf;
		try
		{
			byte[] bytes = Files.readAllBytes(new File(filePath).toPath());
			buf = ByteBuffer.wrap(bytes);
		} catch (IOException e)
		{
			throw new RuntimeException(e);
		}
		int imageCount = buf.getInt(4);
		int imageWidth = buf.getInt(8);
		int imageHeight = buf.getInt(12);
		byte[][][] imageBytes = new byte[imageCount][imageHeight][imageWidth];

		for (int imageNum = 0; imageNum < imageCount; imageNum++)
		{
			for (int rowIndex = 0; rowIndex < imageHeight; rowIndex++)
			{
				int flatRowOffset = (imageNum * imageHeight * imageWidth) + (rowIndex * imageWidth) + 16; // Image data starts 16 bytes into file
				buf.get(flatRowOffset, imageBytes[imageNum][rowIndex], 0, imageWidth);
				// System.out.println(imageNum + " " + Arrays.toString(imageBytes[imageNum][rowIndex]));
			}
			// System.out.println();
		}

		return imageBytes;
	}

	public byte[] readIDXLabels(String filePath)
	{
		ByteBuffer buf;
		try
		{
			byte[] bytes = Files.readAllBytes(new File(filePath).toPath());
			buf = ByteBuffer.wrap(bytes);
		} catch (IOException e)
		{
			throw new RuntimeException(e);
		}
		int labelCount = buf.getInt(4);
		byte[] labelBytes = new byte[labelCount];
		buf.get(8, labelBytes, 0, labelCount); // Label data starts 8 bytes into file

		return labelBytes;
	}

	public static void main(String[] args)
	{
		new Main();
	}
}
