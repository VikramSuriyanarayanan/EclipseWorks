package MineLinkedList;

public class myLinkNode {
	/**
	 * LinkNode class 
	 */
	
	myLinkNode next;
	String strValue;

	public myLinkNode(String passedValue){
		this.strValue = passedValue;
		//this.next = null;
	}
	
	public void printLink(){
		System.out.println("String: " + strValue);
	}
}
