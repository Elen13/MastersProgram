package sequence;

import java.util.ArrayList;

public class Sequence {
	
	private ArrayList<Integer> sequence = new ArrayList<Integer>();
	private SequenceFile file;

	public Sequence(SequenceFile file, ArrayList<Integer> sq) {
		this.sequence = sq;
		this.file = file;
	}
	
	public ArrayList<Integer> getSequence() {
		return sequence;
	}
	
	public SequenceFile file() {
		return file;
	}
	
	public String getFileName(){
		return file.getFile().getName();
	}

}
