package genetic.algorithm;

import gui.OriginalImage;
import gui.OutputImage;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.lang.Math;

/**
 * 
 * @author Jakub Piotrowski
 * @version 1.0
 * This class is our genetic algorithm using to generate image similar to original image
 */
public class Algorithm
{
	private int populationSize, eliteSize, numOfElements;
	private double mutationProbability, mutationSize;
	private OriginalImage originalImage;
	LinkedList<OutputImage> outputs = new LinkedList<OutputImage>();
	
	public Algorithm(int populationSize, int eliteSize, int numOfElements,
			double mutationProbability, double mutationSize,
			OriginalImage originalImage)
	{
		this.populationSize = populationSize;
		this.eliteSize = eliteSize;
		this.numOfElements = numOfElements;
		this.mutationProbability = mutationProbability;
		this.mutationSize = mutationSize;
		this.originalImage = originalImage;
		
		// Here create first random population
	}
	
	private double compareImage(BufferedImage outputImage){
		int width, height;
		double d, dMax;
		
		if(outputImage == null)
			throw new NullPointerException("outputImage is NULL !!!");
		
		width = outputImage.getWidth();
		height = outputImage.getHeight();
		
		if(width != originalImage.getWidth() || height != originalImage.getHeight())
			throw new IllegalArgumentException("Dimensions of original and output image are different !!!");
			
		d = 0; //d is sum of the differences between all pixels in output and original image
		dMax = width * height * Math.sqrt(255*255*3); //dMax is maximum sum of the differences between these images
		
		for(int x = 0; x < width; x++) //Compare every pixel
			for(int y = 0; y < height; y++)
				d += compareColor(new Color(outputImage.getRGB(x,y)),	new Color(originalImage.getImage().getRGB(x, y)));
		
		if(d > dMax)
			throw new ArithmeticException("d is greater than dMax !!!");
		
		return d / dMax;
	}
	
	//We're using this method to compare 2 integer pixels by split both to 3 colors: Red, Blue and Green
	private double compareColor(Color color1, Color color2){
		if(color1 == null || color2 == null)
			throw new NullPointerException("Color1 or Color2 is NULL !!!");
		
		double dR = color1.getRed()-color2.getRed();
		double dG = color1.getGreen()-color2.getGreen();
		double dB = color1.getBlue()-color2.getBlue();
		
		return Math.sqrt( dR*dR + dG*dG + dB*dB );
	}
	
	private OutputImage hybridization(OutputImage mother, OutputImage father){
		if(mother == null || father == null)
			throw new NullPointerException("Mother or father is NULL");
		if(mother.getWidth() != father.getWidth() || mother.getHeight() != father.getHeight())
			throw new ArithmeticException("Dimensions of mother and father are different !!!");	
		
		int width = mother.getWidth(); //Initialization field necessary to create child image
		int height = mother.getHeight();
		BufferedImage child = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		LinkedList<Individual> elements = new LinkedList<Individual>();
		int numOfElements;
		int random;
		
		//Does the numberOfElements is same for all OutputImages ???????
		//If so, delete these 4 lines below -->
		if(mother.getNumOfElements() < father.getNumOfElements()) 
			numOfElements = mother.getNumOfElements();
		else
			numOfElements = father.getNumOfElements();
		// --<
		
		for(int i = 0; i < numOfElements; i++){
			random = (int)(Math.random() * (101));
			try{
				if(random < 50)
					elements.add(mother.getElements().get(i));
				else
					elements.add(father.getElements().get(i));
			}catch (ArrayIndexOutOfBoundsException e) {
				System.out.println("Can't refer to element !!!");
			}
		}
		
		return new OutputImage(width, height, numOfElements, child, elements);
	}
	
	
}
