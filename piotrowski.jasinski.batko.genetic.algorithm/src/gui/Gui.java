package gui;

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
 * @version 1.0 
 * Class to display graphical user interface.
 */
public class Gui extends JFrame
{
	private static final long serialVersionUID = 113639547652102992L;

	private JPanel mainPanel;
	private JLabel label1, label2, label3, label4, label5, label6,
			lab_pokolenie, lab_numpok, lab_dopasowanie, lab_numdop;
	private JTextField tex_imagePath, tex_populationSize, tex_eliteSize,
			tex_numOfElements, tex_mutationProbability, tex_mutationSize;
	private JButton load, start, stop;
	private OrginalImage orginalImage;
	private OutputImage outputImage;
	private File imagePath;

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
		label1 = new JLabel("Image: ");
		label1.setPreferredSize(new Dimension(200, 50));
		tex_imagePath = new JTextField(10);
		load = new JButton("Load image");
		load.addActionListener(new LoadListener());
		c.gridx = 0;
		c.gridy = 0;
		mainPanel.add(label1, c);
		c.gridx = 1;
		c.gridy = 0;
		mainPanel.add(tex_imagePath, c);
		c.gridx = 2;
		c.gridy = 0;
		mainPanel.add(load, c);

		// 2 row
		label2 = new JLabel("Size of population: ");
		label2.setPreferredSize(new Dimension(200, 50));
		tex_populationSize = new JTextField("50", 10);
		// text2.setPreferredSize(new Dimension(50, 20));
		c.gridx = 0;
		c.gridy = 2;
		mainPanel.add(label2, c);
		c.gridx = 1;
		c.gridy = 2;
		mainPanel.add(tex_populationSize, c);

		// 3 row
		label3 = new JLabel("Size of elite: ");
		label3.setPreferredSize(new Dimension(200, 50));
		tex_eliteSize = new JTextField("10", 10);
		c.gridx = 0;
		c.gridy = 3;
		mainPanel.add(label3, c);
		c.gridx = 1;
		c.gridy = 3;
		mainPanel.add(tex_eliteSize, c);

		// 4 row
		label4 = new JLabel("Number of elements: ");
		label4.setPreferredSize(new Dimension(200, 50));
		tex_numOfElements = new JTextField("200", 10);
		c.gridx = 0;
		c.gridy = 4;
		mainPanel.add(label4, c);
		c.gridx = 1;
		c.gridy = 4;
		mainPanel.add(tex_numOfElements, c);

		// 5 row
		label5 = new JLabel("The probability of mutation: ");
		label5.setPreferredSize(new Dimension(200, 50));
		tex_mutationProbability = new JTextField("0.1", 10);
		c.gridx = 0;
		c.gridy = 5;
		mainPanel.add(label5, c);
		c.gridx = 1;
		c.gridy = 5;
		mainPanel.add(tex_mutationProbability, c);

		// 6 row
		label6 = new JLabel("Size of mutations (1-100): ");
		label6.setPreferredSize(new Dimension(200, 50));
		tex_mutationSize = new JTextField("10.0", 10);
		c.gridx = 0;
		c.gridy = 6;
		mainPanel.add(label6, c);
		c.gridx = 1;
		c.gridy = 6;
		mainPanel.add(tex_mutationSize, c);

		// 7 row
		start = new JButton("Start");
		start.addActionListener(new StartListener());
		stop = new JButton("Stop");
		c.gridx = 0;
		c.gridy = 7;
		mainPanel.add(start, c);
		c.gridx = 1;
		c.gridy = 7;
		mainPanel.add(stop, c);

		// 8 row
		orginalImage = new OrginalImage();
		outputImage = new OutputImage();
		c.gridx = 0;
		c.gridy = 8;
		mainPanel.add(orginalImage, c);
		c.gridx = 1;
		c.gridy = 8;
		mainPanel.add(outputImage, c);

		// 9 row
		lab_pokolenie = new JLabel("Generation: ");
		lab_pokolenie.setPreferredSize(new Dimension(200, 50));
		lab_numpok = new JLabel("0");
		c.gridx = 0;
		c.gridy = 9;
		mainPanel.add(lab_pokolenie, c);
		c.gridx = 1;
		c.gridy = 9;
		mainPanel.add(lab_numpok, c);

		// 10 row
		lab_dopasowanie = new JLabel("Fitness: ");
		lab_dopasowanie.setPreferredSize(new Dimension(200, 50));
		lab_numdop = new JLabel("0 %");
		c.gridx = 0;
		c.gridy = 10;
		mainPanel.add(lab_dopasowanie, c);
		c.gridx = 1;
		c.gridy = 10;
		mainPanel.add(lab_numdop, c);

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
			else
			{
				orginalImage.drawImage(imagePath);
				orginalImage.setPreferredSize(new Dimension(
						orginalImage.getWidth(), orginalImage.getHeight()));
				
				outputImage.setNumOfElements(Integer.parseInt(tex_numOfElements.getText()));
				outputImage.setWidth(orginalImage.getWidth());
				outputImage.setHeight(orginalImage.getHeight());
				outputImage.setPreferredSize(new Dimension(orginalImage.getWidth(), orginalImage.getHeight()));
				
				Gui.this.repaint();
				Gui.this.pack();
				Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
				Gui.this.setLocation(dim.width / 2 - Gui.this.getSize().width
						/ 2, dim.height / 2 - Gui.this.getSize().height / 2);
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
