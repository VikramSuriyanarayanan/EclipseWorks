package vikramPackage;
import java.util.*;

public class arrayDel {
	private static ArrayList<Integer> ar = new ArrayList<Integer>();

	public static void main(String args[]){
		System.out.println("HI");
		ar.add(2);
		ar.add(2);
		ar.add(5);ar.add(5);ar.add(5);
		System.out.println("ARR val " + ar);
		for(int i=0;i<ar.size();i++){
			if(ar.contains(2)){
				System.out.println("arr["+i+"] : "+ ar+"removed" );
				ar.remove(i);
				
			}
		}
		System.out.println("ARR val " + ar);
	}

}
