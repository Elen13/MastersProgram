package sequence;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class BinFile implements SequenceFile {

	private File file;
	private String name;
	private Sequence newSequence = null;
	// private String seqence;
	private long fileSize = 0;
	private FileInputStream fis;

	public BinFile(File file) {
		this.file = file;
		// System.out.println("file size " + fileSize); в байтах
		name = file.getName();
		fileSize = file.length();

		try (DataInputStream datain = new DataInputStream(
				
				fis = new FileInputStream(file))) {

					byte[] buffer = new byte[datain.available()];
					datain.read(buffer, 0, datain.available());
					
					ArrayList<Integer> test = new ArrayList<Integer>();
					for (int i = 0; i < buffer.length; i++)
						test.add((buffer[i / Byte.SIZE] << i % Byte.SIZE & 0x80) == 0 ? 0
								: 1);
			
					newSequence = new Sequence(this, test);
				    System.out.println("arr: " + test.size());
				    System.out.println("buf: " + buffer.length);
				   						
		} catch (IOException е) {
			System.out.println("Error input-output: " + е); }
	}

	public File getFile() {
		return file;
	}

	public double getFileSize() {
		double size = (double) file.length();// (double)
												// (file.length()/(1024*1024));
		return size;
	}

	public String name() {
		return name;
	}

	static String toBinary(byte[] bytes) {
		StringBuilder sb = new StringBuilder(bytes.length * Byte.SIZE);
		for (int i = 0; i < Byte.SIZE * bytes.length; i++)
			sb.append((bytes[i / Byte.SIZE] << i % Byte.SIZE & 0x80) == 0 ? '0'
					: '1');
		return sb.toString();
	}

	static byte[] fromBinary(String s) {
		int sLen = s.length();
		byte[] toReturn = new byte[(sLen + Byte.SIZE - 1) / Byte.SIZE];
		char c;
		for (int i = 0; i < sLen; i++)
			if ((c = s.charAt(i)) == '1')
				toReturn[i / Byte.SIZE] = (byte) (toReturn[i / Byte.SIZE] | (0x80 >>> (i % Byte.SIZE)));
			else if (c != '0')
				throw new IllegalArgumentException();
		return toReturn;
	}

	@Override
	public boolean hasNext() {
		return newSequence != null;
	}

	@Override
	public Sequence next() {
		Sequence seq = newSequence;
		readNext();
		return seq;
	}

	private void readNext() {
		newSequence = null;
	}

	@Override
	public boolean isColorspace() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getPercentComplete() {
		if (!hasNext())
			return 100;
		try {
			int percent = (int) (((double) fis.getChannel().position() / fileSize) * 100);
			return percent;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}

}
