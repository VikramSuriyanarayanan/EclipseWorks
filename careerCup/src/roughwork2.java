import java.util.HashMap;
import java.util.Hashtable;


public class roughwork2 {

	private static Hashtable<Integer,Integer> h2 = new Hashtable<Integer, Integer>();
	private static HashMap<Integer,Integer> h3 = new HashMap<Integer,Integer>();

	public static void main(String args[]){
		String s = " Hello World";
		s = stringReversal(s);
	//	System.out.println(s);

		
		h2.put(1, 1);
		h2.put(1, 2);
		h2.put(2, 10);
		h2.put(2, 20);
		System.out.println(h2);
		
		h3.put(1, 1);
		h3.put(1, 2);
		h3.put(2, 10);
		h3.put(2, 20);
		System.out.println(h3);
		
		

	}
	public static String stringReversal(String s){
		if(s.length() == 0)
			return s;

		return (stringReversal((s.substring(1))) + s.charAt(0));
	}

}
