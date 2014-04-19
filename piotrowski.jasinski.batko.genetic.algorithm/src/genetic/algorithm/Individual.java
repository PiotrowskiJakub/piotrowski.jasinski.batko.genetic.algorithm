package genetic.algorithm;

import java.awt.Color;

/**
 * 
 * @author Jakub Piotrowski
 * @version 1.0
 * Class represents one element, in this case circle
 */
public class Individual
{
	private int width, height;	// width and height should be lower than width and height original image
	private int positionX, positionY;	// also should be somewhere on a image (minus width and height)
	private Color color; // set as RGB value, 0-255
	
	public Individual(int width, int height, int positionX, int positionY, Color color)
	{
		this.width = width;
		this.height = height;
		this.positionX = positionX;
		this.positionY = positionY;
		this.color = color;
	}

	public int getWidth()
	{
		return width;
	}

	public int getHeight()
	{
		return height;
	}

	public int getPositionX()
	{
		return positionX;
	}

	public int getPositionY()
	{
		return positionY;
	}

	public Color getColor()
	{
		return color;
	}
}
