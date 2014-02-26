package com.cerner.newdevelopment.medicalstudentmatcher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.cerner.newdevelopment.matcher.Match;

/*
 * This class implements the preference matching algorithm according to the rules set for 
 * OOP assessment
 */
public class CernerPreferenceMatchingAlgorithm implements IPreferenceMatchingAlgorithm {

	private Map<String, Map<String, PreferenceDetails>> hospitalStudentPreferences;
	private Map<String, ArrayList<Entry<String, PreferenceDetails>>> hospitalStudentPreferencesList;
	private Map<String, Map<String, PreferenceDetails>> hospitalPreferences;
	private Map<String, Map<String, PreferenceDetails>> studentPreferences;
	private Set<String> hospitals;
	private Set<String> students;
	private int currentHospitalStudentPreference=0;
	
	@Override
	public List<Match<String, String>> match(
			Map<String, Map<String, PreferenceDetails>> hospitalPreferences,
			Map<String, Map<String, PreferenceDetails>> studentPreferences) {
		
		this.hospitals = hospitalPreferences.keySet();
		this.students = studentPreferences.keySet();
		this.hospitalPreferences = hospitalPreferences;
		this.studentPreferences = studentPreferences;
		
		// Assign ranks for hospital and student preference values
		this.hospitalPreferences = AssignRankToPreferences(hospitalPreferences);
		this.studentPreferences = AssignRankToPreferences(studentPreferences);
		
		// Calculate combined hospital/student preferences
		CalculateHospitalStudentCombinedPreferences();		
		
		// Find the match and return the result
		return this.FindMatch();
	}
	
	/*
	 * This method calculates the sum of hospital and student preferences and put them in a matrix.
	 * This combined value is true representation of how much a student/hospital prefer each other.
	 */
	private void CalculateHospitalStudentCombinedPreferences()
	{
		this.hospitalStudentPreferences = new HashMap<String, Map<String,PreferenceDetails>>();
		
		Iterator<String> hospitalsIterator =null;
		Iterator<String> studentsIterator =null;
		hospitalsIterator = this.hospitals.iterator();
		while(hospitalsIterator.hasNext())
		{
			String hospital = hospitalsIterator.next();
			studentsIterator = this.students.iterator();
			Map<String,PreferenceDetails> preferredMap = new HashMap<String, PreferenceDetails>();
			while(studentsIterator.hasNext())
			{
				String student = studentsIterator.next();
				PreferenceDetails  details = new PreferenceDetails();
				details.hospitalPreference = this.hospitalPreferences.get(hospital).get(student).rank ;
				details.studentPreference = this.studentPreferences.get(student).get(hospital).rank;
				details.value = this.hospitalPreferences.get(hospital).get(student).rank + this.studentPreferences.get(student).get(hospital).rank;
				details.isAlreadyprocessed = false;
				preferredMap.put(student, details);
			}
			
			this.hospitalStudentPreferences.put(hospital, preferredMap);
		}		
	}
	
	/*
	 * This method assigns rank to preference values. Preference values could be anything,as the only criteria is
	 * lower the value, greater the preference.
	 * To standardize, preference values are assigned ranks.
	 */
	private Map<String, Map<String, PreferenceDetails>> AssignRankToPreferences(Map<String, Map<String, PreferenceDetails>> preferences)
	{		
		Iterator<String> iterator= preferences.keySet().iterator();
		String entity = null;
		
		while(iterator.hasNext())
		{
			entity = iterator.next();
			Map<String,PreferenceDetails> preferredMap = preferences.get(entity);
			ArrayList<Entry<String,PreferenceDetails>> list = new ArrayList<Entry<String, PreferenceDetails>>(preferredMap.entrySet());
			Collections.sort(list, new Comparator<Entry<String, PreferenceDetails>>() {
			    public int compare(Entry<String, PreferenceDetails> e1, Entry<String, PreferenceDetails> e2) 
			    {
			        if(e1.getValue().value > e2.getValue().value)
			        {
			        	return 1;
			        }
			        else if(e1.getValue().value < e2.getValue().value)
			        {
			        	return -1;
			        }
			        else
			        {
			        	return 0;			        	
			        }
			    }			    
			});
			
			preferredMap = new HashMap<String, PreferenceDetails>();
			int rank = 1;
			int preValue=list.get(0).getValue().value;
			for(int i=0;i<list.size();i++)
			{
				PreferenceDetails listPreferenceDetails = list.get(i).getValue();
				PreferenceDetails details = new PreferenceDetails();
				if(preValue != listPreferenceDetails.value)
				{
					rank++;
				}
				details.rank = rank;				
				details.isAlreadyprocessed = false;
				preferredMap.put(list.get(i).getKey(), details);
			}
			
			preferences.put(entity, preferredMap);
		}
		
		return preferences;
	}
	
	/*
	 * This method finds the match for each hospital and finally returns the match list.
	 * Algorithm:
	 * 1. Iterate through list of hospitals
	 * 2. For each hospital, pick the least valued student based on combined hospital/student 
	 *    preference matrix.
	 * 3.  
	 */
	private List<Match<String, String>> FindMatch()
	{
		Iterator<String> hospitalsIterator =null;
		Iterator<String> studentsIterator =null;
		hospitalsIterator = this.hospitals.iterator();	
		this.hospitalStudentPreferencesList = new HashMap<String, ArrayList<Entry<String,PreferenceDetails>>>();
		
		Map<String, String> matchesMap = new HashMap<String, String>();
		
		while(hospitalsIterator.hasNext())
		{
			String hospital = hospitalsIterator.next();
			studentsIterator = this.students.iterator();
			// Get the set of student preferences for hospital and sort them
			Map<String,PreferenceDetails> preferredMap = this.hospitalStudentPreferences.get(hospital);
			ArrayList<Entry<String,PreferenceDetails>> list = new ArrayList<Entry<String, PreferenceDetails>>(preferredMap.entrySet());
			Collections.sort(list, new Comparator<Entry<String, PreferenceDetails>>() {
			    public int compare(Entry<String, PreferenceDetails> e1, Entry<String, PreferenceDetails> e2) 
			    {
			    	// Sort first based on combined hospital/student preference value
			        if(e1.getValue().value > e2.getValue().value)
			        {
			        	return 1;
			        }
			        else if(e1.getValue().value < e2.getValue().value)
			        {
			        	return -1;
			        }
			        else
			        {
			        	// If combined hospital/student preference values are equal, then sort based on
			        	// hospital preference value for the current pair of students
			        	if(e1.getValue().hospitalPreference > e2.getValue().hospitalPreference)
			        	{
			        		return 1;
			        	}
			        	else if(e1.getValue().hospitalPreference < e2.getValue().hospitalPreference)
			        	{
			        		return -1;
			        	}
			        	else
			        	{
			        		return 0;			        		
			        	}			        	
			        }
			    }			    
			});
			
			this.hospitalStudentPreferencesList.put(hospital, list);
		}
		
		
		hospitalsIterator = this.hospitals.iterator();	
		String previouslyMatchedHospital;
		while(hospitalsIterator.hasNext())
		{
			String hospital = hospitalsIterator.next();
			String student = null;
			
			while(true)
			{
				// Pick the student from the available who has lowest preference value
				student = this.FindStudent(hospital);
				// If student is not already picked, add it to the match Hashmap
				if(matchesMap.get(student) == null)
				{
					matchesMap.put(student, hospital);
					break;
				}
				else
				{					
					previouslyMatchedHospital = matchesMap.get(student);
					int previouslyPickedHospitalStudentPreference = this.hospitalStudentPreferences.get(previouslyMatchedHospital).get(student).value;
					// If student already picked by some other hospital
					//       1. If current hospital has more preference , replace the existing match and find a new
					//          match for removed hospital
					//       2. If current hospital/student preference and already matched hospital/student preferences are equal, then check
					//          for the hospital preferences for the student and pick the hospital with least values. Find a new match
					//          for the removed hospital.
					if(currentHospitalStudentPreference < previouslyPickedHospitalStudentPreference)
					{
						// Replace the match						
						matchesMap.put(student, hospital);
						hospital = previouslyMatchedHospital;
					}
					else if(currentHospitalStudentPreference == previouslyPickedHospitalStudentPreference)
					{
						if(this.hospitalPreferences.get(hospital).get(student).rank 
								< this.hospitalPreferences.get(previouslyMatchedHospital).get(student).rank) 
						{
							matchesMap.put(student, hospital);
							hospital = previouslyMatchedHospital;
						}
					}
					else
					{
						// Find new match				
					}					
				}
			}
		}
		
		// Copy the matches from hashmap to Match List
		studentsIterator = this.students.iterator();
		List<Match<String, String>> matches = new ArrayList<>();
		while(studentsIterator.hasNext())
		{
			String student = studentsIterator.next();
			Match<String, String> match = new Match<String, String>(matchesMap.get(student), student);
			matches.add(match);
		}		
		
		return matches;
		
	}
	
	/*
	 * This method picks a student for hospital from the list of available students based on 
	 * the preferences
	 */
	private String FindStudent(String hospital)
	{
		String student=null;
		ArrayList<Entry<String,PreferenceDetails>> list = this.hospitalStudentPreferencesList.get(hospital);
		
		// Always pick the lowest valued student from the list.(List is already sorted)
		// Once picked, remove that student from the list, as this student will no longer be considered.
		if(list.size() != 0)
		{
			student = list.get(0).getKey();
			currentHospitalStudentPreference = list.get(0).getValue().value;
			list.remove(0);
			this.hospitalStudentPreferencesList.put(hospital, list);
		}
		
		return student;
	}
}
