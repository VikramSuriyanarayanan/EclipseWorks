package smallQues;

public class NegativeNumberException extends Exception{

	NegativeNumberException(){
		System.out.println("Negative numbers not allowed");
	}
	
	NegativeNumberException(String str){
		System.out.println("The error is " + str);
	}
}
