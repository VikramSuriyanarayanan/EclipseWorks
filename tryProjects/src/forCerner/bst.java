package forCerner;

import java.util.Scanner;

public class bst {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int a[] = new int[10];
		System.out.println("Enter the array values");
		
		Scanner sc = new Scanner(System.in);
		for(int i = 0; i < 5 ; i++){
			a[i] = sc.nextInt();
		}
		for(int i =0; i < 5; i++)
		System.out.println("ARRAY contents are : "+a[i] );
		
		System.out.println("Enter the number to be searched ");
		
		
	}

}
