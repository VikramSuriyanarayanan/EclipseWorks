
public class rvCheck {
	public static void main(String[] args) {
		// This class checks what will happen when -1 is returned
		
		int a = 5;
		int b = assignval();
		System.out.println("Value of b is "+ b);

	}
	
	static int assignval(){
		return -1;
	}

}
