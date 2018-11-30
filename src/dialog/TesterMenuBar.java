package dialog;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class TesterMenuBar extends JMenuBar implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MainWindow application;

	public TesterMenuBar(MainWindow application) {
		this.application = application;

		JMenu fileMenu = new JMenu("Файл");
		fileMenu.setMnemonic(KeyEvent.VK_F);

		JMenuItem fileOpen = new JMenuItem("Открыть...");
		fileOpen.setMnemonic(KeyEvent.VK_O);
		fileOpen.setAccelerator(KeyStroke.getKeyStroke('O', Toolkit
				.getDefaultToolkit().getMenuShortcutKeyMask()));
		fileOpen.setActionCommand("open");
		fileOpen.addActionListener(this);
		fileMenu.add(fileOpen);

		fileMenu.addSeparator();

		JMenuItem fileSave = new JMenuItem("Сохранить...");
		fileSave.setMnemonic(KeyEvent.VK_S);
		fileSave.setAccelerator(KeyStroke.getKeyStroke('S', 
				Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		fileSave.setActionCommand("save");
		fileSave.addActionListener(this);
		fileMenu.add(fileSave);

		fileMenu.addSeparator();

		JMenuItem fileClose = new JMenuItem("Закрыть");
		fileClose.setMnemonic(KeyEvent.VK_C);
		fileClose.setAccelerator(KeyStroke.getKeyStroke('W', Toolkit
				.getDefaultToolkit().getMenuShortcutKeyMask()));
		fileClose.setActionCommand("close");
		fileClose.addActionListener(this);
		fileMenu.add(fileClose);

		JMenuItem fileCloseAll = new JMenuItem("Закрыть всё");
		fileCloseAll.setMnemonic(KeyEvent.VK_A);
		fileCloseAll.setActionCommand("close_all");
		fileCloseAll.addActionListener(this);
		fileMenu.add(fileCloseAll);

		fileMenu.addSeparator();

		JMenuItem fileExit = new JMenuItem("Выход");
		fileExit.setMnemonic(KeyEvent.VK_X);
		fileExit.setActionCommand("exit");
		fileExit.addActionListener(this);
		fileMenu.add(fileExit);

		add(fileMenu);

		JMenu helpMenu = new JMenu("Справка");
		helpMenu.setMnemonic(KeyEvent.VK_H);

		JMenuItem helpContents = new JMenuItem("Содержание...");
		helpContents.setMnemonic(KeyEvent.VK_C);
		helpContents.setActionCommand("help_contents");
		helpContents.addActionListener(this);
		helpMenu.add(helpContents);

		helpMenu.addSeparator();

		JMenuItem helpAbout = new JMenuItem("О программе");
		helpAbout.setMnemonic(KeyEvent.VK_A);
		helpAbout.setActionCommand("about");
		helpAbout.addActionListener(this);

		helpMenu.add(helpAbout);

		add(helpMenu);

	}

	public void actionPerformed(ActionEvent e) {

		String command = e.getActionCommand();

		if (command.equals("exit")) {
			System.exit(0);
		} else if (command.equals("open")) {
			application.openFile();
		} else if (command.equals("close_all")) {
			application.closeAll();
		} 
	}

}