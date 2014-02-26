package com.cerner.newdevelopment.preference.parser;

import com.cerner.newdevelopment.preference.Preference;

public interface IPreferenceParser<P, O> {
	
	/**
	 * Creates a preference object by parsing a string
	 * @param pref  A string representation of the string
	 * @return      The preference object 
	 */
	public Preference<P, O> parse(String pref);

}
