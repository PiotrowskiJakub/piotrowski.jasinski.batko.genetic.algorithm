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
	private int radius;	// radius should be lower than width and height original image
	private int positionX, positionY;	// also should be somewhere on a image (minus width and height)
	private Color color; // set as RGB value, 0-255
	
	public Individual(int radius, int positionX, int positionY, Color color)
	{
		this.radius = radius;
		this.positionX = positionX;
		this.positionY = positionY;
		this.color = color;
	}

	public int getRadius()
	{
		return radius;
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
