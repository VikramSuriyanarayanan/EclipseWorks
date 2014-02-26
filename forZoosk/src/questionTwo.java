
public class questionTwo implements Command{

	public int call(int x, int y){
		int sum = calc(x,y);
		return sum;
	}

	public int calc(int x,int y){
		int sum = 0;
		sum = x+y;
		return sum;
	}

	public static void main(String[] args) {
		/* Implements the method accumulate for addition*/

		int[] arrayOfInt = {1,2,3}; /* Input */
		System.out.println("ACCUMULATION METHOD output: "+accumulate(new questionTwo(),arrayOfInt)); 
	}

	public static int accumulate(Command command, int[] arrayOfInt) {

		int answer = 0;
		for(int i=0;i<arrayOfInt.length;i++){
			try{
				answer = command.call(answer,arrayOfInt[i]);
			}
			catch(ArithmeticException e){
				System.out.println("error");
				e.printStackTrace();
			}
		}	
		return answer;
	}

}

