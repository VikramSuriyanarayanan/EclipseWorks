package com.cerner.newdevelopment.medicalstudentmatcher.dataaccess;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import com.cerner.newdevelopment.matcher.Match;

/**
 * Writes string-based matches to a comma separated file
 * @author MW5521
 *
 */
public class MatchWriter<P,O> {
	private String filename;

	public MatchWriter(String file) {
		filename = file;
	}

	/**
	 * Writes a match to the file specified in the constructor.
	 * @param match The match object that needs to be written
	 */
	public void WriteMatch(Match<P, O> match) {
		Writer writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(filename, true));
			writer.append(match.getItem1().toString());
			writer.append(',');
			writer.append(match.getItem2().toString());
			writer.append('\n');
		} catch (IOException e) {
			System.err.format("IOException: %s%n while writing match to file.", e);
		} finally {
			try {
				if(writer != null)
					writer.close();
			} catch (IOException e) {
				System.err.format("IOException: %s%n while writing match to file.", e);				
			}				
		}
	}
}
