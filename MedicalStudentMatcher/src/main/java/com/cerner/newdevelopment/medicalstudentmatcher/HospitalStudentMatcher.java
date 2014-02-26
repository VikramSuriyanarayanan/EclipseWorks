package com.cerner.newdevelopment.medicalstudentmatcher;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.cerner.newdevelopment.matcher.Match;
import com.cerner.newdevelopment.preference.Preference;

/*
 * This class contains implementation to transform the input into proper format and find the matches using specified matching algorithm
 */
public class HospitalStudentMatcher 
{
	private IPreferenceMatchingAlgorithm matchingAlgorithm;
	private List<Preference<String, String>> hospitalPrefs;
	private List<Preference<String, String>> studentPrefs;
	
	public HospitalStudentMatcher(IPreferenceMatchingAlgorithm matchingAlgorithm, 
			List<Preference<String, String>> hospitalPrefs, 
			List<Preference<String, String>> studentPrefs)
	{
		this.matchingAlgorithm = matchingAlgorithm;
		this.hospitalPrefs = hospitalPrefs;
		this.studentPrefs = studentPrefs;
	}
	
	/*
	 * This method finds the matches
	 */
	public List<Match<String, String>> FindMatch()
	{
		Map<String, Map<String, PreferenceDetails>> hospitalPreferencesMap = this.TransformIntoMap(this.hospitalPrefs);
		Map<String, Map<String, PreferenceDetails>> studentPreferencesMap = this.TransformIntoMap(this.studentPrefs);		
		return matchingAlgorithm.match(hospitalPreferencesMap, studentPreferencesMap);
	}
	
	/*
	 * This method transforms the imput into desired format to find matches easily.
	 */
	private Map<String, Map<String, PreferenceDetails>> TransformIntoMap(List<Preference<String, String>> preferences)
	{	
		Map<String, Map<String, PreferenceDetails>> preferencesMap = new HashMap<String, Map<String,PreferenceDetails>>();
		
		Iterator<Preference<String, String>> preferencesIterator = preferences.iterator();
		while(preferencesIterator.hasNext())
		{
			Preference<String, String> preference = preferencesIterator.next();
			String preferrer = preference.getPreferrer();
			String preferred = preference.getPreferred();
			int value = preference.getValue();
			
			if(preferencesMap.get(preferrer)==null)
			{
				Map<String, PreferenceDetails> preferredMap = new HashMap<String, PreferenceDetails>();
				
				PreferenceDetails details = new PreferenceDetails();
				details.rank = value;
				details.value = value;
				details.isAlreadyprocessed = false;
				preferredMap.put(preferred, details);
				preferencesMap.put(preferrer, preferredMap);
			}
			else
			{
				Map<String, PreferenceDetails> preferredMap = preferencesMap.get(preferrer);
				
				PreferenceDetails details = new PreferenceDetails();
				details.rank = value;	
				details.value = value;
				details.isAlreadyprocessed = false;
				preferredMap.put(preferred, details);
				
				preferencesMap.put(preferrer, preferredMap);				
			}
			
		}
		
		return preferencesMap;
	}
}
