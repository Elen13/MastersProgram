package dialog;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

import modules.Module;
import modules.ModuleFactory;
import sequence.BinFile;
import sequence.SequenceFile;
import analysis.AnalysisRunner;

public class MainWindow extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JTabbedPane fileTabs;
	private WelcomePanel welcomePanel;
	private File lastUsedDir = null;
	private static String saveFile;
	//private FileInputStream fis;
	//private ProbOneZero test;

	public MainWindow(){
		setTitle("ГрафТест");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 600);
		setLocationRelativeTo(null);

		welcomePanel = new WelcomePanel();

		fileTabs = new JTabbedPane(JTabbedPane.TOP);
		setContentPane(welcomePanel);

		setJMenuBar(new TesterMenuBar(this));
	}
	
	public void closeAll() {
		fileTabs.removeAll();
		setContentPane(welcomePanel);
		validate();
		repaint();
	}
	
	public void openFile() {
		JFileChooser chooser;

		if (lastUsedDir == null) {
			chooser = new JFileChooser();
		} else {
			chooser = new JFileChooser(lastUsedDir);
		}
		chooser.setMultiSelectionEnabled(true);

		int result = chooser.showOpenDialog(this);
		if (result == JFileChooser.CANCEL_OPTION)
			return;

		//save result
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    	result = fc.showSaveDialog(this);//== JFileChooser.APPROVE_OPTION) { 
    	if (result == JFileChooser.CANCEL_OPTION)
			return;
    	saveFile = fc.getSelectedFile().getPath()+"/";
		
    	// If we're still showing the welcome panel switch this out for the file
    			// tabs panel
    	if (fileTabs.getTabCount() == 0) {
    		setContentPane(fileTabs);
    		validate();
   			repaint();
  		}
    	
		File[] files = chooser.getSelectedFiles();

		for (int f = 0; f < files.length; f++) {
			File fileToProcess = files[f];
			lastUsedDir = fileToProcess.getParentFile();

			SequenceFile sequenceFile;
			sequenceFile = new BinFile(fileToProcess);

			AnalysisRunner runner = new AnalysisRunner(sequenceFile);
			ResultsPanel rp = new ResultsPanel(sequenceFile);
			runner.addAnalysisListener(rp);
			fileTabs.addTab(sequenceFile.name(), rp);

			Module[] module_list = ModuleFactory.getStandardModuleList();
			runner.startAnalysis(module_list);
		}
	}
	
	public static String getSaveFile(){
		return saveFile;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

				if (args.length > 0) {
					// Set headless to true so we don't get problems
					// with people working without an X display.
					System.setProperty("java.awt.headless", "true");
				}

				else {
					try {
						UIManager.setLookAndFeel(UIManager
								.getSystemLookAndFeelClassName());
					} catch (Exception e) {
					}

					// The interactive default is to not uncompress the
					// reports after they have been generated

					MainWindow app = new MainWindow();

					app.setVisible(true);
				}
		
		/*@SuppressWarnings("unused")
		FileInputStream fis;
		ProbOneZero test;
		ArrayList<Integer> sequence = new ArrayList<Integer>();
		
		//int[] size = {65536}; //32768 - 32KB
	
		String namefi = "s_";//"sequence_";
		
		for(int ii = 1; ii < 4; ii++){
			
			String fname = namefi+ii+".bin";
			String s = namefi+ii;
			System.out.println("fname: "+fname);
		
			try (DataInputStream datain = new DataInputStream(
					
					
					
					fis = new FileInputStream("Examples/"+fname))) {
	
						byte[] buffer = new byte[datain.available()];
						datain.read(buffer, 0, datain.available());
						
						for (int i = 0; i < buffer.length; i++)
							sequence.add((buffer[i / Byte.SIZE] << i % Byte.SIZE & 0x80) == 0 ? 0
									: 1);
						test = new ProbOneZero(sequence);
					    System.out.println("arr: " + sequence.size());
					    System.out.println("buf: " + buffer.length);
						
					    test.test(s);
					   						
			} catch (IOException е) {
				System.out.println("Error input-output: " + е);}
		}*/
		//System.out.println("THE END");
		/*		QuickSort app = new QuickSort();
				
				 //Generate an integer array of length 7
				ArrayList<Double> input = app.generateRandomNumbers(7);
				
			    //Before sort
			    System.out.println(input);
				
			    //After sort
			    System.out.println(app.quicksort(input));*/
	}

}
