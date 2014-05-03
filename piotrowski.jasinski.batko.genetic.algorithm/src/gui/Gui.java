package gui;

import genetic.algorithm.Algorithm;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * 
 * @author Jakub Piotrowski
 * @version 1.0 Class to display graphical user interface.
 */
public class Gui extends JFrame
{
	private static final long serialVersionUID = 113639547652102992L;

	private JPanel mainPanel;
	private JLabel lab_image, lab_populationSize, lab_eliteSize,
			lab_numOfElements, lab_mutationProbability, lab_generation,
			lab_numberOfGeneration, lab_fitness, lab_numberOfFitness;
	private JTextField tex_imagePath, tex_populationSize, tex_eliteSize,
			tex_numOfElements, tex_mutationProbability;
	private JButton load, start, stop;
	private OriginalImage originalImage;
	private OutputImage outputImage;
	private File imagePath;
	private Algorithm algorithm;

	public static void main(String[] args)
	{
		new Gui();
	}

	public Gui()
	{
		super("Create images using a genetic algorithm");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// main Panel
		mainPanel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		// 1 row
		lab_image = new JLabel("Image: ");
		lab_image.setPreferredSize(new Dimension(200, 50));
		tex_imagePath = new JTextField(10);
		tex_imagePath.setEditable(false);
		load = new JButton("Load image");
		load.addActionListener(new LoadListener());
		c.gridx = 0;
		c.gridy = 0;
		mainPanel.add(lab_image, c);
		c.gridx = 1;
		c.gridy = 0;
		mainPanel.add(tex_imagePath, c);
		c.gridx = 2;
		c.gridy = 0;
		mainPanel.add(load, c);

		// 2 row
		lab_populationSize = new JLabel("Size of population: ");
		lab_populationSize.setPreferredSize(new Dimension(200, 50));
		tex_populationSize = new JTextField("50", 10);
		c.gridx = 0;
		c.gridy = 2;
		mainPanel.add(lab_populationSize, c);
		c.gridx = 1;
		c.gridy = 2;
		mainPanel.add(tex_populationSize, c);

		// 3 row
		lab_eliteSize = new JLabel("Size of elite: ");
		lab_eliteSize.setPreferredSize(new Dimension(200, 50));
		tex_eliteSize = new JTextField(Integer.toString((Integer
				.parseInt(tex_populationSize.getText()) / 2)), 10);
		tex_eliteSize.setEditable(false);
		c.gridx = 0;
		c.gridy = 3;
		mainPanel.add(lab_eliteSize, c);
		c.gridx = 1;
		c.gridy = 3;
		mainPanel.add(tex_eliteSize, c);

		// 4 row
		lab_numOfElements = new JLabel("Number of elements: ");
		lab_numOfElements.setPreferredSize(new Dimension(200, 50));
		tex_numOfElements = new JTextField("200", 10);
		c.gridx = 0;
		c.gridy = 4;
		mainPanel.add(lab_numOfElements, c);
		c.gridx = 1;
		c.gridy = 4;
		mainPanel.add(tex_numOfElements, c);

		// 5 row
		lab_mutationProbability = new JLabel("The probability of mutation: ");
		lab_mutationProbability.setPreferredSize(new Dimension(200, 50));
		tex_mutationProbability = new JTextField("0.01", 10);
		c.gridx = 0;
		c.gridy = 5;
		mainPanel.add(lab_mutationProbability, c);
		c.gridx = 1;
		c.gridy = 5;
		mainPanel.add(tex_mutationProbability, c);

		// 6 row
		start = new JButton("Start");
		start.addActionListener(new StartListener());
		stop = new JButton("Stop");
		c.gridx = 0;
		c.gridy = 6;
		mainPanel.add(start, c);
		c.gridx = 1;
		c.gridy = 6;
		mainPanel.add(stop, c);

		// 7 row
		originalImage = new OriginalImage();
		outputImage = new OutputImage();
		c.gridx = 0;
		c.gridy = 7;
		mainPanel.add(originalImage, c);
		c.gridx = 1;
		c.gridy = 7;
		mainPanel.add(outputImage, c);

		// 8 row
		lab_generation = new JLabel("Generation: ");
		lab_generation.setPreferredSize(new Dimension(200, 50));
		lab_numberOfGeneration = new JLabel("0");
		c.gridx = 0;
		c.gridy = 8;
		mainPanel.add(lab_generation, c);
		c.gridx = 1;
		c.gridy = 8;
		mainPanel.add(lab_numberOfGeneration, c);

		// 9 row
		lab_fitness = new JLabel("Fitness: ");
		lab_fitness.setPreferredSize(new Dimension(200, 50));
		lab_numberOfFitness = new JLabel("0 %");
		c.gridx = 0;
		c.gridy = 9;
		mainPanel.add(lab_fitness, c);
		c.gridx = 1;
		c.gridy = 9;
		mainPanel.add(lab_numberOfFitness, c);

		this.getContentPane().add(mainPanel);
		this.pack();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height
				/ 2 - this.getSize().height / 2);
		this.setVisible(true);
	}

	private class StartListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e)
		{
			if (imagePath == null)
				JOptionPane.showMessageDialog(Gui.this,
						"Please choose the image.", "No image",
						JOptionPane.ERROR_MESSAGE);
			else if(Integer.parseInt(tex_populationSize.getText()) < 10)
				JOptionPane.showMessageDialog(Gui.this,
							"Population size is too low.", "Wrong population size",
							JOptionPane.ERROR_MESSAGE);
			else if(Integer.parseInt(tex_numOfElements.getText()) < 50)
				JOptionPane.showMessageDialog(Gui.this,
						"Number of elements is too low.", "Incorrect number of elements",
						JOptionPane.ERROR_MESSAGE);
			else if(Double.parseDouble(tex_mutationProbability.getText()) > 0.1)
				JOptionPane.showMessageDialog(Gui.this,
						"Mutation probability is too high.", "Incorrect probability of mutation",
						JOptionPane.ERROR_MESSAGE);
			else
			{
				tex_eliteSize.setText(Integer.toString((Integer
						.parseInt(tex_populationSize.getText()) / 2)));
				
				originalImage.drawImage(imagePath);
				originalImage.setPreferredSize(new Dimension(originalImage
						.getWidth(), originalImage.getHeight()));

				algorithm = new Algorithm(Integer.parseInt(tex_populationSize
						.getText()), Integer.parseInt(tex_eliteSize.getText()),
						Integer.parseInt(tex_numOfElements.getText()),
						Double.parseDouble(tex_mutationProbability.getText()),
						originalImage);

				outputImage.setNumOfElements(Integer.parseInt(tex_numOfElements
						.getText()));
				outputImage.setWidth(originalImage.getWidth());
				outputImage.setHeight(originalImage.getHeight());
				outputImage.setElements(algorithm.randomImage().getElements());
				outputImage.setPreferredSize(new Dimension(originalImage
						.getWidth(), originalImage.getHeight()));

				Gui.this.repaint();
				Gui.this.pack();
				Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
				Gui.this.setLocation(dim.width / 2 - Gui.this.getSize().width
						/ 2, dim.height / 2 - Gui.this.getSize().height / 2);

				outputImage.setAlgorithm(algorithm);
				outputImage.setNumberOfGeneration(lab_numberOfGeneration);
				outputImage.setNumberOfFitness(lab_numberOfFitness);
				Thread makeEvolution = new Thread(outputImage);
				makeEvolution.start();
			}
		}
	}

	private class LoadListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			JFileChooser chooser = new JFileChooser("img/");
			chooser.showOpenDialog(Gui.this);
			imagePath = chooser.getSelectedFile();
			tex_imagePath.setText(imagePath.getName());
		}
	}
}
