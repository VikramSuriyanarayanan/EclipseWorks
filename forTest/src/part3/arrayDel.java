package part3;
import java.util.*;

public class arrayDel {
	private static ArrayList ar = new ArrayList();

	public static void main(){
		System.out.println("HI");
		ar.add(2);
		ar.add(2);
		ar.add(5);ar.add(5);ar.add(5);
		System.out.println("ARR val " + ar);
		for(int i=0;i<ar.toArray().length;i++){
			if(ar.contains(2)){
				ar.remove(i);
			}
		}
		System.out.println("ARR val " + ar);
	}

}
