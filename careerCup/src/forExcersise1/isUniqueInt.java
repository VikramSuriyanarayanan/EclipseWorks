package forExcersise1;
import java.util.*;

public class isUniqueInt {

//	private static HashMap<Integer,Integer> h1 = new HashMap<Integer,Integer>();
//	private static HashSet<Integer> h2 = new HashSet<Integer>();
	
	public boolean isUnique(int[] array){
/*
		for(int i = 0;i < array.length;i++)
		{
			if(h1.containsKey(array[i]))
					return false;

			h1.put(array[i],1);
		}

		return true; */
		
		Set<String> inputSet = new HashSet<String>();
		
		if(inputSet.contains(array))
			return true;
		
		return false;
	}
	
	public static void main(String[] args){
		
		int[] array = {1,2,3,4,5};
		
		isUniqueInt Obj = new isUniqueInt();
		System.out.println("Does the array have unique integers? " +Obj.isUnique(array));
	}

}