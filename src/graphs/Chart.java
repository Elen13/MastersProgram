package graphs;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Chart extends JPanel{

	private static final long serialVersionUID = 1L;
	
	private String graphTitle;
	
	public Chart (String n){
		graphTitle = n;
	}

	public Dimension getPreferredSize() {
		return new Dimension(800, 600);
	}

	public Dimension getMinimumSize() {
		return new Dimension(100, 200);
	}

	public void paint(Graphics g) {
		super.paint(g);

		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.BLACK);
		
		int xOffset = 0;
		int titleWidth = g.getFontMetrics().stringWidth(graphTitle);
		g.drawString(graphTitle,
				(xOffset + ((getWidth() - (xOffset + 10)) / 2))
						- (titleWidth / 2), 30);
	}
}
