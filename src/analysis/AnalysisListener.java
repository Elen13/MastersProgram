package analysis;

import modules.Module;
import sequence.SequenceFile;

public interface AnalysisListener {

	public void analysisStarted(SequenceFile file);

	public void analysisUpdated(SequenceFile file, int sequencesProcessed,
			int percentComplete);

	public void analysisComplete(SequenceFile file, Module[] results);

	public void analysisExceptionReceived(SequenceFile file, Exception e);
}
