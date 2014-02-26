package com.cerner.newdevelopment.matcher;

/**
 * Represents a match between two objects
 * @author MW5521
 *
 * @param <P> The type of the first object in the match
 * @param <O> The type of the second object in the match
 */
public class Match<P, O> {
	
	private P item1;
	private O item2;
	
	public Match(P p, O o) {
		setItem1(p);
		setItem2(o);
	}

	public P getItem1() {
		return item1;
	}

	public void setItem1(P item1) {
		this.item1 = item1;
	}

	public O getItem2() {
		return item2;
	}

	public void setItem2(O item2) {
		this.item2 = item2;
	}

}
