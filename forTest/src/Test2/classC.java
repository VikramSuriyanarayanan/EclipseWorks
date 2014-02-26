package Test2;

public class classC extends classA implements interfaceD{

	public static void main(String[] args) {
	}

	public void methodC1(){
	}
	
	@Override
	public void methodD1() {
	}
	
	//*******************************************************************************
	//OPTIONAL METHOD: Overriding methodA1 in this classC is optional... . But if this
	// method is present in subclass classB it will get overridden here.
	//*******************************************************************************
	
	//Either methodA2 has to be written or classC must be declared Abstract
	@Override
	public void methodA2() {
	}
	
}
