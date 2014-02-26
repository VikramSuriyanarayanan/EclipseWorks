package com.cerner.newdevelopment.preference.parser;

import com.cerner.newdevelopment.preference.Preference;

/**
 * Parses a string to a preference of strings based on the assumption fields are comma-separated
 * @author MW5521
 *
 */
public class PreferenceCommaParser implements IPreferenceParser<String, String> {
	private enum ColumnNum {
		ColPreferrer, ColPreference, ColValue
	};
	
	@Override
	public Preference<String, String> parse(String pref) {
		String[] items = pref.split(",");

		Preference<String, String> preference = new Preference<String, String>(
				items[ColumnNum.ColPreferrer.ordinal()].trim(),
				items[ColumnNum.ColPreference.ordinal()].trim(),
				Integer.parseInt(items[ColumnNum.ColValue.ordinal()].trim()));

		return preference;
	}

}
