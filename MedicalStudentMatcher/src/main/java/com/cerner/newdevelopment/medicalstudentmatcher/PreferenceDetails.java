package com.cerner.newdevelopment.medicalstudentmatcher;

/*
 * This class holds the details of hospital/student preferences like value, rank, whether this choice is already
 * processed.
 */
public class PreferenceDetails 
{
	public int value;
	public int rank;
	public int hospitalPreference;
	public int studentPreference;
	public Boolean isAlreadyprocessed=false;
}
