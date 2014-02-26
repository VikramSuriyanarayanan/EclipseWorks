package com.cerner.newdevelopment.preference;

/**
 * Class that captures the concept that some objects prefer other objects with a certain level of priority.
 * 
 * If A prefers B more than A prefers C, it is assumed that the value of the preference for A to B would be
 * less than the value of the preference for A to C.  (A preference value of 1 is better than a preference
 * value of 2.) 
 * 
 * @author MW5521
 *
 * @param <P>  The type of the object that prefers another object
 * @param <O>  The type of the object being preferred
 */
public class Preference<P, O> {

	private P preferrer;
	private O preferred;
	private int value;
	
	public Preference(P p, O o, int v) {
		setPreferrer(p);
		setPreferred(o);
		setValue(v);
	}

	/**
	 * Retrieves the object that is the one that prefers something else
	 * @return
	 */
	public P getPreferrer() {
		return preferrer;
	}

	/**
	 * Sets the object that is the one that prefers something else
	 * @param preferrer
	 */
	public void setPreferrer(P preferrer) {
		this.preferrer = preferrer;
	}

	/**
	 * Gets the object that is being preferred
	 * @return
	 */
	public O getPreferred() {
		return preferred;
	}

	/**
	 * Sets the object that is being preferred
	 * @param preferred
	 */
	public void setPreferred(O preferred) {
		this.preferred = preferred;
	}

	/**
	 * Gets the value that represents how much the preferrer prefers the object
	 * @return
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Sets the value that represents how much the preferrer prefers the object
	 * @param value
	 */
	public void setValue(int value) {
		this.value = value;
	}
}
