package gui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * 
 * @author Jakub Piotrowski
 * @version 1.0
 * Class to display original image.
 */
public class OriginalImage extends JPanel
{
	private static final long serialVersionUID = 3006769532505931833L;
	private BufferedImage image;	// original image as BufferedImage
	private int width, height;
	
	public void drawImage(File file)
	{
		try
		{
			image = ImageIO.read(file);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		
		width = image.getWidth();
		height = image.getHeight();
		
		paintComponent(this.getGraphics());
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(image, 0, 0, null);
	}

	public BufferedImage getImage()
	{
		return image;
	}

	public int getWidth()
	{
		return width;
	}

	public int getHeight()
	{
		return height;
	}
}
