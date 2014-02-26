package smallQues;

public class Test06 extends Thread{

	/**
	 * @param args
	 */
	public Test06(String str){
		super(str);
	}
	
	public void run(){
		while(true){
			System.out.println("In thread method");

			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("still running... alive" + getName());
		}
	}

}
