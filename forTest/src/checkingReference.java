
public class checkingReference {

	private int x;

	public static void main(String[] args) {
		checkingReference newobj = new checkingReference();
		int y;
		newobj.x = 5;
			   y = 7;
			   
			   callFunc(y);
			   System.out.println("In main "+ y);
		
	}

	public static int callFunc(int passObj){
		passObj++;
		System.out.println("PASSOBJ "+passObj);
		return passObj;
	}
}
