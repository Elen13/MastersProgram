package modules;

import javax.swing.JPanel;

public abstract class AbstractModule implements Module {

	public boolean raisesError() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean raisesWarning() {
		// TODO Auto-generated method stub
		return false;
	}

	public JPanel getResultsPanel() {
		// TODO Auto-generated method stub
		return null;
	}

}
