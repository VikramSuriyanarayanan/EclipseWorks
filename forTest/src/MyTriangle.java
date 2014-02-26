
public class MyTriangle {

	private int side1;
	private int side2;
	private int side3;
	
	public MyTriangle() {
		this.side1 = 5;
		this.side2 = 10;
		this.side3 = 15;
	}
	
	public MyTriangle(int variableOne, int variableTwo, int variableThree) {
		this.side1 = variableOne;
		this.side2 = variableTwo;
		this.side3 = variableThree;
	}

	public static void main(String[] args) {
		 MyTriangle ObjectOne = new MyTriangle();
		 Boolean returnValue = ObjectOne.isVisible();
		 
		 if(returnValue){
			 System.out.println("For the given inputs, the Sum of any two sides is greater than the third side");
		 }
		 else{
			 System.out.println("For the given inputs, the Sum of any two sides is not greater than the third side for values of 5,10,15");
		 }
		 
	}
	
	public Boolean isVisible(){
		
		int checkOne = side1 + side2;
		int checkTwo = side2 + side3;
		int checkThree = side3 + side1;
		
		if(checkOne > side3 || checkTwo > side1 || checkThree > side2){
			return true;
		}
		else return false;		
	}

}
