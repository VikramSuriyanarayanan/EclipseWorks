package forRecursion;

public class fibonacci{

	public int fibonacciMethod(int N){
		int result  = 0;
		
		if(N == 0)
			return 0;
		if(N == 1)
			return 1;
		else
			result = fibonacciMethod(N-1) + fibonacciMethod(N-2);

		return result;        
	}

	public static void main(String[] args){

		int N = 7;
		fibonacci Obj = new fibonacci();
		System.out.println("Nth Fibonacci number is : " + Obj.fibonacciMethod(N));

	}
}