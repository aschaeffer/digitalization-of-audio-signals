package de.hda.mus.audio.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;

/**
 * @author Joerg Stick
 * 
 */
public class WaveOutputStream extends RewritingHeaderAudioOutputStream {
	public static final int RIFF_MAGIC = 0x52494646; // 'RIFF'
	public static final int WAVE_MAGIC = 0x57415645; // 'WAVE'
	public static final int FMT_MAGIC = 0x666d7420; // 'fmt '
	public static final int DATA_MAGIC = 0x64617461; // 'data'
	public static final int WAV_HEADER_SIZE = 44; // header size for minimal
													// wav-header

	public WaveOutputStream(File file, AudioFormat audioFormat) throws FileNotFoundException, IOException {
		super(file, audioFormat, WAV_HEADER_SIZE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.sj.sampled.file.AudioOutputStream#writeHeader()
	 */
	protected void writeHeader() throws IOException {
		int channels = audioFormat.getChannels();
		int sampleSize = audioFormat.getSampleSizeInBits();
		int blockalign = audioFormat.getFrameSize();
		int sampleRate = (int) audioFormat.getSampleRate();

		// RIFF-Chunk
		randomAccessFile.writeInt(RIFF_MAGIC);
		writeLittleEndianInt((int) (getTotalFileSize() & 0xFFFFFFFF)); // value
																		// to
																		// update
																		// after
																		// writing
																		// audio
																		// data!!!
		randomAccessFile.writeInt(WAVE_MAGIC);
		// FMT-Chunk
		randomAccessFile.writeInt(FMT_MAGIC);
		writeLittleEndianInt(16); // restlength fmt chunk
		writeLittleEndianShort((short) 0x0001); // PCM
		writeLittleEndianShort((short) channels);
		writeLittleEndianInt(sampleRate);
		writeLittleEndianInt(sampleRate * blockalign);
		writeLittleEndianShort((short) blockalign);
		writeLittleEndianShort((short) sampleSize);
		// Data-Chunk
		randomAccessFile.writeInt(DATA_MAGIC);
		writeLittleEndianInt((int) (getAudioDataSize() & 0xFFFFFFFF)); // value
																		// to
																		// update
																		// after
																		// writing
																		// audio
																		// data!!!
	}
}
