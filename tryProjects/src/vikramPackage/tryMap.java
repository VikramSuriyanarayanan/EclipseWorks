package vikramPackage;

import java.util.HashMap;
import java.util.*;

public class tryMap {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		ArrayList a1 = new ArrayList();
		HashMap<String,ArrayList> m1 = new HashMap();
		
		a1.add(1);
		a1.add(2);
		 m1.put("Zara", a1);
	      m1.put("Mahnaz", a1);
	
	      System.out.println("a1 size "+ a1.size());
	      System.out.println(" Map Elements");
	      System.out.print("\t" + m1);
		
		
	}

}
