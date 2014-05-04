package gui;

import genetic.algorithm.Algorithm;
import genetic.algorithm.Individual;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.util.LinkedList;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 
 * @author Jakub Piotrowski
 * @version 1.0 Class to draw output image using genetic algorithm.
 */
public class OutputImage extends JPanel implements Runnable
{
	private static final long serialVersionUID = -6184846121081330233L;
	private int width, height, numOfElements;
	private LinkedList<Individual> elements = new LinkedList<Individual>(); // we
																			// draw
																			// all
																			// elements
																			// in
																			// this
																			// list
																			// on
																			// output
																			// image
	private Algorithm algorithm;
	private JLabel lab_numberOfGeneration, lab_numberOfFitness;
	private double fitness;
	/*********************************************************************************************************************************************************** */

	public void setWidth(int width)
	{
		this.width = width;
	}

	public void setFitness(double fitness)
	{
		this.fitness = fitness;
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

	public void setAlgorithm(Algorithm algorithm)
	{
		this.algorithm = algorithm;
	}

	public void setNumberOfGeneration(JLabel lab_numberOfGeneration)
	{
		this.lab_numberOfGeneration = lab_numberOfGeneration;
	}

	public void setNumberOfFitness(JLabel lab_numberOfFitness)
	{
		this.lab_numberOfFitness = lab_numberOfFitness;
	}

	/*********************************************************************************************************************************************************** */

	public int getWidth()
	{
		return width;
	}

	public int getHeight()
	{
		return height;
	}

	public int getNumOfElements()
	{
		return numOfElements;
	}

	public LinkedList<Individual> getElements()
	{
		return elements;
	}

	public double getFitness()
	{
		return fitness;
	}
	/*********************************************************************************************************************************************************** */

	/**
	 * Function convert output image into BufferedImage
	 * 
	 * @return BufferedImage of our output image
	 */
	public BufferedImage convertToImage()
	{
		BufferedImage bi = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g = bi.createGraphics();
		this.print(g);

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
		for (Individual i : elements)
		{
			g2.setColor(i.getColor());
			g2.fillOval(i.getPositionX(), i.getPositionY(), i.getRadius(),
					i.getRadius());
		}
	}

	/************************************************************************************************************************************************************
	 * 
	 * Constructors
	 */
	public OutputImage(int width, int height, int numOfElements,
			LinkedList<Individual> elements)
	{
		setWidth(width);
		setHeight(height);
		setNumOfElements(numOfElements);
		setElements(elements);
	}

	public OutputImage()
	{
	}

	@Override
	public void run()
	{
		for (int i = 0; i < 10; i++)
		{
			//setElements(algorithm.randomImage().getElements());
			OutputImage temporaryOutputImage = algorithm.evolution();
			setElements(temporaryOutputImage.getElements());
			lab_numberOfGeneration.setText(Integer.toString(i));
//			lab_numberOfFitness.setText((new DecimalFormat("#.##")
//					.format(temporaryOutputImage.getFitness()))
//					.substring(2)
//					+ " %");
			repaint();
		}

	}

}
