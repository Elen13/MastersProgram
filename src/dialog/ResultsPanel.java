package dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.util.Vector;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import analysis.AnalysisListener;
import modules.Module;
import sequence.SequenceFile;

public class ResultsPanel extends JPanel implements ListSelectionListener,
		AnalysisListener {

	private static final long serialVersionUID = 1L;

	private Module[] modules;
	private JList<?> moduleList;
	private JPanel[] panels;
	private JPanel currentPanel = null;
	private JLabel progressLabel;
	private SequenceFile sequenceFile;

	public ResultsPanel(SequenceFile sequenceFile) {
		this.sequenceFile = sequenceFile;
		setLayout(new BorderLayout());
		progressLabel = new JLabel("Ожидание...", JLabel.CENTER);
		add(progressLabel, BorderLayout.CENTER);
	}

	public void valueChanged(ListSelectionEvent e) {
		int index = moduleList.getSelectedIndex();
		if (index >= 0) {
			remove(currentPanel);
			currentPanel = panels[index];
			add(currentPanel, BorderLayout.CENTER);
			validate();
			repaint();
		}
	}

	public SequenceFile sequenceFile() {
		return sequenceFile;
	}

	public Module[] modules() {
		return modules;
	}

	private class ModuleRenderer extends DefaultListCellRenderer {

		private static final long serialVersionUID = 1L;

		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {
			if (!(value instanceof Module)) {
				return super.getListCellRendererComponent(list, value, index,
						isSelected, cellHasFocus);
			}

			Module module = (Module) value;
			/*
			 * ImageIcon icon = OK_ICON; if (module.raisesError()) { icon =
			 * ERROR_ICON; } else if (module.raisesWarning()) { icon =
			 * WARNING_ICON; }
			 */

			JLabel returnLabel = new JLabel(module.name());// ,icon,JLabel.LEFT);
			returnLabel.setOpaque(true);
			if (isSelected) {
				returnLabel.setBackground(Color.LIGHT_GRAY);
			} else {
				returnLabel.setBackground(Color.WHITE);
			}

			return returnLabel;
		}

	}

	public void analysisComplete(SequenceFile file, Module[] rawModules) {
		remove(progressLabel);

		Vector<Module> modulesToDisplay = new Vector<Module>();

		for (int m = 0; m < rawModules.length; m++) {
			if (!rawModules[m].ignoreInReport()) {
				modulesToDisplay.add(rawModules[m]);
			}
		}

		modules = modulesToDisplay.toArray(new Module[0]);

		panels = new JPanel[modules.length];

		for (int m = 0; m < modules.length; m++) {
			panels[m] = modules[m].getResultsPanel();
		}

		moduleList = new JList(modules);
		moduleList.setCellRenderer(new ModuleRenderer());
		moduleList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		moduleList.setSelectedIndex(0);
		moduleList.addListSelectionListener(this);

		add(new JScrollPane(moduleList), BorderLayout.WEST);

		currentPanel = panels[0];
		add(currentPanel, BorderLayout.CENTER);
		validate();

	}

	public void analysisUpdated(SequenceFile file, int sequencesProcessed,
			int percentComplete) {
		if (percentComplete > 99) {
			progressLabel.setText("Read " + sequencesProcessed + " sequences");
		} else {
			progressLabel.setText("Read " + sequencesProcessed + " sequences ("
					+ percentComplete + "%)");
		}
	}

	public void analysisExceptionReceived(SequenceFile file, Exception e) {
		progressLabel.setText("Failed to process file: "
				+ e.getLocalizedMessage());
	}

	public void analysisStarted(SequenceFile file) {
		progressLabel.setText("Начат анализ...");
	}

}
