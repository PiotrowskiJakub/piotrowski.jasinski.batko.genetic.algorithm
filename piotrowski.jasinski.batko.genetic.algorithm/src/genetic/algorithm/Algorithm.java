package genetic.algorithm;

import gui.OriginalImage;
import gui.OutputImage;

import java.util.LinkedList;

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
	
	
}
