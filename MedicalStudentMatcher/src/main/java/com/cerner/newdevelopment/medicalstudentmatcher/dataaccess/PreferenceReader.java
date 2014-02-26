package com.cerner.newdevelopment.medicalstudentmatcher.dataaccess;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.cerner.newdevelopment.preference.Preference;
import com.cerner.newdevelopment.preference.parser.IPreferenceParser;

/**
 * Reads preferences from a file
 * @author MW5521
 *
 */
public class PreferenceReader<P,O> {
	
	private IPreferenceParser<P,O> parser;

	/**
	 * Constructs a reader for a specific file
	 * @param parse The parser to use to parse a line from the file
	 */
	public PreferenceReader(IPreferenceParser<P,O> parse) {
		parser = parse;
	}

	/**
	 * Reads preferences from a file
	 * @param filename The file to read and parse
	 * @return         The list of preferences parsed from the file
	 */
	public List<Preference<P, O>> readPreferences(String filename) {
		List<Preference<P, O>> prefs = new ArrayList<Preference<P, O>>();

		BufferedReader reader = null; 
		try {
			reader = new BufferedReader(new FileReader(filename));
			String line;
			while ((line = reader.readLine()) != null) {
				prefs.add(parser.parse(line));
			}
		} catch (FileNotFoundException e) {
			System.err.format("FileNotFoundException: %s%n.  Unable to open the preference file.", e);
		} catch (IOException e) {
			System.err.format("IOException: %s%n while attempting to read the preference file.", e);
		} catch (NumberFormatException e) {
			System.err.format("NumberFormatException on preference value %s%n.  It is not a valid number.", e);
		} finally {
			try {
				if (reader != null)
                    reader.close();
            } catch (IOException e) {
            	System.err.format("IOException %s%n while attempting to close the file.", e);
            }
		}

		return prefs;
	}

}
