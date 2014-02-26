package forExcersise1;

public class stringManipulation{

	public static void main(String[] args){
		String testString = "abcdA";
		System.out.println(isUnique(testString));
	}

	public static boolean isUnique(String toBeTested){
		int length = toBeTested.length();
		boolean[] arrayFlag = new boolean[128];
		
		for(int i = 0; i < length ; i++)
		{   
			int ascii = toBeTested.charAt(i);

			if(arrayFlag[ascii]){
				return false;
			}

			arrayFlag[ascii] = true;

		}
		return true;
	}

}