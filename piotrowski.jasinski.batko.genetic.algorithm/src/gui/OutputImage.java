package gui;

import genetic.algorithm.Algorithm;
import genetic.algorithm.Individual;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.util.Collections;
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
	private int width, height, numOfElements, counter;
	private LinkedList<Individual> elements = new LinkedList<Individual>(); // list of elements that we draw on an image
	private Algorithm algorithm;	// evolution algorithm
	private JLabel lab_numberOfGeneration, lab_numberOfFitness;	// references to JLabels that we update after each generation
	private double fitness;	// this shows how this image is similar to original one
	private boolean evolutionCondition;	// boolean condition to check evolution loop
	
	// CONSTRUCTORS ***********************************************************************************************************
	
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
	
	// SETTERS *************************************************************************************************************

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

	public void setEvolutionCondition(boolean evolutionCondition)
	{
		this.evolutionCondition = evolutionCondition;
	}

	public void setCounter(int counter)
	{
		this.counter = counter;
	}

	// GETTERS *****************************************************************************************************************

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
	
	// ********************************************************************************************************************

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
		
		Collections.sort(elements);
		
		for (Individual i : elements)
		{
			g2.setColor(i.getColor());
			g2.fillOval(i.getPositionX(), i.getPositionY(), i.getRadius(),
					i.getRadius());
		}
	}

	/**
	 * This function is infinite loop with genetic algorithm that generates new generations of images
	 * This function run when we make a thread with this class as implementation of runnable interface
	 */
	@Override
	public void run()
	{
		DecimalFormat decimalFormat = new DecimalFormat("#.##");
		while(evolutionCondition)
		{
			OutputImage temporaryOutputImage = algorithm.evolution();
			setElements(temporaryOutputImage.getElements());
			lab_numberOfGeneration.setText(Integer.toString(counter));
			String fitnessString = decimalFormat.format(temporaryOutputImage.getFitness());
			if(fitnessString.length() == 3)
				fitnessString = fitnessString + "0";
			lab_numberOfFitness.setText(fitnessString.substring(2) + " %");
			counter++;
			repaint();
		}

	}

}
