package forCerner;

public class primeNo {
	public static void main (String args[]){
		int num = 11;
		int i;
		for (i=2; i < num ;i++ ){
			int n = num%i;
			if (n==0){
				System.out.println("not Prime!");
				break;
			}
		}
		if(i == num){
			System.out.println("Prime number!");
		}

	}
}
