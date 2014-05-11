package genetic.algorithm;

import gui.OriginalImage;
import gui.OutputImage;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.security.SecureRandom;
import java.util.LinkedList;
import java.util.Random;

/**
 * 
 * @author Jakub Piotrowski, Przemysław Jasiński, Sebastian Batko
 * @version 1.0 
 * This class is our genetic algorithm using to generate image similar to original image
 */
public class Algorithm
{
	private int populationSize, eliteSize, numOfElements;
	private double mutationProbability;
	private OriginalImage originalImage;
	LinkedList<OutputImage> outputs = new LinkedList<OutputImage>();	// in the list we have all OutputImages (one population)

	public Algorithm(int populationSize, int eliteSize, int numOfElements,
			double mutationProbability, OriginalImage originalImage)
	{
		this.populationSize = populationSize;
		this.eliteSize = eliteSize;
		this.numOfElements = numOfElements;
		this.mutationProbability = mutationProbability;
		this.originalImage = originalImage;

		// Here we create first random population
		for (int i = 0; i < populationSize; i++)
		{
			outputs.add(geneticImage());
		}

	}

	/**
	 * A convenience method used in the constructor.
	 * @return new random generated OutputImage
	 */
	private OutputImage geneticImage()
	{
		LinkedList<Individual> elements = new LinkedList<Individual>();
		SecureRandom rand = new SecureRandom();
		int width = originalImage.getWidth();
		int height = originalImage.getHeight();
		for (int i = 0; i < numOfElements; i++)
		{
			float r = rand.nextFloat();
			float g = rand.nextFloat();
			float b = rand.nextFloat();
			float a = rand.nextFloat();
			Color randomColor = new Color(r, g, b, a);
			elements.add(new Individual(rand.nextInt((int) (Math.min(width, height)/4)) + 1, rand.nextInt(width),
					rand.nextInt(height), randomColor));
		}

		return new OutputImage(width, height, numOfElements, elements);
	}

	/**
	 * Method used to compare image with the orginalImage
	 * @param outputImage to compare
	 * @return	Fitness as double value.
	 */
	public double compareImage(BufferedImage outputImage)
	{
		int width, height;
		double d, dMax;

		if (outputImage == null)
			throw new NullPointerException("outputImage is NULL !!!");

		width = outputImage.getWidth();
		height = outputImage.getHeight();

		if (width != originalImage.getWidth() || height != originalImage.getHeight())
			throw new IllegalArgumentException("Dimensions of original and output image are different !!!");

		d = 0; // d is sum of the differences between all pixels in output and original image

		dMax = width * height * Math.sqrt(3); // dMax is maximum sum of the differences between these images

		for (int x = 0; x < width; x++)
			// Compare every pixel
			for (int y = 0; y < height; y++)
				d += compareColor(new Color(outputImage.getRGB(x, y)), new Color(originalImage.getImage().getRGB(x, y)));

		if (d > dMax)
			throw new ArithmeticException("d is greater than dMax !!!");

		return (dMax - d) / dMax;
	}

	// We're using this method to compare 2 integer pixels by split both to 3
	// function has been changed to compare HSV
	private double compareColor(Color color1, Color color2)
	{
		if (color1 == null || color2 == null)
			throw new NullPointerException("Color1 or Color2 is NULL !!!");

		float[] hsv1 = new float[3];
		float[] hsv2 = new float[3];
		Color.RGBtoHSB(color1.getRed(), color1.getGreen(), color1.getBlue(), hsv1);
		Color.RGBtoHSB(color2.getRed(), color2.getGreen(), color2.getBlue(), hsv2);
		float dH = (hsv1[0] - hsv2[0]) * (hsv1[0] - hsv2[0]);
		float dS = (hsv1[1] - hsv2[1]) * (hsv1[1] - hsv2[1]);
		float dV = (hsv1[2] - hsv2[2]) * (hsv1[2] - hsv2[2]);
		return Math.sqrt(dH + dS + dV);
	}

	private LinkedList<OutputImage> crossover(OutputImage mother, OutputImage father)
	{
		if (mother == null || father == null)
			throw new NullPointerException("Mother or father is NULL");
		if (mother.getWidth() != father.getWidth() || mother.getHeight() != father.getHeight())
			throw new ArithmeticException("Dimensions of mother and father are different !!!");

		SecureRandom rand = new SecureRandom();
		int random;
		int width = mother.getWidth();
		int height = mother.getHeight();
		LinkedList<Individual> child_list1 = new LinkedList<Individual>();
		LinkedList<Individual> child_list2 = new LinkedList<Individual>();
		LinkedList<OutputImage> children_image = new LinkedList<OutputImage>();
		OutputImage child_image1, child_image2;

		for (int i = 0; i < numOfElements; i++)
		{
			random = rand.nextInt(101);
			try
			{
				if (random < 50)
				{
					child_list1.add(mother.getElements().get(i));
					child_list2.add(father.getElements().get(i));
				} else
				{
					child_list1.add(father.getElements().get(i));
					child_list2.add(mother.getElements().get(i));
				}

			} 
			catch (IndexOutOfBoundsException e)
			{
				System.out.println("Can't refer to element !!!");
			}
		}

		child_image1 = new OutputImage(width, height, numOfElements, child_list1);
		child_image2 = new OutputImage(width, height, numOfElements, child_list2);
		children_image.add(child_image1);
		children_image.add(child_image2);
		
		return children_image;
	}

	private LinkedList<OutputImage> mutation(LinkedList<OutputImage> pattern_list)
	{

		LinkedList<Individual> _elements = new LinkedList<Individual>();
		int width = pattern_list.get(0).getWidth();
		int height = pattern_list.get(0).getHeight();

		LinkedList<OutputImage> finalChild = new LinkedList<OutputImage>();

		for (OutputImage singleImage : pattern_list)
		{
			for (Individual pat : singleImage.getElements())
			{
				int _positionX = pat.getPositionX();
				int _positionY = pat.getPositionY();
				int _radius = pat.getRadius();
				int _red = pat.getColor().getRed();
				int _green = pat.getColor().getGreen();
				int _blue = pat.getColor().getBlue();

				double mutationSize = Math.random();
				double noChangesProbability = 1.0 - mutationProbability;
				double parameterMutationProbability = mutationProbability / 3.0;

				if (mutationSize < (noChangesProbability + parameterMutationProbability))
				{
					_positionX += gaussRandom(mutationSize) * width;
					if (_positionX > width)
						_positionX = width;
					else if (_positionX < 0)
						_positionX = 0;

					_positionY += gaussRandom(mutationSize) * height;
					if (_positionY > height)
						_positionY = height;
					else if (_positionY < 0)
						_positionY = 0;
				} 
				else if (mutationSize < noChangesProbability + 2.0 * parameterMutationProbability)
				{
					_radius += gaussRandom(mutationSize) * ((width + height) / 4.0);
					if (_radius > ((width + height) / 2.0))
						_radius = (int) ((width + height) / 2.0);
					else if (_radius < 0)
						_radius = 0;
				} 
				else
				{
					_red += gaussRandom(mutationSize) * 255;
					if (_red > 255)
						_red = 255;
					else if (_red < 0)
						_red = 0;

					_green += gaussRandom(mutationSize) * 255;
					if (_green > 255)
						_green = 255;
					else if (_green < 0)
						_green = 0;

					_blue += gaussRandom(mutationSize) * 255;
					if (_blue > 255)
						_blue = 255;
					else if (_blue < 0)
						_blue = 0;

				}

				Individual newElements = new Individual(_radius, _positionX, _positionY, new Color(_red, _green, _blue));
				_elements.add(newElements);
			}

			finalChild.add(new OutputImage(width, height, singleImage.getNumOfElements(), _elements));
		}

		return finalChild;

	}

	private double gaussRandom(double mutationSize)
	{
		double x = Math.random();
		double y = Math.random();

		double r = 0.003 * mutationSize * Math.sqrt(-2.0 * Math.log(x))
				* Math.cos(2.0 * Math.PI * y);
		if (r < -1.0)
			r = 0;
		if (r > 1.0)
			r = 0;
		
		return r;
	}

	public OutputImage evolution()
	{
		LinkedList<OutputImage> elites = new LinkedList<OutputImage>();
		int nowBestIndex = 0; // tutaj najlepszy obraz danej populacji

		for (int i = 0; i < outputs.size(); i++)
		{
			outputs.get(i).setFitness(
					compareImage(outputs.get(i).convertToImage()));
		}

		for (int i = 0; i < eliteSize; i++)
		{
			double currentBest = outputs.get(0).getFitness();
			double actualCheck;
			int currentBestIndex = 0;
			for (int j = 1; j < outputs.size(); j++)
			{
				actualCheck = outputs.get(j).getFitness();
				if (currentBest < actualCheck)
				{
					currentBest = actualCheck;
					currentBestIndex = j;
				}
			}
			// add to elite
			elites.add(outputs.get(currentBestIndex));
			outputs.remove(currentBestIndex);
		}

		double actualBest = elites.get(nowBestIndex).getFitness();
		for (int i = 0; i < elites.size(); i++)
		{
			if (actualBest < elites.get(i).getFitness())
			{
				nowBestIndex = i;
				actualBest = elites.get(i).getFitness();
			}
		}

		// create next generation
		outputs.clear();
		int i = 0;
		LinkedList<OutputImage> children_list = new LinkedList<OutputImage>();

		// WERSJA NASZA
		// int mother = 0;
		// int father = 0;
		// while (i < populationSize)
		// {
		// if (mother != father)
		// {
		//
		// double mutationSize = Math.random();
		//
		// if(mutationSize >= mutationProbability){
		// children_list = mutation(crossover(elites.get(mother),
		// elites.get(father)));
		// }else{
		// children_list = crossover(elites.get(mother), elites.get(father));
		// }
		//
		// outputs.add(children_list.get(0));
		// outputs.add(children_list.get(1));
		// // geneticImages[i] = reproduct(eliteImages[mother],
		// // eliteImages[father]);
		// i++;
		// if (i >= populationSize)
		// break;
		// }
		//
		// father++;
		// if (father >= eliteSize)
		// {
		// mother++;
		// father = 0;
		// }
		// if (mother >= eliteSize)
		// {
		// mother = 0;
		// father = 1;
		// }
		// }

		// WERSJA PROWADZACEGO
		while (i < populationSize)
		{
			for (int j = 0; j < eliteSize - 1; j += 2)
			{
				double mutationSize = Math.random();

				if (mutationSize <= mutationProbability)
				{
					children_list = mutation(crossover(elites.get(j),elites.get(j + 1)));
				} else
				{
					children_list = crossover(elites.get(j), elites.get(j + 1));
				}

				outputs.add(children_list.get(0));
				outputs.add(children_list.get(1));
				i++;
				if (i >= populationSize)
					break;
			}
		}

		Random generator = new Random();
		int randomMember1 = generator.nextInt(eliteSize);
		int randomMember2 = generator.nextInt(eliteSize);

		if ((randomMember1 != randomMember2)
				&& (randomMember1 != randomMember2 - 1)
				&& (randomMember1 != randomMember2 + 1))
		{
			double mutationSize = Math.random();

			if (mutationSize >= mutationProbability)
			{
				children_list = mutation(crossover(elites.get(randomMember1),
						elites.get(randomMember2)));
			} else
			{
				children_list = crossover(elites.get(randomMember1),
						elites.get(randomMember2));
			}

			outputs.add(children_list.get(0));
			outputs.add(children_list.get(1));
			i++;
		}

		return elites.get(nowBestIndex);
	}

	public OutputImage randomImage()
	{
		Random rand = new Random();
		return outputs.get(rand.nextInt(populationSize));
	}
}
