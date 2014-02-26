package forCerner;

import java.util.StringTokenizer;

public class strRev {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		strRev rObj = new strRev();
		String input = "Hello World";
		String[] splited = input.split("\\s+");
		System.out.println(splited[1]);
		String result = null;

/*		for(int i = 0; i < splited.length;i++){

			result = rObj.reverseString(splited[i]);
			System.out.println("RESULT is : "+ result);
		}
*/			
		result = rObj.reverseString("Hello World");
		System.out.println("RESULT is : "+ result);
		
	}

	public String reverseString(String s){
		if (s.length() == 0) 
			return s;

		return reverseString(s.substring(1)) + s.charAt(0);
	}

}
