package dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * The Class SeqMonkTitlePanel.
 */

public class TesterTitlePanel extends JPanel {
	private static final long serialVersionUID = 1L;

	/**
	 * Provides a small panel which gives details of the FastQC version and
	 * copyright. Used in both the welcome panel and the about dialog.
	 */
	public TesterTitlePanel() {
		setLayout(new BorderLayout(5, 1));

		JPanel c = new JPanel();
		c.setLayout(new GridBagLayout());

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.weightx = 1;
		constraints.weighty = 1;
		constraints.insets = new Insets(3, 3, 0, 0);
		constraints.fill = GridBagConstraints.NONE;

		JLabel program = new SmoothJLabel(
				"Программа для графического тестирования ГПСЧ", JLabel.CENTER);
		program.setFont(new Font("Dialog", Font.BOLD, 18));
		program.setForeground(new Color(0, 0, 0));
		c.add(program, constraints);

		constraints.gridy++;

		add(c, BorderLayout.CENTER);
	}

	private class SmoothJLabel extends JLabel {

		private static final long serialVersionUID = 1L;

		public SmoothJLabel(String text, int position) {
			super(text, position);
		}

		public void paintComponent(Graphics g) {
			if (g instanceof Graphics2D) {
				((Graphics2D) g).setRenderingHint(
						RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_ON);
			}
			super.paintComponent(g);
		}

	}

}
