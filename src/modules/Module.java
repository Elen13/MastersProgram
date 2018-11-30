package modules;

import javax.swing.JPanel;

import sequence.Sequence;


public interface Module {

	public void processSequence(Sequence sequence);

	public JPanel getResultsPanel();

	public String name();

	public String description();

	public void reset();

	public boolean raisesError();

	public boolean raisesWarning();

	public boolean ignoreInReport();
}