package com.cerner.newdevelopment.medicalstudentmatcher;

import java.util.List;

import com.cerner.newdevelopment.matcher.Match;
import com.cerner.newdevelopment.medicalstudentmatcher.dataaccess.MatchWriter;
import com.cerner.newdevelopment.medicalstudentmatcher.dataaccess.PreferenceReader;
import com.cerner.newdevelopment.preference.Preference;
import com.cerner.newdevelopment.preference.parser.PreferenceCommaParser;

public class MedicalStudentMatcher {

	enum Arguments {
		HospitalFile, ResidentFile, OutputFile
	};

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		//Retrieve file locations from command line arguments
		String hospitalFile = "";
		String residentFile = "";
		String outFile = "";
		
		if (args.length > 2) {
			hospitalFile = args[Arguments.HospitalFile.ordinal()];
			residentFile = args[Arguments.ResidentFile.ordinal()];
			outFile = args[Arguments.OutputFile.ordinal()];
		} else {
			System.out
					.println("Please include the names for the preference files and output file when running the application.\n "
							+ "Usage: \n\tjava MedicalStudentMatcher hospital.csv student.csv out.txt\n");
			return;
		}

		//Retrieve preferences from provided files
		List<Preference<String, String>> hospitalPrefs = ReadPreferences(hospitalFile);
		List<Preference<String, String>> studentPrefs = ReadPreferences(residentFile);

		CernerPreferenceMatchingAlgorithm cernerMatchingAlgorithm = new CernerPreferenceMatchingAlgorithm();
		HospitalStudentMatcher matcher = new HospitalStudentMatcher(cernerMatchingAlgorithm, hospitalPrefs, studentPrefs);
		List<Match<String, String>> matches = matcher.FindMatch();			
		
		WriteMatches(matches, outFile);
	}

	
	/**
	 * Writes the resulting matches to the output file
	 * @param matches The matches to write
	 * @param outFile The output file
	 */
	private static void WriteMatches(List<Match<String, String>> matches, String outFile) {
		MatchWriter<String,String> writer = new MatchWriter<String, String>(outFile);
		for (Match<String, String> match : matches) {
			writer.WriteMatch(match);
		}
	}

	/**
	 * Reads preferences from the preference file provided 
	 * @param file The preference file
	 * @return     The list of preferences read from the file
	 */
	private static List<Preference<String, String>> ReadPreferences(String file) {
		PreferenceReader<String,String> reader = new PreferenceReader<String,String>(new PreferenceCommaParser());
		List<Preference<String, String>> preferences = reader.readPreferences(file);
		return preferences;
	}

}
