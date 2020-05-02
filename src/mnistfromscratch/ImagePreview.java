package mnistfromscratch;

import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ImagePreview
{
	private final JFrame frame;
	private final JLabel imageLabel;

	public ImagePreview()
	{
		frame = new JFrame("Preview");
		imageLabel = new JLabel();
		frame.add(imageLabel);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void preview(byte[][] imageByteArray)
	{
		BufferedImage bufferedImage = new BufferedImage(28, 28,
		        BufferedImage.TYPE_BYTE_GRAY);

		bufferedImage.getRaster().setSample(5, 5, 0, -100);

		imageLabel.setIcon(new ImageIcon(bufferedImage));
		frame.pack();
	}
}
