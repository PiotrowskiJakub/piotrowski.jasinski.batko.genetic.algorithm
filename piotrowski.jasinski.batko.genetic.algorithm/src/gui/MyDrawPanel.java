package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

/**
 * 
 * @author Jakub Piotrowski
 * @version 1.0
 * Class to draw image in ImagePanel using genetic algorithm.
 */
public class MyDrawPanel extends JPanel
{
	private static final long serialVersionUID = -6184846121081330233L;
	private int width, height;
	
	public void setWidth(int width)
	{
		this.width = width;
	}

	public void setHeight(int height)
	{
		this.height = height;
	}

	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(new Color(123));
		g2.fillOval(width/2, height/2, 100, 100);
	}

	
}
