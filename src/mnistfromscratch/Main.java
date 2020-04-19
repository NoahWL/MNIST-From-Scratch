package mnistfromscratch;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.util.Arrays;

public class Main
{
	public Main()
	{
		readIdxImages("./dataset/train-images.idx3-ubyte");
	}

	public void readIdxImages(String filePath)
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
				int flatRowOffset = imageNum * imageHeight * imageWidth + rowIndex * imageWidth + 16;
				buf.get(flatRowOffset, imageBytes[imageNum][rowIndex], 0, imageWidth);
				//System.out.println(imageNum + " " + Arrays.toString(imageBytes[imageNum][rowIndex]));
			}
			//System.out.println();
		}
		
	}

	public static void main(String[] args)
	{
		Main main = new Main();
	}
}
