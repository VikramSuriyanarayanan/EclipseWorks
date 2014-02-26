package forCerner;

public class classB extends classA {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		classA Bobj = new classB();
		Bobj.methodA();

		System.out.println("Value of aa is "+ Bobj.aa);
	}
	
	public void methodA(){
		System.out.println("In class-B : method-A");
	}

}
