package genetic.algorithm;

import gui.OrginalImage;
import gui.OutputImage;

import java.util.LinkedList;

public class Algorithm
{
	private int populationSize, eliteSize, numOfElements;
	private double mutationProbability, mutationSize;
	private OrginalImage orginalImage;
	LinkedList<OutputImage> outputs = new LinkedList<OutputImage>();
	
	public Algorithm(int populationSize, int eliteSize, int numOfElements,
			double mutationProbability, double mutationSize,
			OrginalImage orginalImage)
	{
		this.populationSize = populationSize;
		this.eliteSize = eliteSize;
		this.numOfElements = numOfElements;
		this.mutationProbability = mutationProbability;
		this.mutationSize = mutationSize;
		this.orginalImage = orginalImage;
		
		// Here create first random population
	}
	
	
}
