package smallQues;

public class Test07 {

	public static void main(String[] args) {
		System.out.println("In main method");
	//	Test06 TObj = new Test06("myThreadName");
	//	TObj.start();
		Test06[] tObj = new Test06[100];
		tObj[1] = new Test06("ThreadName");
		tObj[2] = new Test06("tNammmeeeerrrrrrr");
		tObj[1].start();
		tObj[2].start();
		System.out.println("Still in main method");
	}
}
