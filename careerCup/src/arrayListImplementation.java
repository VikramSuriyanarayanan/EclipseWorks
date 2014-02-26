import java.util.*;

public class arrayListImplementation {

	private static HashMap<Object,Integer> h1 = new HashMap<Object,Integer>();
	/**
	 * ARRAY LIST
	 */
	public static void main(String[] args) {
		//ARRAYLIST Implementation in Java
		ArrayList<Integer> a1 = new ArrayList<Integer>();
		a1.add(12);
		a1.add(2133);
		a1.add(12);
		a1.add(12);
		a1.add(12);
		a1.add(400);
		System.out.println("Before removing duplicates: "+a1);
		removeDuplicates(a1);
		System.out.println("After removing duplicates"+a1);
	}
	public static void removeDuplicates ( ArrayList a2){
		
		Iterator it = a2.iterator();
		while(it.hasNext()){
			Object obj = it.next();
			System.out.println(obj);
			if(!(h1.containsKey(obj))){
				h1.put(obj, 1);
			}
		}
		
		System.out.println(h1);
	}
}