package gui;

import genetic.algorithm.Individual;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import javax.swing.JPanel;

/**
 * 
 * @author Jakub Piotrowski
 * @version 1.0
 * Class to draw output image using genetic algorithm.
 */
public class OutputImage extends JPanel
{
	private static final long serialVersionUID = -6184846121081330233L;
	private int width, height, numOfElements;
	private LinkedList<Individual> elements = new LinkedList<Individual>(); // we draw all elements in this list on output image
	
	public void setWidth(int width)
	{
		this.width = width;
	}

	public void setHeight(int height)
	{
		this.height = height;
	}

	public void setNumOfElements(int numOfElements)
	{
		this.numOfElements = numOfElements;
	}

	public void setElements(LinkedList<Individual> elements)
	{
		this.elements = elements;
	}

	/**
	 * Function convert output image into BufferedImage
	 * @return	BufferedImage of our output image
	 */
	public BufferedImage convertToImage()
	{
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = bi.createGraphics();
		this.paint(g);
		
		return bi;
	}
	
	@Override
	/**
	 * Function paints all elements on JPanel
	 */
	public void paint(Graphics g)
	{
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		for(Individual i : elements)
		{
			g2.setColor(i.getColor());
			g2.fillOval(i.getPositionX(), i.getPositionY(), i.getRadius(), i.getRadius());
		}
	}

	
}
