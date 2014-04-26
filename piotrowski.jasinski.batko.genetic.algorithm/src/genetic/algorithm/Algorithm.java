package genetic.algorithm;

import gui.OriginalImage;
import gui.OutputImage;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Random;

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
		
		for(int i = 0; i < populationSize; i++){
			outputs.add(geneticImage((numOfElements)));
		}
		
		
		
	}
	
	private OutputImage geneticImage(int numOfElements){
		LinkedList<Individual> elements = new LinkedList<Individual>();
		Random rand = new Random();
		float r = rand.nextFloat();
		float g = rand.nextFloat();
		float b = rand.nextFloat();
		float a = rand.nextFloat();
		Color randomColor = new Color(r, g, b);
		int width = originalImage.getWidth();
		int height = originalImage.getHeight();
		elements.add(new Individual(rand.nextInt()*3, rand.nextInt()*width, rand.nextInt()*height, randomColor));
		BufferedImage member = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		return new OutputImage(width, height, numOfElements, member, elements);
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
	
	private OutputImage mutation(OutputImage pattern){
		
		LinkedList<Individual> _elements = new LinkedList<Individual>();
		int width = pattern.getWidth();
		int height = pattern.getHeight();
		
		for(Individual pat : pattern.getElements()){
			int _positionX = pat.getPositionX();
			int _positionY = pat.getPositionY();
			int _radius = pat.getRadius();
			int _red = pat.getColor().getRed();
			int _green = pat.getColor().getGreen();
			int _blue = pat.getColor().getBlue();
			
			mutationSize = Math.random();
			double noChangesProbability = 1.0 - mutationProbability;
			double parameterMutationProbability = mutationProbability / 3.0;
			
			if (mutationSize < (noChangesProbability + parameterMutationProbability)){
				_positionX += gaussRandom()*width;
				if (_positionX > width) _positionX = width;
				else if (_positionX < 0) _positionX = 0;
				
				_positionY += gaussRandom()*height;
				if (_positionY > height) _positionY = height;
				else if (_positionY < 0) _positionY = 0;
			} else if (mutationSize < noChangesProbability + 2.0 * parameterMutationProbability){
				_radius += gaussRandom()*((width+height)/4.0);
				if (_radius > ((width+height)/2.0)) _radius = (int)((width+height)/2.0);
				else if (_radius < 0) _radius = 0;
			} else {	
				_red += gaussRandom()*255;
				if (_red > 255) _red = 255;
				else if (_red < 0) _red = 0;
			
				_green += gaussRandom()*255;
				if (_green > 255) _green = 255;
				else if (_green < 0) _green = 0;
				
				_blue += gaussRandom()*255;
				if (_blue > 255) _blue = 255;
				else if (_blue < 0) _blue = 0;
				
			}
			
			_elements.add(pat);
		}
		
		
		return new OutputImage(width, height, pattern.getNumOfElements(), pattern.getImage(), _elements);
		
		
	}
	
	private double gaussRandom(){
			double x = Math.random();
			double y = Math.random();
			
			double r = 0.003 * mutationSize * Math.sqrt(-2.0 * Math.log(x)) * Math.cos(2.0 * Math.PI * y);
			if (r<-1.0) 
				r=0;
			if (r>1.0) 
				r=0;
			return r;		
	}
	
	public void evolution(){
		
	}
	
	
}