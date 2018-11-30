package analysis;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

import modules.Module;
import sequence.SequenceFile;

public class AnalysisQueue implements Runnable, AnalysisListener {

	private static AnalysisQueue instance = new AnalysisQueue();

	private LinkedBlockingDeque<AnalysisRunner> queue = new LinkedBlockingDeque<AnalysisRunner>();

	private AtomicInteger availableSlots = new AtomicInteger(1);
	private AtomicInteger usedSlots = new AtomicInteger(0);

	public static AnalysisQueue getInstance() {
		return instance;
	}

	private AnalysisQueue() {

		/*
		 * if (FastQCConfig.getInstance().threads != null) {
		 * availableSlots.set(FastQCConfig.getInstance().threads); }
		 */
		availableSlots.set(2);

		Thread t = new Thread(this);
		t.start();
	}

	public void addToQueue(AnalysisRunner runner) {
		queue.add(runner);
	}

	public void run() {

		while (true) {
			// System.err.println("Status available="+availableSlots+" used="+usedSlots+" queue="+queue.size());
			if (availableSlots.intValue() > usedSlots.intValue()
					&& queue.size() > 0) {
				usedSlots.incrementAndGet();
				AnalysisRunner currentRun = queue.getFirst();
				queue.removeFirst();
				currentRun.addAnalysisListener(this);
				Thread t = new Thread(currentRun);
				t.start();
			}

			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
			}
		}
	}

	public void analysisComplete(SequenceFile file, Module[] results) {
		usedSlots.decrementAndGet();
	}

	public void analysisUpdated(SequenceFile file, int sequencesProcessed,
			int percentComplete) {
	}

	public void analysisExceptionReceived(SequenceFile file, Exception e) {
		usedSlots.decrementAndGet();
	}

	public void analysisStarted(SequenceFile file) {
	}

}
