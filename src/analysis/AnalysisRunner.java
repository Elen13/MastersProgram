package analysis;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import modules.Module;
import sequence.Sequence;
import sequence.SequenceFile;
import sequence.SequenceFormatException;

public class AnalysisRunner implements Runnable {

	private SequenceFile file;
	private Module[] modules;
	private List<AnalysisListener> listeners = new ArrayList<AnalysisListener>();
	private int percentComplete = 0;

	public AnalysisRunner(SequenceFile file) {
		this.file = file;
	}

	public void addAnalysisListener(AnalysisListener l) {
		if (l != null && !listeners.contains(l)) {
			listeners.add(l);
		}
	}

	public void removeAnalysisListener(AnalysisListener l) {
		if (l != null && listeners.contains(l)) {
			listeners.remove(l);
		}
	}

	public void startAnalysis(Module[] modules) {
		this.modules = modules;
		for (int i = 0; i < modules.length; i++) {
			modules[i].reset();
		}
		AnalysisQueue.getInstance().addToQueue(this); // ????????????????
	}

	public void run() {

		Iterator<AnalysisListener> i = listeners.iterator();
		while (i.hasNext()) {
			i.next().analysisStarted(file);
		}

		int seqCount = 0;
		while (file.hasNext()) {
			++seqCount;
			Sequence seq;
			try {
				seq = file.next();
			} catch (SequenceFormatException e) {
				i = listeners.iterator();
				while (i.hasNext()) {
					i.next().analysisExceptionReceived(file, e);
				}
				return;
			}

			for (int m = 0; m < modules.length; m++) {
				// if (seq.isFiltered() && modules[m].ignoreFilteredSequences())
				// continue;
				modules[m].processSequence(seq); // считаем сами тесты
			}

			// считаем процент готовности
			if (seqCount % 1000 == 0) {
				if (file.getPercentComplete() >= percentComplete + 5) {

					percentComplete = (((int) file.getPercentComplete()) / 5) * 5;

					i = listeners.iterator();
					while (i.hasNext()) {
						i.next().analysisUpdated(file, seqCount,
								percentComplete);
					}
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
					}
				}
			}
		}

		i = listeners.iterator();
		while (i.hasNext()) {
			i.next().analysisComplete(file, modules);
		}

	}

}