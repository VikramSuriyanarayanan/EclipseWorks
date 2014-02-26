package linkedLst;

public class myLinkNode {

	myLinkNode next;
	int intData;
	public myLinkNode(int passedValue){
		this.intData = passedValue;
	}
	
	public void printNode(){
		System.out.println("String Value: "+intData);
	}
}
