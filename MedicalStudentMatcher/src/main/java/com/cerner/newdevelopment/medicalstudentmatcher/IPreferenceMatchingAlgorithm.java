package com.cerner.newdevelopment.medicalstudentmatcher;

import java.util.List;
import java.util.Map;

import com.cerner.newdevelopment.matcher.Match;

public interface IPreferenceMatchingAlgorithm
{	
	public List<Match<String, String>> match(Map<String, Map<String, PreferenceDetails>> hospitalPreferences,
			Map<String, Map<String, PreferenceDetails>> studentPreferences);
}
