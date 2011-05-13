package de.hda.mus.audio.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import javax.sound.sampled.AudioFormat;

public abstract class RewritingHeaderAudioOutputStream {
	protected RandomAccessFile randomAccessFile;
	protected AudioFormat audioFormat;
	protected long totalFileSize;
	protected long audioDataSize;
	protected int headerSize;

	/**
	 * Constructs a new AudioOutputStream object
	 * 
	 * @param file
	 *            The file object representing the location where to save.
	 * @param audioFormat
	 *            The AudioFormat
	 * @param headerSize
	 *            The size of the header in bytes
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public RewritingHeaderAudioOutputStream(File file, AudioFormat audioFormat, int headerSize) throws FileNotFoundException, IOException {
		this.audioFormat = audioFormat;
		this.headerSize = headerSize;
		if (!file.exists()) {
			System.out.println("Creating new file: " + file.getCanonicalFile());
			file.createNewFile();
		}
		randomAccessFile = new RandomAccessFile(file, "rw");
		audioDataSize = 0;
		totalFileSize = 0;
		randomAccessFile.seek(0);
		writeHeader();
	}

	/**
	 * Writes the header data by using an internal <code>RandomAccessFile</code>
	 * .
	 * 
	 * @throws IOException
	 */
	protected abstract void writeHeader() throws IOException;

	/**
	 * Writes <code>length</code> bytes from the specified byte array starting
	 * at offset <code>offset</code> using an internal
	 * <code>RandomAccessFile</code>. to the used output stream.
	 * 
	 * @param buffer
	 *            The buffer to write
	 * @param offset
	 *            The start offset in the data.
	 * @param length
	 *            The number of bytes to write.
	 * @throws IOException
	 */
	public void write(byte[] buffer, int offset, int length) throws IOException {
		// accumulate audio data length
		audioDataSize += length;
		// write
		randomAccessFile.write(buffer, offset, length);
	}

	/**
	 * Rewrites the header with the calculated file size and audio data size and
	 * closes the internally used <code>RandomAccessFile</code>.
	 * 
	 * @throws IOException
	 */
	public void close() throws IOException {
		// compute total file size
		totalFileSize = headerSize + audioDataSize;

		// write header again with file size and audio data size
		// achieved during writing the buffers
		updateHeader();
		randomAccessFile.close();
		try {
			this.finalize();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method responsible for rewriting the header data after all audio data has
	 * been written. The method is called by invoking the <code>close</code>
	 * method.
	 * 
	 * @throws IOException
	 */
	protected void updateHeader() throws IOException {
		randomAccessFile.seek(0);
		writeHeader();
	}

	/**
	 * Write a given <code>int</code> value in little endian byte order by using
	 * a <code>RandomAccessFile</code>.
	 * 
	 * @param value
	 *            Int to write in little endian byte oder.
	 * @throws IOException
	 */
	public void writeLittleEndianInt(int value) throws IOException {
		randomAccessFile.writeByte(value & 0xFF);
		randomAccessFile.writeByte((value >> 8) & 0xFF);
		randomAccessFile.writeByte((value >> 16) & 0xFF);
		randomAccessFile.writeByte((value >> 24) & 0xFF);
	}

	/**
	 * Write a given <code>short</code> value in little endian byte order by
	 * using a <code>RandomAccessFile</code>.
	 * 
	 * @param value
	 *            Short to write in little endian byte oder.
	 * @throws IOException
	 */
	public void writeLittleEndianShort(short value) throws IOException {
		randomAccessFile.writeByte(value & 0xFF);
		randomAccessFile.writeByte((value >> 8) & 0xFF);
	}

	/**
	 * Get the calculatet file size.
	 * 
	 * @return The total calculated file size after writing header and audio
	 *         data.
	 */
	public long getTotalFileSize() {
		return totalFileSize;
	}

	/**
	 * Get the header size in bytes.
	 * 
	 * @return The header size in bytes.
	 */
	private int getHeaderSize() {
		return headerSize;
	}

	/**
	 * Get the number of written audio data in bytes.
	 * 
	 * @return The number of written audio data in bytes.
	 */
	public long getAudioDataSize() {
		return audioDataSize;
	}
}
