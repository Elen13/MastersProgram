package sequence;

import java.io.File;

public interface SequenceFile {

	public boolean hasNext();

	public Sequence next() throws SequenceFormatException;

	public boolean isColorspace();

	public int getPercentComplete();

	public File getFile();

	public String name();

	public double getFileSize();
}